import javax.swing.*;
import java.io.FileNotFoundException;

public class WindowInitializer {
    public WindowInitializer() throws FileNotFoundException {

        MainGui mainGui = new MainGui();
        mainGui.startGameThread();
    }
}
