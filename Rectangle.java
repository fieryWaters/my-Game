import java.awt.*;
/**
 * draw a rectangle containing optional text string
 */
public class Rectangle implements Draw{
    Color color;
    double width;
    double height;
    double x, y;

    public void invert()//Jacob Waters
    {
        if(width<0)
        {
            x+=width;
            width*=-1;
        }
        if(height<0)
        {
            y+=height;
            height*=-1;
        }
    }

    /**
     * create a zero size rectangle
     */
    public Rectangle() {//David Wisneski
        width=0.0;
        height=0.0;
        x=y=0;
        color=Color.black;
    }

    /**
     * create a rectangle of given side at location (0,0)
     * @param width
     * @param height
     */
    public Rectangle(double width, double height){//David Wisneski
        this.width=width;
        this.height=height;
        x=0;
        y=0;
    }

    /**
     * create a rectangel of given size at location (x,y)
     * @param x location
     * @param y location
     * @param width
     * @param height
     */
    public Rectangle(double x, double y, double width, double height){//David Wisneski
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public Rectangle(double x, double y, double width, double height, Color color){//David Wisneski
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.color=color;
    }

    /**
     * determine if rectangle contains a location (x,y)
     * @param x
     * @param y
     * @return true if (x,y) is inside on on the border of rectangle
     */
    public boolean contains(double mx, double my){//David Wisneski
        if (mx>=x && mx<x+width && my>y && my<=y+height) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * change position of rectangle
     * @param new x location
     * @param new y location
     */
    public void setStart(double x, double y){//David Wisneski
        this.x=x;
        this.y=y;
    }

    public void setEnd(double x, double y) {//David Wisneski
        width = x-this.x;
        height = y-this.y;
    }

    /**
     * shift position of rectangle
     * @param dx number of pixels to shift right (dx is positive) or left (dx is negative)
     * @param dy number of pixels to shift down (dy is positive) or up (dy is negative)
     */
    public void move(double dx, double dy){//David Wisneski
        setStart(x+dx, y+dy);
    }

    /**
     * draw the rectangle
     * @param Graphics object of window
     */
    public void draw(Graphics g){//David Wisneski
        g.setColor(color);
        g.drawRect((int)x, (int)y, (int)width, (int)height);
    }

    public void globalDraw(Graphics g, int globalX,int globalY)
    {
        g.drawRect((int)x+globalX, (int)y+globalY, (int)width, (int)height);
    }

    /**
     * get current width of rectangle
     * @return width
     */
    public double getWidth() {//David Wisneski
        return width;
    }

    /**
     * get current height of rectangle
     * @return height
     */
    public double getHeight() {//David Wisneski
        return height;
    }

    public double getX()//David Wisneski
    {
        return this.x;
    }

    public double getY()//David Wisneski
    {
        return this.y;
    }

    /** 
     * change width of rectangle
     * @param new value of width
     */
    public void setWidth(double width){//David Wisneski
        this.width = width;
    }

    /** 
     * change height of rectangle
     * @param new value for height
     */
    public void setHeight(double height){//David Wisneski
        this.height = height;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color=color;
    }

}