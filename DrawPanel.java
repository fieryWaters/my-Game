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
public class DrawPanel extends JPanel 
implements MouseListener, MouseMotionListener, KeyListener 
{
    public static final int SELECT = 1;    // select mode
    public static final int draw = 2;      // draw mode
    public static final int ERASE = 3;
    public static final int merge = 4;

    int mode;                       //  select or draw mode

    public static final int NONE =0;       // no shape selected
    public static final int polynomial=1;  // Create A Polynomial
    public static final int RECTANGLE = 2; // draw a rectangle
    public static final int CIRCLE = 3;    // draw a circle

    int type;  //  type of figure to draw when in draw mode
    int index=0;
    int pIndex=-1;
    int width;int height;
    ArrayList<Draw> figures;    // list of figures on the panel
    Draw current;               // the current figure selected
    int startDragX, startDragY;     //(x,y) of start of drag operation

    int[] points=new int[0];
    ArrayList<Polynomial> equations=new ArrayList<Polynomial>();
    ArrayList<Draw> circles;

    boolean testing=true;
    int globalX, globalY;
    Object[] totalCircles;

    Level level;
    static String levelName=null;
    static String fileName="level1";
    //NormalLine normalLine;
    Circle temp;

    ArrayList<String> currentKeys=new ArrayList<String>();
    IntersectCircle intersect;

    public long startTime;

    static MultiPress multiPress;

    Wheel motionTest;
    Circle currentIntersect;
    int testX;
    public String testString=""; int eGetX;
    int intersectX,intersectY;
    public static void main()
    {

        //         Scanner scan=new Scanner(System.in);
        //         System.out.println("Would you like to read from file");
        //         String answer=scan.nextLine();
        //         if(answer.equals("yes"))
        //         {
        //             System.out.println("What is the level name?");
        //             levelName=scan.nextLine();
        //         }
        //         System.out.println("Before we start, what level will this be when completed?");
        //         fileName=scan.nextLine();
        levelName="level1";
        JFrame window = new JFrame();
        window.setSize(1080,720);
        window.setTitle(levelName);
        DrawPanel panel = new DrawPanel();  // window content pane to hold the draw and tool panels

        window.add(panel);  

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocus();
        multiPress=new MultiPress(panel);
        multiPress.setDaemon(true);
        Painter paint=new Painter(panel);
        paint.setDaemon(true);
        paint.start();
    }

    public DrawPanel( ){//Jacob Waters
        if(levelName!=null)
        {
            level=new Level(new File(levelName+".txt"));
            intersect=new IntersectCircle(level,10);
            level.scrambleColors();
        }
        figures = new ArrayList<Draw>();
        circles = new ArrayList<Draw>();
        current = null;
        mode = draw;
        type = polynomial;  
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        requestFocus();
    }   

    public void paintComponent(Graphics g){//Jacob Waters, David Wisnesky
        //         int realX=100;//460
        //         Polynomial find;int position;
        //         if((find=level.find(realX,globalX))!=null)
        //             position=realX-find.getXShift()-globalX;
        //System.out.println(level.indexOf(level.find(realX,globalX)));
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawLine(0,0,getWidth(),0);
        width=getWidth();height=getHeight();
        // draw each figure
        for (Draw d: figures){
            d.globalDraw(g,globalX,globalY);
        }
        if(circles!=null)
            for (Draw d: circles){
                d.globalDraw(g,globalX,globalY);
            }
        if(equations!=null)
            for(Polynomial p:equations)
            {
                p.globalDraw(g,globalX,globalY);
            }
        if(totalCircles!=null)
            for(int i=0;i<totalCircles.length;i++)
            {
                if(totalCircles[i]instanceof Object[])
                {
                    for(Circle c:((Circle[])totalCircles[i]))
                    {
                        c.globalDraw(g,globalX,globalY);
                    }
                }
                else
                    ((Circle)totalCircles[i]).globalDraw(g,globalX,globalY);;
            }
        if(level!=null)
        {
            level.refreshBounds(globalX,getWidth());
            level.globalDraw(g,globalX,globalY);
            intersect.globalDraw(g,globalX,globalY);// this is the soon to be invisible curve above the hill. working

            //g.drawOval(position-4+globalX+level.find(realX,globalX).getXShift(),level.find(realX,globalX).height(position)+level.find(realX,globalX).getYShift()+globalY-4,8,8);//testing F(X)
            // g.drawOval(intersect.x(position,globalX,level.find(realX,globalX))-4,intersect.y(position,globalX,globalY,level.find(realX,globalX))-4,8,8);//testing intersectX&Y changeX(T)
            if(motionTest!=null)
            {
                g.drawString("peices[i]="+testString+" e.getX()="+eGetX,(int)(getWidth()-140),(int)(getHeight()*.3));//test useful for comparison to realXCoordinates
            }
        }
        if(System.currentTimeMillis()-startTime<4000)
        {
            Font f = new Font("Dialog", Font.PLAIN, 20);
            g.setFont(f);
            g.drawString("Game Saved",(int)(getWidth()-140),(int)(getHeight()*.1));
        }

        if(motionTest!=null)
        {
            g.setColor(Color.green);
            motionTest.globalDraw(g,globalX,globalY);//This line draws both the parabolic flight path of the ball and the flying ball itself, this is working properly
            g.drawString("Time: "+(((double)System.currentTimeMillis()-(double)motionTest.startTime)/1000),(int)(getWidth()-140),(int)(getHeight()*.1));
            currentIntersect.globalDraw(g,globalX,globalY);//currentIntersect is the ball travling through the air at any point T, this part works great
            //g.drawString("Difference: "+(intersect.y(position,globalX,globalY,level.find(realX,globalX))-motionTest.y(intersect.x(position,0,level.find(realX,globalX)),globalX,globalY)),(int)(getWidth()-140),(int)(getHeight()*.2));//testing gold

            g.setColor(Color.black);
        }
        //         if(normalLine!=null)
        //         {
        //             normalLine.globalDraw(g,globalX,globalY);
        //         }

    }

    /*
     * This works for each polynomial, but starts to degrade as globalX increases in magnitude.
     * I think part of the problem is that, as part of my initial simulation, I use variales such as globalX and realX to describe the process,
     * however looking back, those variables already have purposes, and it probably wasnt good insight to use them.
     * 
     * As far as the nature of the bug goes, it seems clear, if not precicely so,
     * that if say, globalX is 100, the overlap in error will be roughly 100 pixels.
     * I think its comparing the height of of the intersect circle with the wrong part of the level.
     * Without a doubt, the issue is centered entierly around globalX at this point. Go CODING :D
     */
    public int intersect(Wheel motion, IntersectCircle intersect)
    {

        int realX=0;//any X value will suffice if in the bounds of the screen--outside the screen remains untested
        Polynomial find;int position;
        if((find=level.find(realX,globalX))!=null)
        {
            for(int globalX=-motion.h+this.globalX;true;globalX--)
            {
                position=realX-level.find(realX,globalX).getXShift()-globalX;//about to remove -globalX
                if((intersect.y(position,globalX,globalY,level.find(realX,globalX))-motionTest.y(intersect.x(position,0,level.find(realX,globalX)),globalX,globalY))<0)//suspicious 0 where I suspect a globalX should be
                {        
                    return -globalX;
                }
            }
        }
        return 0;
    }

    /**
     * 
     * @param e This holds the data relevant to the mouse press. In this method the only values which are pulled from e are e.getX() and e.getY()
     * Those are the x and y coordinates of the current mouse press.
     * Due to the complexity of this method, refer to comments within the method for more understanding.
     */
    public void mousePressed(MouseEvent e){//Jacob Waters, David Wisnesky
        requestFocus();       // required for key events
        motionTest=Wheel.polar(100,Math.PI/4,e.getX()-globalX,e.getY()-globalY,10);
        testString=""+level.indexOf(level.find(e.getX(),globalX));eGetX=e.getX();

        //
        int x=intersect(motionTest,intersect);//works pretty well so far(this segment of code)
        int realX=x-level.find(x,globalX).getXShift();
        intersectX=intersect.x(realX,globalX,level.find(x,globalX))-globalX-10;
        intersectY=intersect.y(realX,globalX,globalY,level.find(x,globalX))-globalY-10;
        currentIntersect=new Circle(intersectX,intersectY,10);
        //

        requestFocus();       // required for key events
        if (mode == SELECT) {//Obsolete, used for moving Polygons such as circles or squares.
            current = null;
            for(int i=0;i<figures.size();i++)
            {
                if(figures.get(i).contains(e.getX(), e.getY()))
                {
                    current = figures.get(i);

                    startDragX = e.getX();
                    startDragY = e.getY();
                    index=i;
                    break;
                }
            }
            for(int i=0;i<circles.size();i++)
            {
                if(circles.get(i).contains(e.getX(), e.getY()))
                {
                    current = circles.get(i);

                    startDragX = e.getX();
                    startDragY = e.getY();
                    index=i;
                    break;
                }
            }
        } else if (mode == draw) {
            // draw action
            current = null;
            switch (type) {
                case RECTANGLE:{//draws a rectangle, interfaces with the mouseDragged method to finish drawing it.
                    current = new Rectangle(e.getX()+globalX, e.getY()+globalY, 0, 0);
                    if (current != null) {
                        figures.add(current);
                    }
                    break;
                }
                case CIRCLE:{//draws a circle
                    current = new Circle(e.getX(), e.getY(), 0);
                    if (current != null) {
                        figures.add(current);
                    }
                    break;
                }
                case polynomial:{//draws mini circles at the point clicked, and appends those x and y values to a list of points.
                    for(int i=0;i<points.length;i+=2)
                    {
                        if(points[i]==e.getX())
                            return;
                    }
                    int[] swap=new int[points.length+2];
                    for(int i=0;i<points.length;i++)
                    {
                        swap[i]=points[i];
                    }
                    swap[swap.length-2]=e.getX()-globalX;
                    swap[swap.length-1]=e.getY()-globalY;
                    points=swap;
                    if(testing)
                    {
                        circles.add(new Circle(e.getX()-2-globalX,e.getY()-2-globalY,2));
                    }

                    break;
                }
            }
        }else if(mode==merge)//merge mode is triggered by selecting a specific polynomial, or Level.
        {
            if(totalCircles!=null)//if totalCircles.length is 1, that is an invalid value because One references the sellected polynomials and the other(s) are to be chosen
                if(totalCircles.length<2)
                    totalCircles=null;
            if(totalCircles==null)
            {
                if(pIndex>=0)
                {
                    double slope =new Dirivative(equations.get(pIndex)).height(e.getX()-equations.get(pIndex).getXShift()-globalX);
                    Object[] equalsInt=new Object[equations.size()];    
                    for(int i=0;i<equations.size();i++)
                    {
                        if(i!=pIndex)
                        {
                            equalsInt[i]=new Dirivative(equations.get(i)).equals(slope);
                        }
                    }
                    totalCircles=new Object[equalsInt.length];
                    for(int i=0;i<equations.size();i++)
                    {
                        if(i!=pIndex)
                        {
                            totalCircles[i]=new Circle[((int[])equalsInt[i]).length];
                        }
                        else
                        {
                            totalCircles[i]=new Circle(e.getX()-4-globalX,equations.get(i).height(e.getX()-equations.get(i).getXShift()-globalX)-4,4);
                            ((Circle)totalCircles[i]).setColor(Color.RED);
                        }
                    }
                    for(int i=0;i<equations.size();i++)
                    {
                        if(i!=pIndex)
                        {
                            for(int a=0;a<(((Circle[])totalCircles[i]).length);a++)
                            {
                                ((Circle[])totalCircles[i])[a]=new Circle(((int[])equalsInt[i])[a]-4,
                                    equations.get(i).height(((int[])equalsInt[i])[a])-4,
                                    4);
                            }
                        }
                    }
                }
                else if(pIndex==-2)
                {
                    if(e.getX()>=level.getLast().getStart()+level.getLast().getXShift()+globalX&&e.getX()+level.getLast().getXShift()+globalX<=level.getLast().getStop())
                    {
                        double slope =new Dirivative(level.getLast()).height(e.getX()-level.getLast().getXShift()-globalX);
                        Object[] equalsInt=new Object[equations.size()];    
                        for(int i=0;i<equations.size();i++)
                        {
                            equalsInt[i]=new Dirivative(equations.get(i)).equals(slope);
                        }
                        totalCircles=new Object[equalsInt.length+1];
                        for(int i=0;i<equations.size();i++)
                        {
                            totalCircles[i]=new Circle[((int[])equalsInt[i]).length];
                        }
                        totalCircles[totalCircles.length-1]=new Circle(e.getX()-4-globalX,level.getLast().height(e.getX()-level.getLast().getXShift()-globalX)-4,4);
                        ((Circle)totalCircles[totalCircles.length-1]).setColor(Color.RED);
                        for(int i=0;i<equations.size();i++)
                        {
                            for(int a=0;a<(((Circle[])totalCircles[i]).length);a++)
                            {
                                ((Circle[])totalCircles[i])[a]=new Circle(((int[])equalsInt[i])[a]-4,
                                    equations.get(i).height(((int[])equalsInt[i])[a])-4,
                                    4);
                            }
                        }

                    }
                }
            }
            else
            {
                if(pIndex>=0)
                {
                    for(int i=0;i<totalCircles.length;i++)
                    {
                        if(i!=pIndex)
                            for(int a=0;a<(((Circle[])totalCircles[i]).length);a++)
                            {
                                if(((Circle[])totalCircles[i])[a].contains(e.getX()-globalX,e.getY()-globalY))
                                {
                                    equations.get(pIndex).setStop((int)(((Circle)totalCircles[pIndex]).getX()+4-equations.get(pIndex).getXShift()));
                                    equations.get(i).setStart((int)(((Circle[])totalCircles[i])[a].getX()+4-equations.get(i).getXShift()));
                                    equations.get(i).changeXShift((int)((Circle)totalCircles[pIndex]).getX()-(int)(((Circle[])totalCircles[i])[a].getX()));
                                    equations.get(i).changeYShift((int)((Circle)totalCircles[pIndex]).getY()-(int)(((Circle[])totalCircles[i])[a].getY()));
                                    totalCircles=null;
                                    circles.clear();
                                    mode=draw;
                                    if(level==null)
                                    {
                                        level=new Level(equations.get(pIndex),equations.get(i));
                                        if(pIndex>i)
                                        {
                                            equations.remove(pIndex);
                                            equations.remove(i);
                                        }
                                        else
                                        {
                                            equations.remove(i);
                                            equations.remove(pIndex);
                                        }

                                    }
                                    else
                                    {

                                    }
                                    pIndex=-1;
                                    return;
                                }
                            }
                    }
                }
                else if(pIndex==-2)
                {
                    for(int i=0;i<totalCircles.length-1;i++)
                    {
                        for(int a=0;a<(((Circle[])totalCircles[i]).length);a++)
                        {
                            if(((Circle[])totalCircles[i])[a].contains(e.getX()-globalX,e.getY()-globalY))
                            {
                                level.getLast().setStop((int)(((Circle)totalCircles[totalCircles.length-1]).getX()+4-level.getLast().getXShift()));
                                equations.get(i).setStart((int)(((Circle[])totalCircles[i])[a].getX()+4-equations.get(i).getXShift()));
                                equations.get(i).changeXShift((int)((Circle)totalCircles[totalCircles.length-1]).getX()-(int)(((Circle[])totalCircles[i])[a].getX()));
                                equations.get(i).changeYShift((int)((Circle)totalCircles[totalCircles.length-1]).getY()-(int)(((Circle[])totalCircles[i])[a].getY()));
                                totalCircles=null;
                                circles.clear();
                                mode=draw;
                                if(level==null)
                                {
                                    level=new Level(equations.get(pIndex),equations.get(i));
                                    if(pIndex>i)
                                    {
                                        equations.remove(pIndex);
                                        equations.remove(i);
                                    }
                                    else
                                    {
                                        equations.remove(i);
                                        equations.remove(pIndex);
                                    }

                                }
                                else
                                {
                                    level.add(equations.get(i),intersect);
                                    level.setColor(Color.black);
                                    equations.remove(i);
                                }
                                pIndex=-1;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e){//Jacob Waters, David Wisnesky
        if(current instanceof Rectangle)
        {
            ((Rectangle)current).invert();
        }

    }

    public void mouseClicked(MouseEvent e){//David Wisnesky
    }

    public void mouseEntered(MouseEvent e){//David Wisnesky
    }

    public void mouseExited(MouseEvent e){////David Wisnesky
    }

    public void mouseDragged(MouseEvent e){//Jacob Waters, David Wisnesky
        if (mode == SELECT ) {
            // select and drag mode
            if (current!=null){
                current.move(e.getX()-startDragX, e.getY()-startDragY);
                startDragX = e.getX();
                startDragY = e.getY();

            }
        } else if (mode == draw ){
            if (current!=null){
                if(type!=polynomial)
                {
                    current.setEnd(e.getX(), e.getY());

                }
            }
        }
    }

    public void mouseMoved(MouseEvent e){//David Wisnesky
    }

    public void keyPressed(KeyEvent e){//Jacob Waters, David Wisnesky
        int key = e.getKeyCode();
        if(!currentKeys.contains(e.getKeyText(key)))
        {
            currentKeys.add(e.getKeyText(key));
            if(!multiPress.isAlive())
            {
                multiPress=new MultiPress(this);
                multiPress.setDaemon(true);
                multiPress.start();
                multiPress.setName("MultiKeyPress");
            }
        }
        if (key == KeyEvent.VK_D ){
            // delete current object.  If there is one.
            if(type==polynomial)
            {
                if(equations.size()>0)
                {
                    if(pIndex>-1)
                    {
                        equations.remove(pIndex);
                        circles.clear();
                        pIndex=-1;
                        totalCircles=null;

                    }
                }
                if(mode==merge)
                {
                    mode=draw;
                    pIndex=-1;
                }
            }
            else
            {
                if (current!=null) {
                    figures.remove(current);
                    current=null;

                }
            }
        } else if (key == KeyEvent.VK_C) {
            totalCircles=null;
            mode=draw;
            if(pIndex>=0)
            {
                equations.get(pIndex).setColor(Color.black);
            }
            else if(pIndex==-2)
            {
                level.setColor(Color.black);
            }
            pIndex=-1;

        } else if (key == KeyEvent.VK_R) {
            // draw a rectangle
            mode=draw;
            type=RECTANGLE;
        } else if (key == KeyEvent.VK_S) {
            // mode is select
            mode = SELECT;
        } else if(key == KeyEvent.VK_N)
        {
            save(fileName);
            startTime=System.currentTimeMillis();

        }else if(key == KeyEvent.VK_V){
            clean();

        }
        else if(key == KeyEvent.VK_P)
        {
            mode=draw;
            type=polynomial;
            if(totalCircles!=null)
            {
                totalCircles=null;
                equations.get(pIndex).setColor(Color.BLACK);
                pIndex=-1;

            }
        }
        else if(key == KeyEvent.VK_ENTER)
        {
            if(points.length!=0)
            {
                equations.add(new Polynomial(points,-globalX,width-globalX));
                points=new int[0];

            }
        }
        else if(key==KeyEvent.VK_UP)
        {
            if(equations.size()!=0)
            {
                if(pIndex==equations.size()-1)
                {
                    if(level!=null)
                    {
                        equations.get(equations.size()-1).setColor(Color.BLACK);
                        level.setColor(Color.RED);
                        pIndex=-2;
                        mode=merge;

                    }
                    else
                    {
                        pIndex=-1;
                        equations.get(equations.size()-1).setColor(Color.BLACK);
                        mode=draw;

                    }
                }
                else if(pIndex==-1)
                {
                    pIndex++;
                    equations.get(pIndex).setColor(Color.RED);

                    mode=merge;
                }
                else if(pIndex==-2)
                {
                    level.setColor(Color.BLACK);
                    pIndex++;
                    mode=draw;

                }
                else
                {
                    pIndex++;
                    mode=merge;
                    equations.get(pIndex).setColor(Color.RED);
                    equations.get(pIndex-1).setColor(Color.BLACK);

                }
            }
            else
            {
                if(level!=null)
                {
                    if(pIndex==-1)
                    {
                        pIndex=-2;
                        level.setColor(Color.red);

                        mode=merge;
                    }
                    else if(pIndex==-2)
                    {
                        pIndex=-1;
                        level.setColor(Color.black);

                        mode=draw;
                    }
                }
            }
        }else if(key==KeyEvent.VK_DOWN)
        {
            if(equations.size()!=0)
            {
                if(pIndex==0)
                {
                    pIndex--;
                    equations.get(pIndex+1).setColor(Color.BLACK);

                }
                else if(pIndex==equations.size()-1)
                {
                    equations.get(equations.size()-1).setColor(Color.BLACK);
                    pIndex--;  
                    equations.get(pIndex).setColor(Color.RED);

                }
                else if(pIndex==-1)
                {
                    if(level!=null)
                    {
                        pIndex--;
                        level.setColor(Color.RED);

                        mode=merge;
                    }
                    else
                    {
                        pIndex=equations.size()-1;
                        equations.get(equations.size()-1).setColor(Color.RED);
                        mode=merge;

                    }
                }
                else if(pIndex==-2)
                {
                    pIndex=equations.size()-1;
                    level.setColor(Color.BLACK);
                    equations.get(pIndex).setColor(Color.red);

                }
                else
                {
                    pIndex--;
                    equations.get(pIndex).setColor(Color.RED);
                    equations.get(pIndex+1).setColor(Color.BLACK);

                }
            }
            else if(level!=null)
            {
                if(pIndex==-2)
                {
                    pIndex=-1;
                    level.setColor(Color.black);

                }
                else if(pIndex==-1)
                {
                    pIndex=-2;
                    level.setColor(Color.red);

                }
            }
        }
    }

    public void multiPress()
    {
        Thread t=new Thread();
        //         while(currentKeys.contains("T")&&currentKeys.contains("F"))
        //         {
        //             globalY+=10;
        //             globalX+=10;
        //             
        //         }
        //         while(currentKeys.contains("T")&&currentKeys.contains("F"))
        //         {
        //             globalY+=10;
        //             globalX+=10;
        //             
        //         }
        //         while(currentKeys.contains("T")&&currentKeys.contains("F"))
        //         {
        //             globalY+=10;
        //             globalX+=10;
        //             
        //         }
    }

    boolean keyTrigger=true;
    public void keyReleased(KeyEvent e){//David Wisnesky
        currentKeys.remove(e.getKeyText(e.getKeyCode()));
        keyTrigger=false;
    }

    public void keyTyped(KeyEvent e){//David Wisnesky
    }

    public void save(String levelName)//Jacob Waters
    {        
        BufferedWriter output = null;
        if(level!=null)
            try
            {
                output = new BufferedWriter(new FileWriter(levelName+".txt",false));
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

    public void clean()//Jacob Waters
    {
        int i=0;
        while(i<figures.size()){
            Draw shape=figures.get(i);
            if(shape.getWidth()<3||shape.getHeight()<3)
            {
                figures.remove(i);
            }
            else
            {
                i++;
            }
        }
    }
}

// 
//     /*
//      * This works for each polynomial, but starts to degrade as globalX increases in magnitude.
//      * I think part of the problem is that, as part of my initial simulation, I use variales such as globalX and realX to describe the process,
//      * however looking back, those variables already have purposes, and it probably wasnt good insight to use them.
//      * 
//      * As far as the nature of the bug goes, it seems clear, if not precicely so,
//      * that if say, globalX is 100, the overlap in error will be roughly 100 pixels.
//      * I think its comparing the height of of the intersect circle with the wrong part of the level.
//      * Without a doubt, the issue is centered entierly around globalX at this point. Go CODING :D
//      */
//     public int intersect(Wheel motion, IntersectCircle intersect)
//     {
// 
//         int realX=0;//any X value will suffice if in the bounds of the screen--outside the screen remains untested
//         Polynomial find;int position;
//         if((find=level.find(realX,globalX))!=null)
//         {
//             position=realX-level.find(realX,globalX).getXShift()-globalX;
// 
//             for(int globalX=-motion.h+this.globalX;true;globalX--)
//             {
//                 position=realX-level.find(realX,globalX).getXShift()-globalX;//about to remove -globalX
//                 if((intersect.y(position,globalX,globalY,level.find(realX,globalX))-motionTest.y(intersect.x(position,0,level.find(realX,globalX)),globalX,globalY))<0)//suspicious 0 where I suspect a globalX should be
//                 {        
// 
//                     return globalX;
//                 }
//             }
//         }
//         return 0;
//     }
