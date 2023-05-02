import java.io.FileNotFoundException;
import java.util.*;

public class TypeRacer {
    private ArrayList<String> prompts;
    private String mode;

    public TypeRacer(String mode) throws FileNotFoundException {
        prompts = new ArrayList<>();
        prompts = TextDatabaseCreator.getTextDB("src/TextDatabase");
    }

    public ArrayList<String> getPrompts() {
        return prompts;
    }

    public String printPrompt(int idx){
        String prompt = prompts.get(idx);
        String result = "";
        int count = 0;
        for (int i = 0; i < prompt.length(); i++){
            if (prompt.charAt(i) == ' '){
                count++;
            }
            if ((count % 20 == 0) && (count != 0)){
                result += prompt.charAt(i) + "\n";
                count++;
            }
            else {
                result += prompt.charAt(i) + "";
            }
        }
        return result;

    }

    public int getWPM(String t, double time, double accuracy){
        int numChars = t.length();
        int wpm = (int) ((((double)numChars / 4.7) / time) * 60);
        return (int) (wpm * (accuracy / 100));
    }

    public double getAccuracy(String typed, String prompt){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,.?' ";
        int[] promptLetterCount  = new int[57];
        int[] typedLetterCount = new int[57];
        int diff = 0;
        for (int i = 0; i <  prompt.length(); i++){
            int idx = letters.indexOf(prompt.substring(i, i + 1));
            if (idx != -1){
                promptLetterCount[idx] += 1;
            }
        }
        for (int i = 0; i <  typed.length(); i++){
            int idx = letters.indexOf(typed.substring(i, i + 1));
            if (idx != -1) {
                typedLetterCount[idx] += 1;
            }
        }
        for (int i = 0; i < promptLetterCount.length; i++){
            diff += Math.abs(promptLetterCount[i] - typedLetterCount[i]);
        }
        double accuracy = (double) (prompt.length() - diff) / prompt.length() * 100;
        if (accuracy < 0){
            return 0;
        }
        return accuracy;
    }

}
