import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getPseudo(),"Coco");
        assertNotEquals(p.getPseudo(), "b");
    }

    @Test
    void setPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setPseudo("salut");
        assertEquals(p.getPseudo(), "salut");
        assertNotEquals(p.getPseudo(), "d");
        p.setPseudo("Coco");
    }

    @Test
    void getEmail() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getEmail(),"cocolastico@gmail.com");
        assertNotEquals(p.getPseudo(), "b");
    }

    @Test
    void setEmail() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setEmail("salut@gmail.com");
        assertEquals(p.getEmail(), "salut@gmail.com");
        assertNotEquals(p.getEmail(), "dd");
        p.setPseudo("cocolastico@gmail.com");
    }

    @Test
    void getMdp() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getMdp(),"cocopops");
        assertNotEquals(p.getPseudo(), "zz");
    }

    @Test
    void setMdp() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setMdp("mmddpp");
        assertEquals(p.getMdp(), "mmddpp");
        assertNotEquals(p.getMdp(), "azertyuiop");
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
        assertNotNull(p);
        assertEquals(p.getEtat(),0);
        assertNotEquals(p.getEtat(), 33);

    }

    @Test
    void setEtat() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setEtat(1);
        assertEquals(p.getEtat(), 1);
        assertNotEquals(p.getEtat(), 2);
        p.setEtat(0);
    }

    @Test
    void isDesactivated() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player e = db.getPlayer("Lanka");
        assertFalse( p.isDesactivated());
        assertTrue(e.isDesactivated());

    }

    @Test
    void setDectivated() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setDectivated(true);
        assertTrue(p.isDesactivated());
        p.setDectivated(false);

    }

    @Test
    void isAdmin() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player e = db.getPlayer("Le Chef");
        assertFalse(p.isAdmin());
        assertTrue(e.isAdmin());

    }

    @Test
    void setAdmin() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player e = db.getPlayer("Le Chef");
        p.setAdmin(true);
        e.setAdmin(false);
        assertFalse(e.isAdmin());
        assertTrue(p.isAdmin());
        e.setAdmin(true);
        p.setAdmin(false);

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