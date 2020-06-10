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
        assertNull(p);

        p = db.getPlayer("Coco");

        assertEquals(p.getPseudo(), "Coco");
        assertFalse(p.isDesactivated());
        assertFalse(p.isAdmin());
        assertEquals(p.getEmail(), "cocolastico@gmail.com");
        assertEquals(p.getEtat(), 0);
        assertEquals(p.getMdp(), "cocopops");

    }

    @Test
    void getStatus() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("Coco");
        assertNotNull(p);

        assertEquals(db.getStatus(p), 0); // Disconnected
        db.setStatus(p, 1);
        assertEquals(db.getStatus(p), 1);

        db.setStatus(p, 0);
    }

    @Test
    void setStatus() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("Coco");
        assertNotNull(p);

        assertEquals(db.getStatus(p), 0);
        db.setStatus(p, 1);
        assertEquals(db.getStatus(p), 1);
        db.setStatus(p, 0);
        assertEquals(db.getStatus(p), 0);
    }

    @Test
    void getPlayerMessage() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player ananas = db.getPlayer("L'ananas");
        Player posix  = db.getPlayer("p0slx");
        Player chef  = db.getPlayer("Le chef");

        ArrayList<Message> messages = db.getPlayerMessage(ananas, posix);
        assertEquals(messages.size(), 2);

        messages = db.getPlayerMessage(ananas, chef);
        assertEquals(messages.size(), 1);
    }
}