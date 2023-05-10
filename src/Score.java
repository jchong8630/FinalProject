import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Score {
    private ArrayList<Integer> wpm;
    private ArrayList<Double> accuracy;

    private ArrayList<String> scores;

    public Score() throws FileNotFoundException {
        wpm = new ArrayList<>();
        accuracy = new ArrayList<>();
        scores = TextDatabaseCreator.getTextDB("src/PlayerScores.txt");
        for (int i = 0; i < scores.size(); i++){
            String[] score = scores.get(i).split(" ");
            wpm.add(Integer.parseInt(score[0]));
            accuracy.add(Double.parseDouble(score[4].substring(0, score[4].length() - 2)));
        }
    }

    public void addWPM(int wpm){
        this.wpm.add(wpm);
    }

    public void addAccuracy(double acc){
        accuracy.add(acc);
    }

    public String printScore(){
        String result = "";
        for (int i = 0; i < wpm.size(); i++){
            result += wpm.get(i) + " WPM   " + String.format("%.02f",accuracy.get(i)) + "%\n";
        }
        return result;
    }

    public double getAvgWPM(){
        double total = 0.0;
        for (int w : wpm){
            total += w;
        }
        return total / wpm.size();
    }

    public double getAvgAccuracy(){
        double total = 0.0;
        for (double a : accuracy){
            total += a;
        }
        return total / accuracy.size();
    }
}
