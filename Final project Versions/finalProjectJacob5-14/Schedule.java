
import java.util.Dictionary;
/**
 * I'm thinking that we should change have & need to a HaveDictionary[] & a NeedDictionary[]
 * so we can keep track of each time slot.
 * Also perhaps we should make a business object which keeps track of the schedule, and hold the employees in one place.
 */
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
public class Schedule
{
    public HaveDictionary have[][][] = new HaveDictionary[4][7][48];
    public NeedDictionary need[][][] = new NeedDictionary[4][7][48];
    public ArrayList<Person> workers = new ArrayList<Person>();
    public ArrayList<String> positions = new ArrayList<String>();

    /**
     * Jacob Waters
     * Constructor for objects of class Schedule
     */
    public Schedule()
    {
        for(int a=0;a<have.length;a++)
            for(int b=0;b<have[0].length;b++)
                for(int c=0;c<have[0][0].length;c++)
                {
                    have[a][b][c]=new HaveDictionary();
                    need[a][b][c]=new NeedDictionary();
                }
    }

    //new 
    public Schedule(String fileName)
    {
        for(int a=0;a<have.length;a++)
            for(int b=0;b<have[0].length;b++)
                for(int c=0;c<have[0][0].length;c++)
                {
                    have[a][b][c]=new HaveDictionary();
                    need[a][b][c]=new NeedDictionary();
                }
        File input=new File("Schedule.txt");
        try
        {
            Scanner scan=new Scanner(input);
            while(!scan.hasNextInt())
            {
                positions.add(scan.next());
            }
            for(int week=0;week<4;week++)
                for(int day=0;day<7;day++)
                    for(int halfHour=0;halfHour<48;halfHour++)
                    {
                        Iterator keys=positions.iterator();
                        for(int i=0;keys.hasNext();i++)
                        {
                            need[week][day][halfHour].add((String)keys.next(),Integer.parseInt(scan.next()));
                        }
                    }
            while(scan.hasNext())
            {
                String name=scan.next();
                ArrayList<String> myPositions=new ArrayList<String>();
                String temp;
                boolean[][][] mySchedule=new boolean[4][7][48];
                while(!scan.hasNextInt())
                {
                    myPositions.add(scan.next());
                }
                for(int week=0;week<4;week++)
                    for(int day=0;day<7;day++)
                        for(int halfHour=0;halfHour<48;halfHour++)
                        {
                            mySchedule[week][day][halfHour]=scan.next().equals("1");                            
                        }
                workers.add(new Person(name,myPositions,mySchedule));

            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("No schedule found, I just created a new one.");
            for(int a=0;a<have.length;a++)
                for(int b=0;b<have[0].length;b++)
                    for(int c=0;c<have[0][0].length;c++)
                    {
                        have[a][b][c]=new HaveDictionary();
                        need[a][b][c]=new NeedDictionary();
                    }
        }
    }

    /**
     * Jacob Waters
     */
    public void addPosition(String newPosition)
    {
        positions.add(newPosition);
        for(int a=0;a<have.length;a++)
            for(int b=0;b<have[0].length;b++)
                for(int c=0;c<have[0][0].length;c++)
                {
                    need[a][b][c].add(newPosition,0);
                }
    }

    /**
     * Jacob Waters
     */
    public void setNeededEmployees(String position,int week,int day,int startTime,int stopTime, int neededEmployees)
    {
        while(startTime<stopTime)
        {
            need[week][day][startTime].add(position,neededEmployees);
            startTime++;
        }
    }

    public void printSchedule(int week,int day)
    {
        for(int hour=0;hour<48;hour++)
        {
            Iterator positions=need[week][day][hour].getKeyIterator();
            Iterator totalNeeded=need[week][day][hour].getValueIterator();
            if(hour%2==0)

                System.out.print((hour/2)+":00 ");
            else
                System.out.print((hour/2)+":30 ");
            while(positions.hasNext())
            {
                System.out.print(totalNeeded.next()+" "+positions.next()+", ");
            }
            System.out.println();
        }
    }

    public Object[] save()
    {
        Iterator positions=need[0][0][0].getKeyIterator();
        int size=need[0][0][0].keys.size();
        String[] titles=new String[size];
        for(int i=0;i<size;i++)
        {
            titles[i]=(String)positions.next();
        }
        String[][][] numberNeeded=new String[4][7][48];
        for(int week=0;week<4;week++)
            for(int day=0;day<7;day++)
                for(int halfHour=0;halfHour<48;halfHour++)
                {
                    Iterator values=need[week][day][halfHour].getValueIterator();
                    numberNeeded[week][day][halfHour]="";
                    for(int i=0;i<size;i++)
                    {
                        numberNeeded[week][day][halfHour]+=values.next()+" ";
                    }
                }
        Object[] workers=new Object[this.workers.size()];
        for(int i=0;i<workers.length;i++)
        {
            workers[i]=new String[1+(this.workers.get(i).getPositions().size())+1344];
            ((String[])workers[i])[0]=this.workers.get(i).getName();
            for(int a=0;a<this.workers.get(i).positionsToArray().length;a++)
                ((String[])workers[i])[1+a]=this.workers.get(i).positionsToArray()[a];
            for(int week=0;week<4;week++)
                for(int day=0;day<7;day++)
                    for(int halfHour=0;halfHour<48;halfHour++)
                        if(this.workers.get(i).schedule[week][day][halfHour])
                            ((String[])workers[i])[1+this.workers.get(i).positionsToArray().length+(week*7*48)+(day*48)+halfHour]="1";
                        else
                            ((String[])workers[i])[1+this.workers.get(i).positionsToArray().length+(week*7*48)+(day*48)+halfHour]="0";

        }
        Object[] returns =new Object[3];
        returns[0]=titles;
        returns[1]=numberNeeded;
        returns[2]=workers;
        return returns;
    }

}
