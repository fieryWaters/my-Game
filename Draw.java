import java.awt.*;
/**
 * All figures supported by drawing program must
 * implement this interface.
 */
public interface Draw {
    /**
     * The object should draw itself using the 
     * supplied graphics instance
     * @param Graphics instance
     */
    public void draw(Graphics g);//David Wisneski
    public void globalDraw(Graphics g, int globalX,int globalY);//Jacob Waters
    /**
     *draws the object in respect to the x,y coordinates of a point of reference
     */
    /**
     * The object's (x,y) starting coordintes are changed 
     * to the new values (x, y)
     * @param new value for x
     * @param new value for y
     */
    
    public void setStart(double x, double y);//David Wisneski
    /**
     * The object's ending (x,y) coordinates are changed.
     * The meaning of ending (x,y) depends on the particular object.
     * @param new value for x
     * @param new value for y
     */
    public void setEnd (double x, double y);//David Wisneski
    /**
     * move the object dx pixels to the right. If dx is negative
     * move the object to the left.  Move the object down dy 
     * pixels, or up if dy is negative.
     * @param dx pixel units 
     * @param dy pixel units
     */
    public void move(double dx, double dy);//David Wisneski
    /** 
     * If the coordinate (x,y) lies inside or on the object, return
     * true, false otherwise.
     * @param x coordinate
     * @Param y coordinate
     * @return true if (x,y) is inside or on the object.
     */
    public boolean contains (double x, double y);//David Wisneski
    
    public double getX();//David Wisneski
    public double getY();//David Wisneski
    public double getWidth();//David Wisneski
    public double getHeight();//David Wisneski
    
   
    
    public void setColor(Color color);//Jacob Waters
    public Color getColor();//Jacob Waters
}
