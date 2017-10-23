package FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author SamTheTurdBurgler
 */
public class Reader {
    private final Scanner sc;
    private String token;
    
    public Reader(String fileName) throws FileNotFoundException {
        sc = new Scanner(new File(fileName));
    }
    
    public String readNext(){
        token = "";
        if(sc.hasNext()) {
            token = sc.next(); 
        }
        return token;
    }
}
