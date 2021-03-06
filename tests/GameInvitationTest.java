import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GameInvitationTest {

    @Test
    void getSender() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        assertEquals(g1.getSender(), p);
        assertNotEquals(g1.getSender(),p3);
    }

    @Test
    void getReceiver() throws IOException, SQLException  {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        assertEquals(g1.getReceiver(),p2);
        assertNotEquals(g1.getReceiver(),p3);
    }

    @Test
    void getEmissionDate() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Date d2 = new Date(2021,6,3);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        assertEquals(g1.getEmissionDate(), d1);
        assertNotEquals(g1.getEmissionDate(), d2);

    }

    @Test
    void getEtatInv() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        assertEquals(g1.getEtatInv(), 0);
        assertNotEquals(g1.getEtatInv(), 5);
    }

    @Test
    void getId() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        assertEquals(g1.getId(),1);
        assertNotEquals(g1.getId(),45);
    }

    @Test
    void accept() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        g1.accept();
        assertEquals(g1.getEtatInv(),1);
        assertNotEquals(g1.getEtatInv(),0);
    }

    @Test
    void decline() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        GameInvitation g1 = new GameInvitation(p,p2,d1,0,1);
        g1.decline();
        assertEquals(g1.getEtatInv(),-1);
        assertNotEquals(g1.getEtatInv(),1);
    }
}