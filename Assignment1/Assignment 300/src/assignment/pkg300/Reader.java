package assignment.pkg300;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *Opens the file and reads the next line.
 * @author SamTheTurdBurgler
 */
public class Reader {
    
    private static Scanner sc;
    
    public Reader(String name) throws FileNotFoundException {
        sc = new Scanner(new File(name));
    }
    
    public static String next() {
        if(hasNext()) return sc.next();
        else return "";
    }
    
    public static boolean hasNext() {
        return sc.hasNext();
    }
}
