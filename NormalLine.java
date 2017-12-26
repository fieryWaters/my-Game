
/**
 * The NormalLine is designed to create a line perpendicular to a particular point on a Polynomial.
 * 
 */
import java.awt.Graphics;
public class NormalLine
{
    public double slope, b;
    int x,y,start,stop;
    boolean undefined;
    public NormalLine(Polynomial p, int x,int globalX)//Jacob Waters
    {
        Dirivative d=new Dirivative(p);
        //x=x-globalX;//testing
        if(d.height(x-p.getXShift())!=0)
        {
            slope =-1/d.height(x-globalX-p.getXShift());
            b=(-slope*(x-globalX))+p.height(x-globalX-p.getXShift());//error
        }
        else
        {
            this.x=x;
            this.y=p.height(x-globalX-p.getXShift());
            undefined=true;
        }

        //this.x=x;
        //y=p.height(x);
        start=x-20;
        stop=x+20;

    }

    public Polynomial add(Polynomial p)//Jacob Waters
    {
        Polynomial temp =new Polynomial(p);
        temp.getCoefficients()[temp.getCoefficients().length-1]=
        temp.getCoefficients()[temp.getCoefficients().length-1]+b;

        temp.getCoefficients()[temp.getCoefficients().length-2]=
        temp.getCoefficients()[temp.getCoefficients().length-2]+slope;

        return temp;
    }

    public void globalDraw(Graphics g,int globalX,int globalY)//Jacob Waters
    {
        if(!undefined)
        {
            int heightX,heightX2;
            for(int x=start;x<=stop;x++)
            {
                g.drawLine(x+globalX,
                    height(x)+globalY,
                    x+1+globalX
                ,height(x+1)+globalY);
            }
        }else
        {

            g.drawLine(
                x+globalX,
                y+globalY-10,
                x+globalX,
                y+10+globalY);
        }
    }

    public int height(int x)//Jacob Waters
    {
        return (int)(x*slope+b);
    }
}
