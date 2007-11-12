/*
 * HangmanClientApp.java
 */

package hangmanclient;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class HangmanClientApp extends SingleFrameApplication {

    private HangmanClientCmd clientCmd;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        HangmanClientView view = new HangmanClientView(this);
        clientCmd = new HangmanClientCmd(view);
        show(view);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of HangmanClientApp
     */
    public static HangmanClientApp getApplication() {
        return Application.getInstance(HangmanClientApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(HangmanClientApp.class, args);
    }
}
