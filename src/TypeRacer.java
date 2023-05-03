import java.io.FileNotFoundException;
import java.util.*;

public class TypeRacer {
    private ArrayList<String> prompts;
    private String mode;
    private Score s;
    private String prompt;

    public TypeRacer(String mode, Score s) throws FileNotFoundException {
        prompts = new ArrayList<>();
        prompts = TextDatabaseCreator.getTextDB("src/TextDatabase");
        this.s = s;
        this.mode = mode;
        int p = (int) (Math.random() * prompts.size());
        prompt = prompts.get(p);
    }

    public ArrayList<String> getPrompts() {
        return prompts;
    }

    public String printPrompt(){
        String result = "";
        String basic = "";
        int idx = prompts.indexOf(prompt);
        int count = 0;
        int length = 0;
        int c = 0;
        int wordCount = getWordCount(prompt);
        if (mode.equals("w")){
            while(wordCount < 20){
                idx += 1;
                prompt = prompts.get(idx);
                wordCount = getWordCount(prompt);
            }
            for (int i = 0; i < prompt.length(); i++){
                if (prompt.charAt(i) == ' '){
                    c++;
                }
                if (c == 20){
                    length = i;
                    break;
                }
            }
            for (int i = 0; i < length; i++){
                if (prompt.charAt(i) == ' '){
                    count++;
                }
                if ((count % 20 == 0) && (count != 0)){
                    result += prompt.charAt(i) + "\n";
                    basic += prompt.charAt(i);
                    count++;
                }
                else {
                    result += prompt.charAt(i) + "";
                    basic += prompt.charAt(i);
                }
            }
        }
        prompt = basic;
        return result;
    }

    public int getWordCount(String p){
        int count = 0;
        for (int i = 0; i < p.length(); i++){
            if (p.charAt(i) == ' '){
                count++;
            }
        }
        return count;
    }

    public int getWPM(String t, double time, double accuracy){
        int numChars = t.length();
        int wpm = (int) ((((double)numChars / 4.7) / time) * 60);
        s.addWPM((int) (wpm * (accuracy / 100)));
        return (int) (wpm * (accuracy / 100));
    }

    public double getAccuracy(String typed){
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
            s.addAccuracy(0);
            return 0;
        }
        s.addAccuracy(accuracy);
        return accuracy;
    }


}
