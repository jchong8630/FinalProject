import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        TypeRacer t = new TypeRacer("Easy");
        int p = (int) (Math.random() * t.getPrompts().size());
        int j = (int) (Math.random() * 20);
        for (int i = 3; i > 0; i --){
            System.out.println(i);
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(t.printPrompt(j));
        double start = LocalTime.now().toNanoOfDay();
        Scanner s = new Scanner(System.in);
        String typedWords = s.nextLine();
        double end = LocalTime.now().toNanoOfDay();
        double elapsedTime = (end - start) / 1000000000.0;
        double accuracy = t.getAccuracy(t.getPrompts().get(j), typedWords);
        System.out.println(t.getWPM(typedWords, elapsedTime,accuracy ) + " WPM");
        System.out.println(String.format("%.02f",accuracy) + "%");
    }


}
