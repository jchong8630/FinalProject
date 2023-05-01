import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TypeRacer {
    private ArrayList<String> prompts;
    private String mode;

    public TypeRacer(String mode) throws FileNotFoundException {
        prompts = new ArrayList<>();
        prompts = TextDatabaseCreator.getTextDB("src/TextDatabase");
        Collections.sort(prompts);
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

    public int getWPM(String t, double time){
        int numChars = t.length();
        int wpm = (int) ((((double)numChars / 4.7) / time) * 60);
        return wpm;
    }

    public double getAccuracy(){
        
    }

}
