
/**
 * The Polynomial Class is designed to receive a set of points, then create a polynomial that intersects each point.
 * 
 * @author Jacob Waters
 * @version BETA
 */
import java.awt.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
/**
 * The Polynomial class is an abstract representation of a polynomial with N terms, where N equals coefficients.length.
 * For example: Ax^2+BX+C is a polynomial where coefficients[0]=A, coefficients[1]=B, coefficients[2]=C.
 * 
 * A main purpose of this class is to take multiple points into a constructer, and create a Polynomial that intersects all the points, with flowing curves.
 * 
 * int[] points holds the points that were initially used to create the Polynomial, in the format of x1,y1,x2,y2,x3,y3...
 */
public class Polynomial 
{
    private double[] coefficients;//Ax^3+BX^2+CX+D
    private int[] points;//x1,y1,x2,y2,x3,y3...
    private int 
    start,//The X coordinate where the graph is started.
    stop,//The X coordinate where the graph is stopped.
    xShift,//An X value in which to shift the whole graph to the left of right.
    yShift;//An Y value in which to shift the whole graph to the up or down.
    private Color color=Color.BLACK;//color is the current color of the Polynomial.

    public Polynomial(Polynomial p)//Jacob Waters
    {
        coefficients=Arrays.copyOf(p.getCoefficients(),p.getCoefficients().length);
        points=Arrays.copyOf(p.getPoints(),p.getPoints().length);
        start=p.getStart();
        stop=p.getStop();
        xShift=getXShift();
        color=getColor();
    }

    /**
     * This is a constructer designed to construct a Polynomial from file. A .txt File is read, and separated into multiple String[] objects, one for each Polynomial.
     * Then each discreet String[] is fed into this constructer. All the polynomials are then used to instantiate a Level object.
     */
    public Polynomial(String[] info)//Jacob Waters
    {
        int length=Integer.parseInt(info[0]);
        this.coefficients=new double[length];
        for(int i=0;i<length;i++)
        {
            coefficients[i]=Double.parseDouble(info[i+1]);
        }
        points=new int[length*2];
        for(int i=0;i<points.length;i++)
        {
            points[i]=Integer.parseInt(info[i+1+coefficients.length]);
        }
        start=Integer.parseInt(info[1+coefficients.length+points.length]);
        stop=Integer.parseInt(info[2+coefficients.length+points.length]);
        xShift=Integer.parseInt(info[3+coefficients.length+points.length]);
        yShift=Integer.parseInt(info[4+coefficients.length+points.length]);

    }

    /**
     * Primarily for demo purposes, this method prints all the key data held in a Polynomial.
     */
    public void print()//Jacob Waters
    {
        for(int i=0;i<coefficients.length;i++)
        {
            System.out.println((char)(i+'A')+"= "+coefficients[i]);
        }
        System.out.println();
        for(int i=0;i<coefficients.length;i++)
        {
            if(i<coefficients.length-2)
            {
                if(coefficients[i]>0)
                    System.out.print("+"+coefficients[i]+"(X^"+(coefficients.length-i-1)+")");
                else if(coefficients[i]<0)
                    System.out.print(coefficients[i]+"(X^"+(coefficients.length-i-1)+")");
            }
            else if(i==coefficients.length-2)
            {
                if(coefficients[i]>0)
                    System.out.print("+"+coefficients[i]+"X");
                else if(coefficients[i]<0)
                    System.out.print(coefficients[i]+"X");
            }
            else
            {
                if(coefficients[i]>0)
                    System.out.print("+"+coefficients[i]);
                else if(coefficients[i]<0)
                    System.out.print(coefficients[i]);
            }
        }
    }

    /**
     * This sets the bounds of the polynomial. A polynomial is continuous (-infinity,infinity),
     * therefore bounds must be set to determine what part of the function is graphed.
     * 
     * @param start The left bound.
     * @param stop The right bound.
     */
    public void setBounds(int start,int stop)//Jacob Waters
    {
        this.start=start;
        this.stop=stop;
    }

    /**
     * This sets the left bound only.
     * 
     * @param start The left bound.
     */
    public void setStart(int start)//Jacob Waters
    {
        this.start=start;
    }

    /**
     * This sets the right bound only.
     * 
     * @param stop The right bound.
     */
    public void setStop(int stop)//Jacob Waters
    {
        this.stop=stop;
    }

    /**
     * @return Returns the left bound of the graph.
     */
    public int getStart()//Jacob Waters
    {
        return start;
    }

    /**
     * @return Returns the right bound of the graph.
     */
    public int getStop()//Jacob Waters
    {
        return stop;
    }

    /**
     * @return Returns the coefficients that form the Polynomial. If the function is represented by Ax^3+BX^2+CX+D,
     * it returns A,B,C,D in a double[].
     */
    public double[] getCoefficients()//Jacob Waters
    {
        return coefficients;
    }

    /**
     * If the function is represented by Ax^3+BX^2+CX+D, this method replace those A,B,C,D with new values.
     * @param coefficients This will set the A,B,C,D... values to the values found in coefficients.
     */
    public void setCoefficients(double[] coefficients)//Jacob Waters
    {
        this.coefficients=coefficients;
    }

    public int[] getPoints()//Jacob Waters
    {
        return points;
    }

    public void getPoints(int[] points)//Jacob Waters
    {
        this.points= points;
    }

    /**
     * This method sets how much the graph is shifted in the X axis.
     * @param xShift The value to shift the graph by.
     */
    public void setXShift(int xShift)//Jacob Waters
    {
        this.xShift=xShift;
    }

    /**
     * This method changes xShift by xChange pixels. For example if the graph was shifted over 150 pixels, and this method was called with xChange=25
     * the graph would shift over an additional 25 pixels, with a final xShift value of 175.
     * @param xChange The value to shift the graph by.
     */
    public void changeXShift(int xChange)//Jacob Waters
    {
        xShift=xShift+xChange;
    }

    /**
     * @return Returns the amount the graph is shifted by.
     */
    public int getXShift()//Jacob Waters
    {
        return xShift;
    }

    /**
     * This method sets the YShift value, which is used to shift the whole graph up or down.
     * @param yShift The value to shift the graph by.
     */
    public void setYShift(int yShift)//Jacob Waters
    {
        this.yShift=yShift;
    }

    /**
     * This method changes yShift by yChange pixels. For example if the graph was shifted over 150 pixels, and this method was called with yChange=25
     * then the graph would shift down an additional 25 pixels, with a final yShift value of 175. 
     * 
     * Please note that y=0 is at the top of the screen, and that the bottom of the screen is a positive value getHeight(),
     * which can be called from the DrawPanel class.
     * 
     * 
     * @param xChange The value to shift the graph by.
     */
    public void changeYShift(int yChange)//Jacob Waters
    {
        yShift=yShift+yChange;
    }

    /**
     * @return Returns the value the graph is shifted by in the Y axis.
     */
    public int getYShift()//Jacob Waters
    {
        return yShift;
    }

    /**
     * @return Returns the color of this Polynomial.
     */
    public Color getColor()//Jacob Waters
    {
        return color;
    }

    /**
     *Sets the color of this polynomial.
     *@param color The color this Polynomial will be set to.
     */
    public void setColor(Color color)//Jacob Waters
    {
        this.color=color;
    }

    /**
     *Creates a Polynomial from an array of points, a start, and a stop value.
     *@param points The points used to generate the function.
     *@param start Starting value of the funciton.
     *@param stop The right bound of the function.
     */
    public Polynomial(int[] points,int start, int stop)//Jacob Waters
    {
        double[] wrapper=new double[points.length];
        for(int i=0;i<wrapper.length;i++)
        {
            wrapper[i]=(double)points[i];
        }
        coefficients=solve(wrapper);
        this.points=points;
        this.start=start;this.stop=stop;xShift=0;yShift=0;
    }
    public Polynomial(double[] wrapper,int start, int stop)//Jacob Waters
    {
        coefficients=solve(wrapper);
        this.points=points;
        this.start=start;this.stop=stop;xShift=0;yShift=0;
    }

    /**
     *This method takes an array of points, in double format to ensure arithmetic precision, and converts it to the
     *coefficients of a polynomial that intersects the given points. This is most important method in this class.
     *@param points A series of points formatted as an array of doubles.
     */
    public static double[] solve(double[] points)//x1,y1,x2,y2,x3,y3...//Jacob Waters
    {
        int count=points.length/2;//The number of coefficients returned will be equal to the number of points inputted, which is half of points.length since there's one X, and one Y Value for each point.
        Object[] equation=new Object[count];//Object[] can hold arrays. The purpose of this style is to make a 3D array that doesn't require X*Y*Z total indexes.
        for(int i=0;i<count;i++)
        {
            equation[i]=new double[count-i][count+1];
        }//Each index of equation holds a 2D double.
        //If there were 3 points, then count would equal 3, the output of the last for loop is as follows, in this case
        //                    y=ax^2+Bx+x
        //                    
        //                       empty          Theoretical          Actual Given Constants
        //                     [][][][]    [Y1][A*x1^2][B*x1][C*1]    [Y1][x1^2][x1][1]
        //      equations[0]== [][][][] == [Y2][A*x2^2][B*x2][C*1] == [Y1][x1^2][x1][1]                  
        //                     [][][][]    [Y3][A*x3^2][B*x3][C*1]    [Y1][x1^2][x1][1]
        // 
        //      When you multiply the given X^n values times A,B, and C, you get a constant times A, a different constant times B, and no constant times C.        
        //      When this is done on each of the three equations, you find you have a system of equations, with three variables. The next step illustrated is the result of 
        //      Gaussian Elimination. If you're reading this, you probably know how to do Gaussian Elimination and didn't realize it. 
        //      If unfamiliar with this technique do a quick google search before reading on.
        //
        //      equations[1]= [][][][0]// where each of these blank values are found through Gaussian Elimination. Notice C coefficient has been eliminated.
        //                    [][][][0]   
        //         
        //      equations[2] [C1][C2*A][0][0] Now both C, and B have been eliminated by a second iteration of gaussian elimination
        //         
        //      Now solve for A, plug A into equations[1] to solve for B, plug A&B into equations[0] to find C. Now that A,B,C are found, return a double[] cotaining those values.   
        //         
        int degree=(points.length/2)-1;// with points=3 the maximum degree polynomial is 2, ax^2
        for(int i=0;i<points.length;i+=2)//iterates through each X value, skipping Y values
        {
            ((double[][])equation[0])[i/2][0]=points[i+1];//Fancy Casting allows Object to be cast as double[][]
            //Also this previous line instantiates the Y values of each equation. if i correlates to an X value, i+1 correlates to a Y value.
            for(int n=degree;n>=0;n--)
            {
                ((double[][])equation[0])[i/2][degree-n+1]=Math.pow(points[i],n);//Instantiates each horizontal cell after the first to x^n
            }
        }
        for(int i=1;i<count;i++)//The hard part haha. I honestly forgot how this works, if you're interested contact me at jacobwaters10@yahoo.com, and we can re-figure-it-out. It's still pretty hard to grasp the abstraction, even though I made it. 
        {
            for(int n=0;n<((double[][])equation[i]).length;n++)
            {
                for(int a=0;a<count+1;a++)
                {
                    ((double[][])equation[i])[n][a]=(
                        (((double[][])equation[i-1])[n+1][a]/((double[][])equation[i-1])[n+1][i])
                        -(((double[][])equation[i-1])[0][a])/((double[][])equation[i-1])[0][i]);
                }
            }
        }
        double[] returns=new double[count];
        int[] answers=new int[count];
        for(int i=count;i>0;i--)
        {
            double total=0;
            if(i!=count)
            {
                for(int a=i+1;a<=count;a++)
                {
                    total+=((double[][])equation[i-1])[0][a]*returns[a-1];
                }
            }
            returns[i-1]=((((double[][])equation[i-1])[0][0])-total)/((double[][])equation[i-1])[0][i];
        }
        return returns;
    }

    /**
     * Obsolete method. Draws a Polynomial object.
     * @param g This is the Graphics Object that is being used to draw. By accepting a Graphics Object as a
     * parameter it allows the drawing to be done within each individual class, rather than have a superfluous amount of code in the driver.
     */
    public void draw(Graphics g)//Jacob Waters
    {
        g.setColor(color);
        int heightX,heightX2;
        for(int x=start;x<=stop;x++)
        {
            g.drawLine(x+xShift,height(x),x+xShift+1,height(x+1));
        }
    }

    /**
     *  Draws a Polynomial object with respect to a global shift variable.
     * @param g This is the Graphics Object that is being used to draw. By accepting a Graphics Object as a
     * parameter it allows the drawing to be done within each individual class, rather than have a superfluous amount of code in the driver.
     * @param globalX An external variable that is used to shift the graph, used in relation to a moving point of reference.
     * @param globalY Shifts the graph vertically by this value.
     */
    public void globalDraw(Graphics g,int globalX,int globalY)//Jacob Waters
    {
        g.setColor(color);
        int heightX,heightX2;
        for(int x=start;x<=stop;x++)
        {
            g.drawLine(x+xShift+globalX,
                height(x)+globalY,
                x+xShift+1+globalX
            ,height(x+1)+globalY);

            //testing

        }
    }

    /**
     * Returns the height of this Polynomial without respect to any xShift or globalX shifting variables.
     * @param x this is the x in y=f(x)
     * @return returns the y value as a function of the x value.
     */
    public int height(int x)//Jacob Waters
    {
        double heightX=0;
        for(int a=0;a<coefficients.length;a++)
        {
            heightX+=coefficients[a]*Math.pow(x,coefficients.length-a-1);
        }
        if(heightX%1>=.5)
            heightX=((int)heightX)+1+yShift;
        else
            heightX=(int)heightX+yShift;
        return (int)heightX;
    }

    /**
     * returns all X values where F(X)=value
     * @param value a value searched for.
     * @return returns the x coordinates where f(x)=value
     */
    public int[] equals(double value)//Jacob Waters
    {
        double heightX1,heightX2;
        int[] returns=new int[0];
        for(int x=start;x<=stop;x++)
        {
            if(((heightX1=this.doubleHeight(x)-value)>0&&((heightX2=this.doubleHeight(x+1)-value)<0)))
            {
                if(Math.abs(heightX1)>=Math.abs(heightX2))
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x+1+xShift;
                    returns=temp;
                }
                else
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x+xShift;
                    returns=temp;
                }
            }
            else if((heightX1=this.doubleHeight(x)-value)<0&&((heightX2=this.doubleHeight(x+1)-value)>0))
            {
                if(Math.abs(heightX1)>=Math.abs(heightX2))
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x+1+xShift;
                    returns=temp;
                }
                else
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x+xShift;
                    returns=temp;
                }
            }
            else if((heightX1=this.doubleHeight(x)-value)==0)
            {
                int[] temp=new int[returns.length+1];
                for(int i=0;i<returns.length;i++)
                {
                    temp[i]=returns[i];
                }
                temp[temp.length-1]=x+1+xShift;
                returns=temp;
            }
        }
        return returns;
    }

    /**
     * Returns the height with double precision.
     * @param x value inputted
     * @return returns the y value as a function of x.
     */
    public double doubleHeight(int x)//Jacob Waters
    {
        double heightX=0;
        for(int a=0;a<coefficients.length;a++)
        {
            heightX+=coefficients[a]*Math.pow(x,coefficients.length-a-1);
        }
        return heightX;
    }

    /**
     * This method was designed to be used in conjustion with the Level.save() method.
     * The returned String[] is appended to a .txt file that contains all the Polynomials in the Level object.
     * 
     *
     * @return      This returns a list of String that contains all the data held in a Polynomial. 
     */

    public String[] save()//Jacob Waters
    {
        String[] returns=new String[coefficients.length*3+5]; 
        returns[0]=""+coefficients.length;
        for(int i=0;i<coefficients.length;i++)
        {
            returns[i+1]=""+coefficients[i];
        }
        for(int i=0;i<points.length;i++)
        {
            returns[i+1+coefficients.length]=""+points[i];
        }
        returns[1+coefficients.length+points.length]=""+start;
        returns[2+coefficients.length+points.length]=""+stop;
        returns[3+coefficients.length+points.length]=""+xShift;
        returns[4+coefficients.length+points.length]=""+yShift;
        return returns;
    }

}