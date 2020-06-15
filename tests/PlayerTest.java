import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getPseudo() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getPseudo(),"Coco");
        assertNotEquals(p.getPseudo(), "b");
    }

    @Test
    void setPseudo() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setPseudo("salut");
        assertEquals(p.getPseudo(), "salut");
        assertNotEquals(p.getPseudo(), "d");
        p.setPseudo("Coco");
    }

    @Test
    void getEmail() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getEmail(),"cocolastico@gmail.com");
        assertNotEquals(p.getPseudo(), "b");
    }

    @Test
    void setEmail() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setEmail("salut@gmail.com");
        assertEquals(p.getEmail(), "salut@gmail.com");
        assertNotEquals(p.getEmail(), "dd");
        p.setPseudo("cocolastico@gmail.com");
    }

    @Test
    void getMdp() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getMdp(),"cocopops");
        assertNotEquals(p.getPseudo(), "zz");
    }

    @Test
    void setMdp() throws IOException, SQLException {
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
    void getEtat() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        assertNotNull(p);
        assertEquals(p.getEtat(),0);
        assertNotEquals(p.getEtat(), 33);

    }

    @Test
    void setEtat() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setEtat(1);
        assertEquals(p.getEtat(), 1);
        assertNotEquals(p.getEtat(), 2);
        p.setEtat(0);
    }

    @Test
    void isDeactivated() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player e = db.getPlayer("Lanka");
        assertFalse( p.isDeactivated());
        assertTrue(e.isDeactivated());

    }

    @Test
    void setDeactivated() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        p.setDeactivated(true);
        assertTrue(p.isDeactivated());
        p.setDeactivated(false);

    }

    @Test
    void isAdmin() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player e = db.getPlayer("Le Chef");
        assertFalse(p.isAdmin());
        assertTrue(e.isAdmin());

    }

    @Test
    void setAdmin() throws IOException, SQLException {
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
    void getFriends() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("p0slx");
        assertNotNull(p.getFriends());
    }

    @Test
    void setFriends() {
    }

    @Test
    void testToString() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        String res = p.toString();

        assertEquals(res, "Player{pseudo='Coco', email='cocolastico@gmail.com', mdp='cocopops', avatar='', friends=[Player{pseudo='Le Chef', email='gendarmerie@gmail.com', mdp='password', avatar='', friends=null, etat=0, desactivated=false, admin=true}], etat=0, desactivated=false, admin=false}");
    }
}