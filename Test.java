import java.io.File;
public class Test {
    public static void main( String [] args ) {//Jacob Waters
        File actual = new File(".");
        for( File f : actual.listFiles()){
            System.out.println( f.getName() );
        }
    }
}