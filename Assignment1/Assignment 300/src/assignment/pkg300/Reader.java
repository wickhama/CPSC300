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
    
    /*Constructor(String: name of file)
    Throws FileNotFoundException
    */
    public Reader(String name) throws FileNotFoundException {
        sc = new Scanner(new File(name));
    }
    
    /*next() 
    returns String: next token in file
    */
    public static String next() {
        if(hasNext()) return sc.next();
        else return "";
    }
    
    /*hasNext()
    returns if file has more tokens.
    */
    public static boolean hasNext() {
        return sc.hasNext();
    }
}
