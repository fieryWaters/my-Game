
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Write a description of class Driver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Driver
{
    public static Schedule globalSchedule;
    public static String[] oneTwoThree={"1","2","3"};
    public static String[] oneTwoThreeFour={"1","2","3","4"};
    public static String[] yesNo={"yes","no"};
    public static void main()
    {
        boolean done=false;
        boolean admin;
        while(!done)
        {
            globalSchedule=new Schedule("Schedule");
            Scanner scan=new Scanner(System.in);
            System.out.println("Admin or User?");
            String[] correctAnswers={"admin","user"};
            String answer=findValidAnswer(correctAnswers);
            if(answer.equalsIgnoreCase("Admin"))
                admin=true;
            else
                admin=false;
            if(admin)
            {
                do{
                    System.out.println("Ok, big guy, what would you like to do?");
                    System.out.println("Enter the number that correspronds with your choice.");
                    System.out.println("1: add a new job title.");
                    System.out.println("2: change the Schedule.");
                    System.out.println("3: Save the Schedule.");
                    System.out.println("4: quit the program.");

                    String[] oneTwoThreeFour={"1","2","3","4"};
                    answer=findValidAnswer(oneTwoThreeFour);
                    int choice=Integer.parseInt(answer);
                    if(choice==1)//Jacob Waters
                    {
                        do
                        {
                            String newPosition;
                            do
                            {
                                System.out.println("What is the position you'd like to add?");
                                newPosition=scan.nextLine();
                                System.out.println("Is this spelled correctly: "+newPosition);
                                correctAnswers[0]="yes";correctAnswers[1]="no";
                                answer=findValidAnswer(correctAnswers);
                            }while(!answer.equals("yes"));
                            globalSchedule.addPosition(newPosition);
                            System.out.println("are you done?");
                            answer=findValidAnswer(correctAnswers);
                            done=answer.equals("yes");
                        }while(!done);
                        done=false;
                    }
                    else if(choice==2)//Jacob Waters
                    {
                        String[] days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
                        System.out.println("Alrighty then, would you like to start from the beginning?");
                        correctAnswers[0]="yes";correctAnswers[1]="no";
                        answer=findValidAnswer(correctAnswers);
                        done=false;
                        if(answer.equals("yes"))
                        {
                            for(int week=0;!done;week++)
                            {
                                for(int day=0;day<7;day++)
                                {
                                    do
                                    {
                                        if(addPeople(week,day,scan))
                                        {
                                            globalSchedule.printSchedule(week,day);
                                            System.out.println("add successful, you can say \"1\" continue with today, \"2\" go to tomorrow, \"3\" next Week, or \"4\" I'm over it yo");
                                            answer=findValidAnswer(oneTwoThreeFour);
                                            if(answer.equals("2"))
                                                done=true;
                                            else if(answer.equals("3"))
                                            {
                                                week++;
                                                day=0;
                                            }else if(answer.equals("4"))
                                            {
                                                done=true;day=7;
                                            }
                                        }
                                        else
                                        {
                                            System.out.println("Would you like to try again?");
                                            answer=findValidAnswer(correctAnswers);
                                            if(answer.equals("no"))
                                                done=true;
                                        }
                                    }while(!done);
                                }
                                System.out.println("wow, you actually went through a whole week, you must be a thorough cucumber, truly.");
                                System.out.println("Are you done, I doubt you want to do that a second time.");
                                String[] yesNo={"yes","no"};
                                answer=findValidAnswer(yesNo);
                                if(answer.equals("yes"))
                                    done=true;
                            }
                            done=false;
                        }
                        else
                        {

                        }
                    }else if(choice==3)//Jacob Waters
                    {
                        //System.out.println("input a fileName,you IMP");
                        save("Schedule");
                        System.out.println("State saved, woot");
                        System.out.println("Are you done?");
                        String[] yesNo={"yes","no"};
                        done=findValidAnswer(yesNo).equals("yes");
                    }
                    else if(choice==4)//Jacob Waters
                    {
                        done=true;
                    }
                }while(!done);
            }
            else//!admin
            {
                do
                {
                    System.out.println("Alright small fry, what would you like to do?");
                    System.out.println("You can say \"1\" Sign in, \"2\" Add Myself, \"3\" Save, or \"4\" log out");
                    answer=findValidAnswer(oneTwoThreeFour);
                    if(answer.equals("1"))
                    {
                        System.out.println("Whats your full name, kind sir or lass?");
                        String name=goodInput();//good input makes sure they spell it right before assigning name.
                        Person currentPerson=null;
                        for(Object person:globalSchedule.workers.toArray())
                        {
                            if(((Person)person).getName().equals(name))
                                currentPerson=(Person)person;
                        }
                        if(currentPerson!=null)
                        {
                            userLoggedIn(currentPerson);
                        }
                        else//name not found in globalSchedule.workers
                        {
                            System.out.println("There was an error, you weren't found in our system.");
                            System.out.println("Would you like to add yourself to the system?");
                            if(findValidAnswer(yesNo).equals("yes"))
                                userLoggedIn(addWorker(globalSchedule,name));
                        }
                    }else if(answer.equals("2")) //add myself to the system
                    {
                        userLoggedIn(addWorker(globalSchedule));
                    }
                    else if(answer.equals("3"))
                    {
                        save("Schedule");
                        System.out.println("Your data has been saved woo!");
                    }
                    else//log out
                    {
                        done=true;
                    }
                }while(!done);
            }
            System.out.println("Would you like to shut down the program?");
            done=findValidAnswer(yesNo).equals("yes");
        }
        System.out.println("Thanks for Visiting!");
    }

    public static void userLoggedIn(Person p)
    {
        String answer;
        boolean done=false;
        String[] days ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        System.out.println("Welcome, "+p.getName()+"!");
        do{

            System.out.println("What would you like to do?");
            System.out.println("You can say: \"1\" Update my Schedule, \"2\" view my Personal Schedule,");
            System.out.println("\"3\" view my Shedule with this restaurant, or \"4\" Log out");
            answer=findValidAnswer(oneTwoThreeFour);
            if(answer.equals("1"))//update my schedule
            {

                System.out.println("Okay now its time to see what hours you can't work for each day of the week!");
                System.out.println("This is the format for the time: Start Time - End Time (ex. 9:00 - 5:30)");
                System.out.println("Also note we only take increments of 30 minutes!");
                System.out.println("We will start with Monday and work through the list,");
                System.out.println("if you cannot work this day simply enter \"no\",");
                System.out.println("if you're free all day enter \"free\".");
                int day = 0;
                int week=0;
                boolean flag;
                do{
                    do
                    {
                        String time = goodInput();//Mod changed scan.nextLine() to goodInput()
                        flag = addTime(p,week, day, time);
                        if(flag == false)
                            System.out.println("Sorry we are having an issue with your input! Please enter it again");
                        else
                        {
                            if(!time.equals("no")&&!time.equals("free"))
                            {
                                System.out.println("Are there any other times you can't work on this day?");
                                //mod Jacob Waters
                                if(findValidAnswer(yesNo).equals("no"))//Sometimes people are busy at two different time segments of the day.
                                    day++;
                            }
                            else
                                day++;
                            //mod Jacob Waters
                            if(day!=7)
                                System.out.println("Perfect! Everything worked out now tell us about " + days[day] + ".");
                        }
                    }while(day != 7);
                    System.out.println("Would you like to go onto next week");
                    String[] yesNo={"yes","no"};
                    if(findValidAnswer(yesNo).equals("no"))
                        done=true;
                }while(week<4&&!done);
                done=false;
            }
            else if(answer.equals("2"))//view my personal Schedule
            {
                p.viewSchedule(0);
            }
            else if(answer.equals("3"))//view my schedule with this restaurant
            {

            }else
                return;
            System.out.println("would you like to log out?");
            done=findValidAnswer(yesNo).equals("yes");
        }while(!done);
    }

    public static Person addWorker(Schedule globalSchedule)//Coby Forrester
    {
        String[] days ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello! To add you to our list of workers we will need some information");  
        System.out.println("Please enter your full name here: ");
        String name = goodInput();
        System.out.println("Now please enter your position here (ie Busser): ");
        //Mod Jacob waters from here
        Object[] toArray=globalSchedule.positions.toArray();
        String[] wrapper=new String[toArray.length];
        for(int i=0;i<wrapper.length;i++)
            wrapper[i]=(String)toArray[i];
        String position = findValidAnswer(wrapper);
        //Mod Jacob Waters to here- This streamlines the process to ensure a good input.
        Person p=new Person(name, position);
        globalSchedule.workers.add(p);
        System.out.println("Add successful");
        return p;
    }

    public static Person addWorker(Schedule globalSchedule,String name)//Coby Forrester//copied and modded by Jacob
    {
        String[] days ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello "+name+"! To add you to our list of workers we will need some information");  
        System.out.println("Now please enter your position here (ie Busser): ");
        //Mod Jacob waters from here
        Object[] toArray=globalSchedule.positions.toArray();
        String[] wrapper=new String[toArray.length];
        for(int i=0;i<wrapper.length;i++)
            wrapper[i]=(String)toArray[i];
        String position = findValidAnswer(wrapper);
        //Mod Jacob Waters to here- This streamlines the process to ensure a good input.
        Person p=new Person(name, position);
        globalSchedule.workers.add(p);
        System.out.println("Add successful");
        return p;
    }

    public static boolean addTime(Person person,int week, int day, String time) //interprets time input from a user day hours for loop//Coby Forrester
    //Mod Jacob Waters- I changed your method so that it takes what times you can't work, because most likely most of your day is free not including your job.
    {
        if("no".equals(time.toLowerCase()))
        {
            for(int i = 0; i < 48; i++)
                person.schedule[week][day][i] = true;
            return true;
        }
        else if(time.equals("free"))
            return true;

        if(time.length() < 9)//otherwise input is unreadable
            return false;
        String hours1 = "";
        String hours2 = "";
        String minutes1 = "";
        String minutes2 = "";
        int colon = 0;
        for(int i = 0; i < time.length(); i++)//for interpreting the time and making it workable Strings
        {
            if(time.charAt(i) == ':' && Character.isDigit(time.charAt(i - 1)) && 
            Character.isDigit(time.charAt(i + 1)) &&
            Character.isDigit(time.charAt(i + 2))) //seing if we are at a semiColon for reference and that all values are ints
            {
                if(colon == 0) //just a reference to see in the String at which semi colon we are at
                {
                    if(i > 1 && !Character.isDigit(time.charAt(i - 2)) )//testing if number is a digit 9:00 will yield 9 in hours and 13:30 will yield 13
                        hours1 = "" + time.charAt(i - 1);
                    else if(i > 1)
                        hours1 = "" + time.charAt(i - 2) + time.charAt(i - 1);
                    else
                        hours1 = "" + time.charAt(i - 1);
                    minutes1 = "" + time.charAt(i + 1) + time.charAt(i + 2);
                    colon++;
                }
                else
                {
                    if(!Character.isDigit(time.charAt(i - 2))) //testing if number is a digit 9:00 will yield 9 in hours and 13:30 will yield 13
                        hours2 = "" + time.charAt(i - 1);
                    else
                        hours2 = "" + time.charAt(i - 2) + time.charAt(i - 1);
                    minutes2 = "" + time.charAt(i + 1) + time.charAt(i + 2);
                }
            }
            else if(time.charAt(i) == ':') //characters in front of or behind semicolon are not usable integers
            {
                return false;
            }
        }

        if(colon == 0)//only one instance of a colon
            return false;

        if(Integer.parseInt(minutes1) % 30 != 0 || Integer.parseInt(minutes2) % 30 != 0)
            return false;

        if(Integer.parseInt(hours1) > 24 || Integer.parseInt(hours2) > 24)
            return false;

        int from = findTimeToArraySpotConversion(hours1, minutes1);
        int to = findTimeToArraySpotConversion(hours2, minutes2);
        if(from > 47 && to > 47 && from >= 0 && to >= 0 && to <= from)
            return false;
        for(int i = from; i < to; i++)
            person.schedule[week][day][i] = true;
        return true;
    }

    public static int findTimeToArraySpotConversion(String hours, String minutes)//returns ints for where in array to start filling with true//Coby Forrester
    {
        int temp = (int)((double)Integer.parseInt(hours) * 2);
        if(minutes.equals("30"))
            return temp + 1;
        else 
            return temp;
    }

    public static boolean addPeople(int week,int day,Scanner scan)// 3 Busser 7:30-13:30 Jacob Waters
    {
        String[] correctAnswers={"yes","no"};
        String answer;
        String[] days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        System.out.println("week "+(week+1)+", "+days[day]);
        System.out.println("format: numberNeeded Position StartTime-StopTime");
        System.out.println("Ex: 3 Busser 7:30-13:30");
        String argument=scan.nextLine();
        String[] peices=argument.split(" ");
        String position;
        if(globalSchedule.need[0][0][0].contains(peices[1]))
        {
            position=peices[1];
        }
        else
        {
            System.out.println("the position "+peices[1]+" is not found within the system, would you like to add "+peices[1]);
            if(findValidAnswer(correctAnswers).equals("yes"))
            {
                globalSchedule.addPosition(peices[1]);
                position=peices[1];
            }else
                return false;
        }
        if(peices.length!=3)
        {
            System.out.println("there are the correct number of tokens, there should be 3, there's actually only "+peices.length);
            return false;
        }
        String[] process=peices[2].split("-");
        if(process.length!=2)
        {
            System.out.println("Time not formatted corectly, there is no - character.");
            return false;
        }
        String[] left=process[0].split(":");
        String[] right=process[1].split(":");
        if(left.length!=2||right.length!=2)
        {
            System.out.println("Time not formatted corectly, there is no : character int the time.");
            return false;
        }
        if(!(isDigit(left[0])&&isDigit(left[1])&isDigit(right[0])&&isDigit(right[1])&&isDigit(peices[0])))
        {
            System.out.println("The tokens are not digits where they should be.");
            return false;
        }
        int start=Integer.parseInt(left[0])*2;
        if(left[1].equals("30"))
        {
            start+=1;
        }else if(!left[1].equals("00"))
        {
            System.out.println("improper time format, only 30 minute increments supported.");
            return false;
        }

        int numberNeeded=Integer.parseInt(peices[0]);
        int stop=Integer.parseInt(right[0])*2;
        if(right[1].equals("30"))
        {
            stop+=1;
        }else if(!right[1].equals("00"))
        {
            System.out.println("improper time format, only 30 minute increments supported.");
            return false;
        }
        System.out.println(position+" week "+week+", day "+day+" StartTime: "+start+", stopTime "+stop+", numberNeeded "+numberNeeded);
        globalSchedule.setNeededEmployees(position,week,day,start,stop,numberNeeded);
        return true;
    }

    public static String findValidAnswer(String[] correctAnswers)//Jacob Waters
    {
        Scanner scan=new Scanner(System.in);
        String answer=scan.nextLine();
        while(!validAnswer(correctAnswers,answer))
        {
            System.out.println("Ya screwed up, bub. Try again...");
            System.out.print("you can answer : ");
            for(int i=0;i<correctAnswers.length;i++)
            {
                System.out.print(correctAnswers[i]+", ");
            }
            System.out.println("try again please.");

            answer=scan.nextLine();
        }
        return answer.toLowerCase();
    }

    public static boolean validAnswer(String[] correctAnswers,String answer)//Jacob Waters
    {
        for(String str:correctAnswers)
        {
            if(answer.equalsIgnoreCase(str))
                return true;
        }
        return false;
    }

    public static String goodInput()//Jacob Waters
    {
        Scanner scan=new Scanner(System.in);
        String returns=scan.nextLine();
        System.out.println("is \""+returns+"\" spelled correctly?");
        String[] yesNo={"yes","no"};
        switch(findValidAnswer(yesNo))
        {
            case"yes":
            {
                return returns;
            }
            case"no":
            {
                System.out.println("Ok lets try again");
                return goodInput();
            }

        }
        return null;
    }

    public static boolean isDigit(String str)//Jacob Waters
    {
        for(int i=0;i<str.length();i++)
        {
            if(!(Character.isDigit(str.charAt(i))))
            {
                return false;
            }
        }
        return true;
    }

    public static void save(String levelName)//Jacob Waters
    {        
        BufferedWriter output = null;
        Object[] data=globalSchedule.save();
        String[] positions=(String[]) data[0];
        String[][][] numberNeeded=(String[][][])data[1];
        Object[] workers=(Object[])data[2];//each object contains String[], essentially a 2-D jagged array of String
        try
        {
            output = new BufferedWriter(new FileWriter(levelName+".txt",false));
            for(int i=0;i<positions.length;i++)
            {
                output.append(positions[i]);
                output.newLine();
            }
            for(int week=0;week<4;week++)
                for(int day=0;day<7;day++)
                    for(int halfHour=0;halfHour<48;halfHour++)
                    {
                        output.append(numberNeeded[week][day][halfHour]);
                        output.newLine();
                    }
            for(Object o:workers)
                for(String s:(String[])o)
                {
                    output.append(s);
                    output.newLine();
                }

        }
        catch ( IOException e)
        {
            System.out.println(e);
        }

        try
        {
            output.close();
        }
        catch ( IOException e)
        {
            System.out.println(e);
        }
    }
    //I'm saving this here incase I break your code Coby
    //     public static void addWorker(Schedule globalSchedule)//Coby Forrester
    //     {
    //         String[] days ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    //         Scanner scan = new Scanner(System.in);
    //         System.out.println("Hello! To add you to our list of workers we will need some information");  
    //         System.out.println("Please enter your full name here: ");
    //         String name = scan.nextLine();
    // 
    //         System.out.println("Now please enter your position here (ie Busser): ");//need to check if position is in system
    // 
    //         String position = findValidAnswer((String[])globalSchedule.positions.toArray());//Mod Jacob Waters- This streamlines the process to ensure a good input.
    // 
    //         //         boolean flag = true;
    //         //         if( ! globalSchedule.need[0][0][0].contains(position.toLowerCase()))
    //         //         while(flag)
    //         //         {
    //         //             System.out.println("Sorry we could not find your position, please enter it again: ");//need to check if position is in system
    //         //             position = scan.nextLine();
    //         //             if(globalSchedule.need[0][0][0].contains(position.toLowerCase()))
    //         //                 flag = false;
    //         //         }
    //         Person person = new Person(name, position);
    //         System.out.println("Okay now its time to see what hours you can work for each day of the week!");
    //         System.out.println("This is the format for the time: Start Time - End Time (ex. 9:00 - 5:30)");
    //         System.out.println("Also note we only take increments of 30 minutes!");
    //         System.out.println("We will start with Monday and work through the list, if you cannot work this day simply enter \"no\".");
    //         //what you need to do is some work
    //         int counter = 0;
    //         boolean flag = true;
    //         do
    //         {
    //             String time = goodInput();//Mod changed scan.nextLine() to goodInput()
    //             flag = addTime(person, counter, time);
    //             if(flag == false)
    //                 System.out.println("Sorry we are having an issue with your input! Please enter it again");
    //             else
    //             {
    //                 counter++;
    //                 System.out.println("Perfect! Everything worked out now tell us about " + days[counter] + ".");
    //             }
    //         }while(counter != 7);
    // 
    //         globalSchedule.workers.add(new Person(name, position));
    //     }

}
