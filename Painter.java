
/**
 * Write a description of class Painter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Painter extends Thread implements Runnable
{
    DrawPanel d;
    public Painter(DrawPanel d)
    {
        this.d=d;
        this.setDaemon(true);
    }

    public void run()
    {
        try{
            while(true)
            {
                d.repaint();
                Thread.sleep(10);
            }
        }
        catch(InterruptedException e)
        {

        }
    }
}
