import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Currency;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class MainScreen implements Runnable, ActionListener, KeyListener {
    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private JLabel prompt;
    private Thread gameThread;

    private boolean checker;
    private String promptText;
    private JTextField typingArea;
    private JLabel score;
    private Score sc;
    private TypeRacer t;
    private boolean alreadyStarted;
    private double timeStart;
    private double timeEnd;


    public MainScreen() throws FileNotFoundException {
        sc = new Score();
        t = new TypeRacer(30, sc);
        frame = new JFrame();
        panel = new JPanel();
        start = new JButton("Start");
        score = new JLabel();
        start.addActionListener(this);
        typingArea = new JTextField(20);
        typingArea.addKeyListener(this);
        prompt = new JLabel();
        prompt.setPreferredSize(new Dimension(900, 100));
        panel.setBorder(BorderFactory.createEmptyBorder(500, 500, 500, 500));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(prompt);
        panel.add(start);
        panel.add(typingArea);
        panel.add(score);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CowType");
        frame.pack();
        frame.setVisible(true);
        checker = false;
        alreadyStarted = false;
    }

//    public static void main(String[] args) throws IOException, InterruptedException, AWTException {
//        Score sc = new Score();
//        Scanner s = new Scanner(System.in);
//        System.out.println("Welcome to CowType!");
//        boolean check = true;
//        while(check){
//            System.out.print("How many word would you like your prompt to have? ");
//            int words = Integer.parseInt(s.nextLine());
//            TypeRacer t = new TypeRacer(words, sc);
//            for (int i = 3; i > 0; i --){
//                System.out.println(i);
//                TimeUnit.SECONDS.sleep(1);
//            }
//            System.out.println(t.printPrompt());
//            double start = LocalTime.now().toNanoOfDay();
//            String typedWords = s.nextLine();
//            double end = LocalTime.now().toNanoOfDay();
//            double elapsedTime = (end - start) / 1000000000.0;
//            double accuracy = t.getAccuracy(typedWords);
//            int wpm = t.getWPM(typedWords, elapsedTime,accuracy);
//            System.out.println(wpm + " WPM   " + String.format("%.02f", accuracy) + "%");
//            System.out.print("Continue? (y/n) ");
//            check = s.nextLine().equalsIgnoreCase("y");
//            System.out.println();
//        }
//
//        System.out.println("\nScore:\n" + sc.printScore());
//        System.out.println("Avg WPM: " + String.format("%.02f", sc.getAvgWPM()) + "  Avg Accuracy: " + String.format("%.02f", sc.getAvgAccuracy()) + "%");
//        try
//        {
//            String filename= "src/PlayerScores.txt";
//            FileWriter fw = new FileWriter(filename,false); //the true will append the new data
//            fw.write(sc.printScore());//appends the string to the file
//            fw.flush();
//            fw.close();
//        }
//        catch(IOException ioe)
//        {
//            System.err.println("IOException: " + ioe.getMessage());
//        }
//    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        promptText = t.printPrompt();
        prompt.setText(promptText);
        if (source.getText().equals("Start")) {
            System.out.println("Test");
            checker = true;
            typingArea.setVisible(true);
            score.setText(sc.printScore() + " ");
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            timeEnd = LocalTime.now().toNanoOfDay();
            typingArea.setVisible(false);
            double accuracy = t.getAccuracy(typingArea.getText());
            double elapsedTime = (timeEnd - timeStart) / 1000000000.0;
            prompt.setText("WPM: " + t.getWPM(typingArea.getText(), elapsedTime, accuracy) + "  Accuracy: " + String.format("%.2f", accuracy)+ "%");
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

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (gameThread != null) {
            if (checker == true) {
                try {
                    timer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void timer() throws InterruptedException {
        prompt.setText("3");
        TimeUnit.SECONDS.sleep(1);
        prompt.setText("2");
        TimeUnit.SECONDS.sleep(1);
        prompt.setText("1");
        TimeUnit.SECONDS.sleep(1);
        prompt.setText(promptText);
        TimeUnit.SECONDS.sleep(1);
        checker = false;
        timeStart = LocalTime.now().toNanoOfDay();
    }
}
