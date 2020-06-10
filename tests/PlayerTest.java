import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertTrue(p != null);
        assertEquals(p.getPseudo(),"Coco");
        assertFalse(p.getPseudo().equals("b"));
    }

    @Test
    void setPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setPseudo("salut");
        assertEquals(p.getPseudo(), "salut");
        assertFalse(p.getPseudo().equals("d"));
        p.setPseudo("Coco");
    }

    @Test
    void getEmail() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertTrue(p != null);
        assertEquals(p.getEmail(),"cocolastico@gmail.com");
        assertFalse(p.getPseudo().equals("b"));
    }

    @Test
    void setEmail() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setEmail("salut@gmail.com");
        assertEquals(p.getEmail(), "salut@gmail.com");
        assertFalse(p.getEmail().equals("dd"));
        p.setPseudo("cocolastico@gmail.com");
    }

    @Test
    void getMdp() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertTrue(p != null);
        assertEquals(p.getMdp(),"cocopops");
        assertFalse(p.getPseudo().equals("zz"));
    }

    @Test
    void setMdp() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setMdp("mmddpp");
        assertEquals(p.getMdp(), "mmddpp");
        assertFalse(p.getMdp().equals("azertyuiop"));
        p.setPseudo("cocopops");
    }

    @Test
    void getAvatar() {
//        DatabaseConnection db = new DatabaseConnection();
//        db.connexion();
//        Player p = db.getPlayer("Coco");
//        assertTrue(p != null);
//        assertEquals(p.getAvatar(),"mouton.png");
//        assertFalse(p.getAvatar().equals("ez"));
    }

    @Test
    void setAvatar() {
    }

    @Test
    void getEtat() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertTrue(p != null);
        assertEquals(p.getEtat(),0);
        assertFalse(p.getEtat() == 33);

    }

    @Test
    void setEtat() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setEtat(1);
        assertEquals(p.getEtat(), 1);
        assertFalse(p.getEtat() == 2);
        p.setEtat(0);
    }

    @Test
    void isDesactivated() {
    }

    @Test
    void setDectivated() {
    }

    @Test
    void isAdmin() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");

    }

    @Test
    void setAdmin() {
    }

    @Test
    void getFriends() {

    }

    @Test
    void setFriends() {
    }

    @Test
    void testToString() {
    }
}