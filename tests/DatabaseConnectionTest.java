import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void connectPlayer() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        assertTrue(db.connectPlayer("Coco", "cocopops"));
        assertTrue(db.connectPlayer("L'ananas", "jaimeLesPommes"));
        assertFalse(db.connectPlayer("lananas@gmail.com", "j'aimeLesPommes"));

        assertFalse(db.connectPlayer("", ""));
    }

    @Test
    void getPlayer() {

        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("");
        assertTrue(p == null);

        p = db.getPlayer("Coco");

        assertTrue(p.getPseudo().equals("Coco"));
        assertFalse(p.isDesactivated());
        assertFalse(p.isAdmin());
        assertTrue(p.getEmail().equals("cocolastico@gmail.com"));
        assertTrue(p.getEtat() == 0);
        assertTrue(p.getMdp().equals("cocopops"));

    }

    @Test
    void getStatus() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("Coco");
        assertTrue(p != null);

        assertTrue(db.getStatus(p) == 0); // Disconnected
        db.setStatus(p, 1);
        assertTrue(db.getStatus(p) == 1);

        db.setStatus(p, 0);
    }

    @Test
    void setStatus() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("Coco");
        assertTrue(p != null);

        assertTrue(db.getStatus(p) == 0);
        db.setStatus(p, 1);
        assertTrue(db.getStatus(p) == 1);
        db.setStatus(p, 0);
        assertTrue(db.getStatus(p) == 0);
    }

    @Test
    void getPlayerMessage() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player ananas = db.getPlayer("L'ananas");
        Player posix  = db.getPlayer("p0slx");
        Player chef  = db.getPlayer("Le chef");

        ArrayList<Message> messages = db.getPlayerMessage(ananas, posix);
        assertTrue(messages.size() == 2);

        messages = db.getPlayerMessage(ananas, chef);
        assertTrue(messages.size() == 1);
    }
}