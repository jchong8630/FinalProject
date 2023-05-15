import javax.swing.*;
import java.io.FileNotFoundException;

public class WindowInitializer {
    public WindowInitializer() throws FileNotFoundException {

        MainScreen mainScreen = new MainScreen();
        mainScreen.startGameThread();
    }
}
