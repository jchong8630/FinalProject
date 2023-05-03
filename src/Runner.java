import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Score sc = new Score();
        Scanner s = new Scanner(System.in);
        System.out.print("Welcome to CowType!\nWhat mode would you like to play? (t)imed or (w)ord: ");
        String mode = s.nextLine();
        boolean check = true;
        while(check){
            TypeRacer t = new TypeRacer(mode, sc);
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
        }

        System.out.println("\n" + sc.printScore());
    }


}
