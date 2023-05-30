import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TextUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainGui extends JFrame implements Runnable, ActionListener, KeyListener, ChangeListener, MouseListener {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel prompt;
    private JTextField typingArea;
    private JLabel start;
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
    private JPanel typingPanel;
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
        this.setSize(1500, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        // END DO NOT CHANGE

        sc = new Score();
        t = new TypeRacer(30, sc);
        promptText = "";
        checker = false;
        typingArea.setBorder(new LineBorder(new Color(50, 52, 55),2));
        slider.setOpaque(false);
        slider.setBackground(new Color(180, 180, 180));
        slider.setForeground(new Color(226, 183, 20));
        slider.setUI(new JSliderUI(slider));
        start.addMouseListener(this);
        typingArea.addKeyListener(this);
        typingArea.setVisible(false);
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
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel)e.getSource();
        promptText = t.printPrompt();
        prompt.setText(promptText);
        if (source.getText() == "") {
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
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
            prompt.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
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
            wordCount.setText(slider.getValue() + "    ");
            if (slider.getValue() == 0){
                slider.setValue(1);
            }
        }
    }

    public void timer() throws InterruptedException {
        prompt.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
        prompt.setText("3");
        TimeUnit.SECONDS.sleep(1);
        prompt.setText("2");
        TimeUnit.SECONDS.sleep(1);
        prompt.setText("1");
        TimeUnit.SECONDS.sleep(1);
        prompt.setFont(new Font("JetBrains Mono", Font.BOLD, 15));
        if (t.getWords() <= 10){
            prompt.setFont(new Font("JetBrains Mono", Font.BOLD, 30));
        }
        if (t.getWords() <= 20){
            prompt.setFont(new Font("JetBrains Mono", Font.BOLD, 25));
        }
        else if (t.getWords() <= 30){
            prompt.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        }
        else if (t.getWords() <= 35){
            prompt.setFont(new Font("JetBrains Mono", Font.BOLD, 17));
        }
        prompt.setText(promptText);
        checker = false;
        timeStart = LocalTime.now().toNanoOfDay();
    }
}
