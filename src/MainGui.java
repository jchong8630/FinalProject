import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainGui extends JFrame implements Runnable, ActionListener, KeyListener, ChangeListener {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel prompt;
    private JTextField typingArea;
    private JButton start;
    private JLabel title;
    private JLabel scoreLabel;
    private JLabel score1;
    private JLabel score2;
    private JLabel score3;
    private JLabel score4;
    private JLabel score5;
    private JLabel score6;
    private JLabel score7;
    private JLabel score8;
    private JSlider slider;
    private JLabel wordCount;
    private JLabel blank;
    private Thread gameThread;
    private double timeStart;
    private double timeEnd;
    private ArrayList<JLabel> scores;
    private Score sc;
    private TypeRacer t;
    private String promptText;
    private boolean checker;
    public MainGui() throws FileNotFoundException {

        // DO NOT CHANGE
        this.setContentPane(mainPanel);
        this.setTitle("GorillaType");
        this.setSize(1500, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        // END DO NOT CHANGE

        sc = new Score();
        t = new TypeRacer(30, sc);
        promptText = "";
        checker = false;
        start.addActionListener(this);
        typingArea.addKeyListener(this);
        slider.addChangeListener(this);
        t.setWords(slider.getValue());
        scores = new ArrayList<>();
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);
        scores.add(score4);
        scores.add(score5);
        scores.add(score6);
        scores.add(score7);
        scores.add(score8);
        ArrayList<String> s = sc.getScores();
        for (int i = 0; i < scores.size(); i++){
            scores.get(i).setText(s.get(i));
        }
        this.setVisible(true);
    }


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
            typingArea.setText("");
            checker = true;
            typingArea.setVisible(true);
            try {
                sc.setScores();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            ArrayList<String> s = sc.getScores();
            for (int i = 0; i < scores.size(); i++){
                scores.get(i).setText(s.get(i));
            }
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
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        t.setWords(source.getValue());
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
            wordCount.setText(slider.getValue() + "  ");
            if (slider.getValue() == 0){
                slider.setValue(1);
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
