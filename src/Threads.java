import java.io.IOException;
import java.sql.SQLException;

public class Threads extends java.lang.Thread {
    private volatile boolean running = true;

    public void stopThread() {
        this.running = false;
    }

    @Override
    public void run() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        while (running) {
            try {
                Thread.sleep(500);
                // COOOOOOOODE
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
