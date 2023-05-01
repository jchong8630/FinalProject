import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Locale;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;


public class TextDatabaseCreator {
    public static ArrayList<String> getTextDB(String fileName) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Scanner s = new Scanner(new File(fileName));
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
            s.close();
        }
        catch (IOException ie){

        }
        return list;
    }
}
