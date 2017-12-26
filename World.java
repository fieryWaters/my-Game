/**
 * The program allows a user to draw and move
 * figures which are oval, happy face and rectangle.
 * To draw a circle, type "c", then drag mouse to create oval
 * To draw a rectangle, type "r", then drag mouse
 * To draw a happy face, type "h", then drag mouse
 * To move an object, type "s", then press mouse key on object and drag.
 * To delete an object, click on the object, then type "d".
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
public class World extends JPanel 
implements MouseListener, MouseMotionListener, KeyListener 
{
    static String levelName;
    static Level level;
    int globalX=0, globalY=0;
    public static void main()//Jacob Waters, David Wisneski
    {
        System.out.println("What level do you want to play?");
        Scanner scan=new Scanner(System.in);
        levelName=scan.nextLine();
        level=new Level(new File(levelName+".txt"));
        JFrame window = new JFrame();
        window.setSize(1080,720);
        window.setTitle("World");
        World panel = new World();  // window content pane to hold the draw and tool panels

        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public World( ){//David Wisneski
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g){//Jacob Waters, David Wisneski
        g.clearRect(0, 0, getWidth(), getHeight());
        level.refreshBounds(globalX,getWidth());
        level.globalDraw(g,globalX,globalY);
    }

    public void mousePressed(MouseEvent e){//David Wisneski
        requestFocus();  
    }

    public void mouseReleased(MouseEvent e){//David Wisneski

    }

    public void mouseClicked(MouseEvent e){//David Wisneski
    }

    public void mouseEntered(MouseEvent e){//David Wisneski
    }

    public void mouseExited(MouseEvent e){//David Wisneski
    }

    public void mouseDragged(MouseEvent e){//David Wisneski

    }

    public void mouseMoved(MouseEvent e){//David Wisneski
    }

    public void keyPressed(KeyEvent e){//David Wisneski
        int key = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e){//David Wisneski
    }

    public void keyTyped(KeyEvent e){//David Wisneski
    }

    public void save(String levelName)//Jacob Waters
    {        
        BufferedWriter output = null;

        try
        {
            output = new BufferedWriter(new FileWriter(levelName+".txt",false));
            output.append("");
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

    public void save(Level level)//Jacob Waters
    {
        //         Scanner scan=new Scanner(System.in);
        //         System.out.println("What is the Level Name?");
        //         String levelName=scan.nextLine();
        BufferedWriter output = null;
        try
        {
            output = new BufferedWriter(new FileWriter("level1.txt",false));
            for(int i=0;i<level.length();i++)
            {
                for(String a:level.get(i).save())
                {
                    output.append(a);
                    output.newLine();
                }
            }
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
