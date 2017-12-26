
/**
 * 
 * This Class is designed to intersect an invisible motion parabola, with this 
 * invisible intersect curve, and return a real time in the future for an intersection,
 * and through deduction: a coordinate where the edge of the Level intersects 
 * the ball, and a coordinate of where the center will be at the returned time.
 * 
 * I created the IntersectCircle class as a utility to intersect a moving circle of radius r
 * with a Level Object. Whenever a circle hits a curve, the center is always a constant
 * distance r from the edge of the circle, and the point of intersection is at the edge.
 * 
 * This class implements a parametric function as its primary mathematical driving force.
 * 
 * I plan to represent the movement of the circle over time as a parabola, where the
 * x,y coordinate is the location of the center of the circle at time T.
 * 
 * The IntersectCircle uses T as an abitrary parametric variable, while the parabola
 * representing the motion of the ball will be connected to real time, and is not arbitrary.
 * 
 * I have done programming in the past that utilizes a constant check method to
 * determine intersections. Not only is this ineficient, it's also inefective, and looks
 * terrible when the ball half goes through a barrior. I want to use an extremly accurate,
 * predicted time, to trigger a collision,or bounce, at the precise time that is should hit.
 * 
 * @author Jacob Waters 
 * @version 6/4/17
 */
import java.awt.Graphics;
import java.awt.Color;
public class IntersectCircle
{
    public Dirivative[] d;
    Level level;
    public Color color;
    double r;

    public IntersectCircle(Level level)//Jacob Waters
    {
        this.level=level;
        d=new Dirivative[level.peices.length];
        for(int i=0;i<level.peices.length;i++)
        {
            d[i]=new Dirivative(level.peices[i]);
        }
        r=1;
    }

    public IntersectCircle(Level level,double r)//Jacob Waters
    {
        this.level=level;
        d=new Dirivative[level.peices.length];
        for(int i=0;i<level.peices.length;i++)
        {
            d[i]=new Dirivative(level.peices[i]);
        }
        this.r=r;
    }

    public void add(Polynomial p)//keeps the intersect circle in sync with the contained Level
    {

        Dirivative[] temp=new Dirivative[d.length+1];
        for(int i=0;i<d.length;i++)
        {
            temp[i]=d[i];
        }
        temp[temp.length-1]=new Dirivative(p);
        d=temp;
    }

    public int[] intersect(Polynomial other)
    {
        return null;
    }

    /**
     * This method returns the x coordinate at any time t. The x coordinate must be in the
     * range of t plus or minus r, excluding endpoints. 
     */
    public int x(int t, int globalX,Polynomial p)//Jacob Waters
    {
        double temp=t+r*Math.cos(thetaL(t, globalX,p))+p.getXShift()+globalX;
        return (int)Math.round(temp);
    }

    /**
     * This method returns the y value of the intersection function at any time T.
     */
    public int y(int t, int globalX, int globalY,Polynomial p)//Jacob Waters
    {
        double temp=p.height(t)+r*Math.sin(thetaL(t,globalX,p))+globalY;
        return (int)Math.round(temp);
    }

    /**
     * This method returns the angle in radians that is found by turning the slope of 
     * the function at T, turning that into an angle, and subtracting pi/2 from it. 
     * Ie turning left pi/2 degrees.
     * Note that because Y values start at the top of the screen and increase towards the
     * bottom, that subtracting pi/2 is a left turn, while in typical math is would be a right turn.
     */
    public double thetaL(int t, int globalX, Polynomial p)//Jacob Waters
    {
        return (-(Math.PI/2)+Math.atan(d[level.indexOf(p)].height(t)));
    }

    /**
     * This method is strictly for testing purposes. 
     */
    public void globalDraw(Graphics g,int globalX,int globalY)//Jacob Waters
    {
        for(int i=level.leftIndex;i<=level.rightIndex;i++)
        {
            g.setColor(level.get(i).getColor());//testing
            for(int t=level.get(i).getStart();t<level.get(i).getStop();t++)
            {
                g.drawLine(
                    x(t,globalX,level.get(i)),
                    y(t,globalX,globalY,level.get(i)),
                    x(t+1,globalX,level.get(i)),
                    y(t+1,globalX,globalY,level.get(i)));
            }
        }

    }
}
