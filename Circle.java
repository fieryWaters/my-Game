import java.awt.*;
public class Circle implements Draw{

    double radius;              // size of circle
    double x, y;                // coordinates of upper left bounding box for circle
    Color color=Color.black;

    public void setColor(Color color)//Jacob Waters
    {
        this.color=color;
    }

    public Color getColor()//Jacob Waters
    {
        return color;
    }

    public double getY()//David Wisneski
    {
        return y;
    }

    public double getX()//David Wisneski
    {
        return x;
    }

    public double getWidth()//David Wisneski
    {
        return radius;
    }

    public double getHeight()//David Wisneski
    {
        return radius;
    }

    /**
     * create circle with radius and location
     * @param x coordinate of upper right corner of bounding box for circle
     * @param y coordinate of upper right corner of bounding box for cirlce
     * @param radius
     */
    public Circle(double x, double y, double radius){//David Wisneski
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    /**
     * draw the circle and optional text 
     * @param Graphics instance of drawing window
     */
    public void draw(Graphics g){//David Wisneski
        g.setColor(color);
        g.drawOval((int)x,(int)y, (int)(radius*2), (int)(radius*2));
    }

    public void globalDraw(Graphics g, int globalX,int globalY)//Jacob Waters
    {
        g.setColor(color);
        g.drawOval((int)x+globalX,(int)y+globalY, (int)(radius*2), (int)(radius*2));
    }

    /**
     * shift location of circle
     * @param dx number of pixels to shift right (if dx negative, shift left)
     * @param dy number of pixels to shift down (if dy negative, shift up)
     */
    public void move(double dx, double dy){//David Wisneski
        x=x+dx;
        y=y+dy;
    }

    /**
     * set radius 
     * @param new value for radius
     */
    public void setRadius(double radius){//David Wisneski
        this.radius = radius;
    }

    /**
     * get radius value
     * @return radius
     */
    public double getRadius(){////David Wisneski
        return radius;
    }

    /**
     * determine if the coordiante (mx, my) is inside or on circle
     */
    public boolean contains (double mx, double my){//David Wisneski
        double xCenter = x+radius;
        double yCenter = y+radius;
        double distance = Math.sqrt((mx-xCenter)*(mx-xCenter)+(my-yCenter)*(my-yCenter));
        // is distance from (mx, my) to center of circle <= radius
        if (distance <= radius) {
            return true;
        } else {
            return false;
        }
    }

    public void setEnd (double xEnd, double yEnd)
    {//David Wisneski
        double radius1 = (xEnd -x)/2.0;
        double radius2 = (yEnd -y)/2.0;
        if (radius1<radius2) {
            radius=radius2;
        } else {
            radius=radius1;
        }
        if (radius<0) radius=0;
    }

    public void setStart(double xStart, double yStart){//David Wisneski
        x = xStart;
        y=  yStart;
    }


}