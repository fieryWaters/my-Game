
/**
 * Write a description of class Person here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Person
{
    private String name;
    private ArrayList<String> positions;
    public boolean[][][] schedule = new boolean[4][7][48];//days halfHours
    public String[] positionsToArray()
    {
        Object[] temp=positions.toArray();
        String[] returns=new String[temp.length];
        for(int i=0;i<positions.size();i++)
        {
            returns[i]=(String)temp[i];
        }
        return returns;
    }

    public Person(String name,String position)
    {
        this.name=name;
        positions=new ArrayList<String>();
        positions.add(position);
    }
    
    public Person(String name,ArrayList<String> positions,boolean[][][] schedule)
    {
        this.name=name;
        positions=new ArrayList<String>();
        this.positions=positions;
        this.schedule=schedule;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ArrayList<String> getPositions()
    {
        return positions;
    }
    public void addPosition(String position)
    {
        positions.add(position);
    }

    public void setPosition(String[] positions)
    {
        for(String s:positions)
        {
            this.positions.add(s);
        }
    }

    public void viewSchedule(int week)
    {
        System.out.println("           Mon      Tue      Wed     Thu      Fri      Sat      Sun");
        
        for(int halfHour=0;halfHour<48;halfHour++)
        {

            if(halfHour%2==0)

                System.out.print((halfHour/2)+":00 ");
            else
                System.out.print((halfHour/2)+":30 ");
            if(halfHour<20)
                System.out.print(" ");
            for(int day=0;day<7;day++)
            {
                if(schedule[week][day][halfHour])
                    System.out.print("|| Busy ");
                else
                    System.out.print("|| Free ");
            }
            System.out.println();
        }
    }
}
