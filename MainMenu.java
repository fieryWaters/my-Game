/**
 * DrawPanel is the Driver for my Level Development Engine. While the majority of the code here is Written by me, Jacob Waters, I have Professor David Wisneski to 
 * thank for the skeleton of the project. Originally this project allowed the user to draw a circle after pressing C, a Rectangle after R. 
 * For a copy of this original code email me jwaters8978@mpc.edu. You may wish to implement your own drawing project without all the fluff this one offers. Thanks David!
 * 
 * Currently this program is designed to create a level for a 2D race car game. It's cheif functions are generation of Polynomials that intersect inputted points, connecting those
 * Polynomials in a peicewise function, and writing them to file.
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Iterator;
public class MainMenu extends JPanel 
implements MouseListener, MouseMotionListener, KeyListener 
{
    int width, height;
    Rectangle temp=new Rectangle();
    ArrayList<String> currentKeys=new ArrayList<String>();
    public static void main()
    {

        JFrame window = new JFrame();
        window.setSize(1080,720);
        window.setTitle("Main Menu");
        MainMenu panel = new MainMenu();  // window content pane to hold the draw and tool panels

        window.add(panel);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocus();
        // panel.test();
    }

    public MainMenu( ){//Jacob Waters, David Wisnesky
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        requestFocus();
    }

    public void paintComponent(Graphics g){//Jacob Waters, David Wisnesky
        g.clearRect(0, 0, getWidth(), getHeight());
        width=getWidth();height=getHeight();
        g.drawRect(0,0,100,100);
        g.drawLine(100,100,200,200);
        g.setColor(Color.BLACK);
        Font f = new Font("Dialog", Font.PLAIN, 20);
        g.setFont(f);
        g.drawRect((int)temp.getX(),(int)temp.getY(),(int)temp.getWidth(),(int)temp.getHeight());
        g.drawString("Score: ",(int)(getWidth()*(.9))-100,(int)(getHeight()*.1));
    }

    /**
     * 
     * @param e This holds the data relevant to the mouse press. In this method the only values which are pulled from e are e.getX() and e.getY()
     * Those are the x and y coordinates of the current mouse press.
     */
    public void mousePressed(MouseEvent e){//Jacob Waters, David Wisnesky

        requestFocus();      
        e.getX();e.getY();
        temp=new Rectangle(e.getX(),e.getY(),100,100);
        repaint();

    }

    public void mouseReleased(MouseEvent e){//Jacob Waters, David Wisnesky

        repaint();
    }

    public void mouseClicked(MouseEvent e){//David Wisnesky
    }

    public void mouseEntered(MouseEvent e){//David Wisnesky
    }

    public void mouseExited(MouseEvent e){////David Wisnesky
    }

    public void mouseDragged(MouseEvent e){//Jacob Waters, David Wisnesky

    }

    public void mouseMoved(MouseEvent e){//David Wisnesky
    }

    public void keyPressed(KeyEvent e){//Jacob Waters, David Wisnesky
        int key = e.getKeyCode();
        currentKeys.add(e.getKeyText(key));

        if (key == KeyEvent.VK_D ){

        }
    }

    public void keyReleased(KeyEvent e)//David Wisnesky
    {
        currentKeys.remove(e.getKeyText(e.getKeyCode()));

    }

    public void keyTyped(KeyEvent e){//David Wisnesky
    }

    public void save(String levelName)//Jacob Waters
    {        
        BufferedWriter output = null;
        try
        {
            output = new BufferedWriter(new FileWriter("levelName.txt",false));

            output.append("A String");
            output.newLine();

        }
        catch ( IOException e)
        {
            System.out.println(e);
        }

        try
        {
            output.close();
        }
        catch ( IOException e)
        {
            System.out.println(e);
        }
    }

}
