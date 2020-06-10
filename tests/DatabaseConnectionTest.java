import org.junit.jupiter.api.Test;

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
}