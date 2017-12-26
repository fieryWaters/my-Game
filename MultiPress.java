
public class MultiPress extends Thread implements Runnable
{
    DrawPanel d;
    public MultiPress(DrawPanel d)
    {
        this.d=d;
    }

    public void run()
    {
        while(!d.currentKeys.isEmpty())
            try{
                if(d.currentKeys.contains("T")&&d.currentKeys.contains("F"))
                {
                    this.sleep(3);
                    d.globalY+=3;
                    if(d.globalX<=-3)
                        d.globalX+=3;
                    else if(d.globalX!=0)
                        d.globalX=0;

                }
                else if(d.currentKeys.contains("T")&&d.currentKeys.contains("H"))
                {
                    this.sleep(3);
                    d.globalY+=3;
                    d.globalX-=3;

                }
                else if(d.currentKeys.contains("G")&&d.currentKeys.contains("F"))
                {
                    this.sleep(3);
                    d.globalY-=3;
                    if(d.globalX<=-3)
                        d.globalX+=3;
                    else if(d.globalX!=0)
                        d.globalX=0;

                }
                else if(d.currentKeys.contains("G")&&d.currentKeys.contains("H"))
                {
                    this.sleep(3);
                    d.globalY-=3;
                    d.globalX-=3;

                }
                else if(d.currentKeys.contains("T"))
                {
                    this.sleep(3);
                    d.globalY+=3;

                }
                else if(d.currentKeys.contains("G"))
                {
                    this.sleep(3);
                    d.globalY-=3;

                }
                else if(d.currentKeys.contains("F"))
                {
                    this.sleep(3);
                    if(d.globalX<=-3)
                        d.globalX+=3;
                    else if(d.globalX!=0)
                        d.globalX=0;

                }
                else if(d.currentKeys.contains("H"))
                {
                    this.sleep(3);
                    d.globalX-=3;

                }
            }
            catch(Exception e)
            {

            }
    }
}