import org.junit.jupiter.api.Test;

import java.sql.Date;


import static org.junit.jupiter.api.Assertions.*;

class FriendInvitationTest {


    @Test
    void getSender() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        assertEquals(f1.getSender(), p);
        assertNotEquals(f1.getSender(),p3);
    }

    @Test
    void getReceiver() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        assertEquals(f1.getReceiver(),p2);
        assertNotEquals(f1.getReceiver(),p3);
    }

    @Test
    void getEmissionDate() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Date d2 = new Date(2021,6,3);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        assertEquals(f1.getEmissionDate(), d1);
        assertNotEquals(f1.getEmissionDate(), d2);

    }

    @Test
    void getEtatInv() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        assertEquals(f1.getEtatInv(), 0);
        assertNotEquals(f1.getEtatInv(), 5);
    }

    @Test
    void getId() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        assertEquals(f1.getId(),1);
        assertNotEquals(f1.getId(),45);
    }

    @Test
    void accept() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        f1.accept();
        assertEquals(f1.getEtatInv(),1);
    }

    @Test
    void decline() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Date d1 = new Date(2020,5,6);
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        FriendInvitation f1 = new FriendInvitation(p,p2,d1,0,1);
        f1.decline();
        assertEquals(f1.getEtatInv(),-1);
    }
}