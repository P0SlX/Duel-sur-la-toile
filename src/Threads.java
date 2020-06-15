import javafx.application.Platform;

public class Threads extends java.lang.Thread {
    private Player loggedPlayer;
    private volatile boolean running = true;

    public void stopThread() {
        this.running = false;
    }

    @Override
    public void run() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        this.loggedPlayer = Controller.getLoggedPlayer();

        while (running) {
            try {


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
