import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;

public class Runner {
    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
        Score sc = new Score();
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to CowType!");
        boolean check = true;
        while(check){
            System.out.print("How many word would you like your prompt to have? ");
            int words = Integer.parseInt(s.nextLine());
            TypeRacer t = new TypeRacer(words, sc);
            for (int i = 3; i > 0; i --){
                System.out.println(i);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println(t.printPrompt());
            double start = LocalTime.now().toNanoOfDay();
            String typedWords = s.nextLine();
            double end = LocalTime.now().toNanoOfDay();
            double elapsedTime = (end - start) / 1000000000.0;
            double accuracy = t.getAccuracy(typedWords);
            int wpm = t.getWPM(typedWords, elapsedTime,accuracy);
            System.out.println(wpm + " WPM   " + String.format("%.02f", accuracy) + "%");
            System.out.print("Continue? (y/n) ");
            check = s.nextLine().equalsIgnoreCase("y");
            System.out.println();
        }

        System.out.println("\nScore:\n" + sc.printScore());
        System.out.println("Avg WPM: " + String.format("%.02f", sc.getAvgWPM()) + "  Avg Accuracy: " + String.format("%.02f", sc.getAvgAccuracy()) + "%");
        try
        {
            String filename= "src/PlayerScores.txt";
            FileWriter fw = new FileWriter(filename,false); //the true will append the new data
            fw.write(sc.printScore());//appends the string to the file
            fw.flush();
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}


