
/**
 * Write a description of class Level here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import java.util.Scanner;
import java.io.*;
public class Level//Jacob Waters
{
    Polynomial[] peices;//all the Polynomials that compose a full level, in sorted order from left to right.
    Color color=Color.BLACK;//the color of the line drawn
    int leftIndex,rightIndex;//determines which Polynomials to draw. If a level has 100 different Polynomials, it would be inefficient to draw them all.
    //Only the Polynomials within the bounds of the screen, in terms of X, are drawn. Refer to refreshBounds() for details.
    /**
     * This constructor basically just adds the two polynomials to peices, and sets the bounds so that both are shown.
     */
    public Level(Polynomial a,Polynomial b)//Jacob Waters
    {
        peices =new Polynomial[2];
        peices[0]=a;
        peices[1]=b;
        leftIndex=0;rightIndex=1;
    }

    /**
     * Creates a level from file. Presumable a Level has been created using the drawPanel and saved to a .txt file of a chosen name. Refer to DrawPanel.save(String fileName) for more info.
     * @param input This is the .txt file which contains the key information about a Level.
     */
    public Level(File input)//Jacob Waters
    {
        try{
            peices=new Polynomial[0];
            Scanner scan=new Scanner(input);
            while(scan.hasNextInt())
            {
                int length=scan.nextInt();
                scan.nextLine();
                String[] returns=new String[length*3+5];
                returns[0]=""+length;
                for(int i=1;i<length*3+5;i++)
                    returns[i]=scan.nextLine();
                Polynomial temp=new Polynomial(returns);
                add(temp);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void scrambleColors()
    {
        for(int i=0;i<peices.length;i++)
        {
            if(i%3==0)
                peices[i].setColor(Color.green);
            else if(i%3==1)
                peices[i].setColor(Color.blue);
            else if(i%3==2)
                peices[i].setColor(Color.red);
        }
    }

    /**
     * @return returns the total number of Polynomials in this Level.
     */
    public int length()//Jacob Waters
    {
        return peices.length;
    }

    /**
     *  Draws a Level object with respect to a global shift variable.
     * @param g This is the Graphics Object that is being used to draw. By accepting a Graphics Object as a
     * parameter it allows the drawing to be done within each individual class, rather than have a superfluous amount of code in the driver(s).
     * @param globalX An external variable that is used to shift the graph, potentially used in relation to a moving point of reference.
     * @param globalY Shifts the graph vertically by this value.
     */
    public void globalDraw(Graphics g,int globalX,int globalY)//Jacob Waters
    {
        for(int i=leftIndex;i<=rightIndex;i++)
        {
            //peices[i].setColor(color); 
            peices[i].globalDraw(g,globalX,globalY);
        }
    }

    /**
     * @param p appends p onto the last index of peices.
     */
    public void add(Polynomial p)//Jacob Waters
    {
        Polynomial[] temp=new Polynomial[peices.length+1];
        for(int i=0;i<peices.length;i++)
        {
            temp[i]=peices[i];
        }
        temp[temp.length-1]=p;
        peices=temp;
    }

    /**
     * @param p appends p onto the last index of peices.
     */
    public void add(Polynomial p,IntersectCircle intersect)//Jacob Waters
    {
        Polynomial[] temp=new Polynomial[peices.length+1];
        for(int i=0;i<peices.length;i++)
        {
            temp[i]=peices[i];
        }
        temp[temp.length-1]=p;
        peices=temp;
        intersect.add(p);
    }

    /**
     * @param color Changes the color of this level to this color.
     */
    public void setColor(Color color)//Jacob Waters
    {
        this.color=color;
    }

    /**
     * @return Returns the Polynomial at index.
     */
    public Polynomial get(int index)//Jacob Waters
    {
        return peices[index];
    }

    /**
     * @param inputX This is a real X coordinate from the screen, without regard to
     * any shifting variables.
     * @param globalX The global Shift variable being applied to the level.
     * This method takes an input and the shift value, and returns the polynomial that is
     * within the bounds. This has been tested and works perfectly.
     
     */
    public Polynomial find(int inputX,int globalX)//Jacob Waters
    {
        for(int i=0;i<peices.length;i++)
        {
            //System.out.println("inputX: "+inputX+"+ getShift: "+    peices[i].getXShift()+" minus global "+globalX+" should be greater than "+peices[i].getStart());
            //System.out.println("corrected value "+(inputX+peices[i].getXShift()-globalX)+" should be greater than "+peices[i].getStart());
            if(inputX-peices[i].getXShift()-globalX>=peices[i].getStart()&&inputX-peices[i].getXShift()-globalX<=peices[i].getStop())
            //modified by -peices[i].getXShift() not sure of effect yet. this method partially works without this.
            {
                return peices[i];
            }
        }
        return null;
    }

    /**
     * @return Returns the Polynomial at the last index.
     */
    public Polynomial getLast()//Jacob Waters
    {
        return peices[peices.length-1];
    }

    /**
     * This method changes the bounds of the Polynomial so only polynomials that are within the bounds of the screen are displayed.
     * Without this relatively simple method Level(s) with a large number of peices would start to lag/destroy your computer by overworking it unnecessarily.
     * It works by checking if there are any Polynomials that should be on the screen that aren't, and by checking to see if any polynomials that aren't on the 
     * screen are being graphed. 
     * 
     * Note that leftIndex and rightIndex are the indices of the Polynomial[] at the left and right bounds.
     * 
     * @param globalX In this case globalX refers to the left bound of the screen.
     * @param width globalX+width refers to the right bounds of the screen.
     */
    public void refreshBounds(int globalX, int width)//Jacob Waters
    {
        while(peices[leftIndex].getStart()+peices[leftIndex].getXShift()+globalX>0)//good
            if(leftIndex>0)
                leftIndex--;
            else
                break;
        while(peices[leftIndex].getStop()+peices[leftIndex].getXShift()+globalX<0)//good
            if(leftIndex<peices.length-1)
                leftIndex++;
        while(peices[rightIndex].getStop()+peices[rightIndex].getXShift()+globalX<width)
            if(rightIndex<peices.length-1)
                rightIndex++;
            else break;
        while(peices[rightIndex].getStart()+peices[rightIndex].getXShift()+globalX>width)
            if(rightIndex>0)
                rightIndex--;
            else break;

    }

    public int indexOf(Polynomial p)//Jacob Waters
    {
        for(int i=0;i<peices.length;i++)
        {
            if(p==peices[i])
                return i;
        }
        return -1;
    }
    public int indexOf(int inputX,int globalX)//Jacob Waters
    {
        for(int i=0;i<peices.length;i++)
        {
            //System.out.println("inputX: "+inputX+"+ getShift: "+    peices[i].getXShift()+" minus global "+globalX+" should be greater than "+peices[i].getStart());
            //System.out.println("corrected value "+(inputX+peices[i].getXShift()-globalX)+" should be greater than "+peices[i].getStart());
            System.out.println();
            if(inputX-peices[i].getXShift()-globalX>=peices[i].getStart()&&inputX-peices[i].getXShift()-globalX<=peices[i].getStop())
            //modified by -peices[i].getXShift() not sure of effect yet. this method partially works without this.
            {
                return i;
            }
        }
        return -1;
    }

    public Polynomial[] toArray()
    {
        return peices;
    }
}