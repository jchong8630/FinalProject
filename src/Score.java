import java.util.ArrayList;

public class Score {
    private ArrayList<Integer> wpm;
    private ArrayList<Double> accuracy;

    public Score(){
        wpm = new ArrayList<>();
        accuracy = new ArrayList<>();
    }

    public void addWPM(int wpm){
        this.wpm.add(wpm);
    }

    public void addAccuracy(double acc){
        accuracy.add(acc);
    }

    public String printScore(){
        String result = "Score:\n";
        for (int i = 0; i < wpm.size(); i++){
            result += wpm.get(i) + " WPM   " + String.format("%.02f",accuracy.get(i)) + "%\n";
        }
        return result;
    }
}
