import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*;
/**
 * This program traces and records mouse movement during drag
 * (moving mouse when mouse key is pressed and held down).
 * It then draws lines to show the path of the mouse.
 */
public class Scribble extends JPanel implements MouseListener, MouseMotionListener {//David Wisneski

    private int[] x;      // lists of (x,y) pairs of coordinates that are the mouse path
    private int[] y; 
    private int count;    // number of valid elements in the x and y arrays

    public Scribble() {//David Wisneski
        x = new int[10000];
        y = new int[10000];
        count = 0;
        addMouseListener(this);          // required for press and release events
        addMouseMotionListener(this);    // required for move and drag events
    }

    public void paint(Graphics g) { //David Wisneski
        // g.clearRect(0, 0, getWidth(), getHeight());  // clear window
        
        // draw the lines from the points in the arrays
        // a value -1 means the line is disconnected
        for (int i=0; i<count-1; i++) {
            if (x[i]!=-1 && x[i+1]!=-1) {
                g.drawLine(x[i], y[i], x[i+1], y[i+1]);
            }
        }
    } 
    //  mouse event methods 
    public void mousePressed(MouseEvent e){//David Wisneski
        // record that mouse key is pressed, we are starting a new path
        addPoint(-1, -1);
        addPoint(e);
        repaint();
    }

    public void mouseReleased(MouseEvent e){//David Wisneski
        // record that we are ending a line
        addPoint(e);
        repaint();
    }

    public void mouseClicked(MouseEvent e){}//David Wisneski

    public void mouseEntered(MouseEvent e){}//David Wisneski

    public void mouseExited(MouseEvent e){}//David Wisneski

    // mouse motion methods
    public void mouseDragged(MouseEvent e) {//David Wisneski
        addPoint(e);
        repaint();
    }

    public void mouseMoved(MouseEvent e) { }//David Wisneski

    //  helper method
    public void addPoint(int x, int y) {//David Wisneski
        //  add point with coordinates (x,y) to arrays
        //  if array is full, then do nothing.
        if (count < 10000) {
            this.x[count] = x;
            this.y[count] = y;
            count++;
        }
    }
    
    //  example of overloaded method
    public void addPoint(MouseEvent me) {//David Wisneski
        addPoint( me.getX(), me.getY() );
    }

    public static void main(String[] args) { //David Wisneski
        // make the frame , add the application and show it.
        JFrame frame = new JFrame(); 
        Scribble canvas = new Scribble();  
        frame.setVisible(true); 
        // add the application program canvas 
        frame.setSize(600, 600);
        frame.getContentPane().add(canvas);         
    } 
}
