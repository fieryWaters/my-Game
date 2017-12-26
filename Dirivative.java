
/**
 * Write a description of class Dirivative here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dirivative
{
    double[] coefficients;
    int start,stop;
    /**
     * Constructs a Dirivative of a given polynomial.
     * @param p this object is a divative of Polynomial p
     */
    public Dirivative(Polynomial p)//Jacob Waters
    {
        coefficients=new double[p.getCoefficients().length-1];
        for(int i=0;i<coefficients.length;i++)
        {
            coefficients[i]=p.getCoefficients()[i]*(p.getCoefficients().length-i-1);
        }
        this.start=p.getStart();this.stop=p.getStop();
    }

    /**
     * @return returns the coefficients of this Dirivative I.E. ax^2+bx+c returns a,b,c.
     */
    public double[] getCoefficients()//Jacob Waters
    {
        return coefficients;
    }

    /**
     * Finds all values where f(x)=value
     * @param value The value being searched for.
     */
    public int[] equals(double value)//deal with imprecision issue. ex y=.0001, looking for 0 //Jacob Waters
    {
        double heightX1,heightX2;
        int[] returns=new int[0];
        for(int x=start;x<=stop;x++)
        {
            if(((heightX1=this.height(x)-value)>0&&((heightX2=this.height(x+1)-value)<0)))
            {
                if(Math.abs(heightX1)>=Math.abs(heightX2))
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x+1;
                    returns=temp;
                }
                else
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x;
                    returns=temp;
                }
            }
            else if((heightX1=this.height(x)-value)<0&&((heightX2=this.height(x+1)-value)>0))
            {
                if(Math.abs(heightX1)>=Math.abs(heightX2))
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x+1;
                    returns=temp;
                }
                else
                {
                    int[] temp=new int[returns.length+1];
                    for(int i=0;i<returns.length;i++)
                    {
                        temp[i]=returns[i];
                    }
                    temp[temp.length-1]=x;
                    returns=temp;
                }
            }
            else if((heightX1=this.height(x)-value)==0)
            {
                int[] temp=new int[returns.length+1];
                for(int i=0;i<returns.length;i++)
                {
                    temp[i]=returns[i];
                }
                temp[temp.length-1]=x+1;
                returns=temp;
            }
        }
        return returns;
    }

    /**
     * returns the slope at point x on the original function, with emphasis on double pricision.
     * It's important to realize that a slope can hold a wide range of values. An int does not suffice.
     * @param x the X point on the original function.
     * @return the slope at point x of the original Polynomial.
     */
    public double height(int x)//Jacob Waters
    {
        double heightX=0;
        for(int a=0;a<coefficients.length;a++)
        {
            heightX+=coefficients[a]*Math.pow(x,coefficients.length-a-1);
        }
        return heightX;
    }

    /**
     * Prints a summary of the important data in a Dirivative.
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
}
