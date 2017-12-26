
/**
 * Write a description of class Wheel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.Graphics;
import java.awt.Color;
public class Wheel
{
    public double xV, yV;
    Polynomial motion;
    int h,k,r,gravity;
    long startTime;
    double collide=10;


    public Wheel(double xV,double yV, int h, int k,int radius)
    {
        this.xV=xV;
        this.yV=yV;
        this.h=h;
        this.k=k;
        r=radius;
        startTime=System.currentTimeMillis();
        gravity=50;//300 is ideal

        double[] coefficients={doublePolynomialX(0),doublePolynomialY(0),doublePolynomialX(1),doublePolynomialY(1),doublePolynomialX(2),doublePolynomialY(2)};
        if(xV!=0)
            motion=new Polynomial(coefficients,-2000,10000);//testing x and y values need to be modulated more intelligently...setting the limit to 
    }

    public Wheel(double xV,double yV, int h, int k,int radius,int gravity)
    {
        this.xV=xV;
        this.yV=yV;
        this.h=h;
        this.k=k;
        r=radius;
        startTime=System.currentTimeMillis();
        this.gravity=gravity;
    }

    public static Wheel polar(double r,double theta, int h, int k,int radius)
    {
        return new Wheel(r*Math.cos(theta),r*Math.sin(theta),h,k,radius);
    }

    public int x(double t)
    {
        return (int)Math.round(xV*t)-r+h;
    }

    public double t(double x)
    {
        return (x-h+r)/xV;
    }

    public double doublePolynomialX(double t)
    {
        return (xV*t)+h;
    }

    public int y(double t)
    {
        return (int)Math.round((gravity*Math.pow(t,2))-yV*t-r+k);
    }

    public double doublePolynomialY(double t)
    {
        return (gravity*Math.pow(t,2))-yV*t+k;
    }

    public int y(int x)
    {
        return motion.height(x);
    }

    public int y(int x,int globalX,int globalY)
    {
        return motion.height(x)+globalY;
    }

    public void globalDraw(Graphics g, int globalX, int globalY)
    {
        double t=(((double)System.currentTimeMillis()-(double)startTime)/1000);
        g.drawOval(x(t)+globalX,y(t)+globalY,2*r,2*r);
        if(xV!=0)//xV is asymtotically close to 0, xv!=0 returns true
        {
            motion.globalDraw(g,globalX,globalY);// this 
        }
        else
        {
            g.drawLine(x(t)+globalX,0,x(t)+globalX,1000);
        }

    }

    public static double theta(double y,double x)
    {
        return (Math.PI/2)-((x/Math.abs(x))*(Math.PI/2))+Math.atan(y/x);
    }
}
