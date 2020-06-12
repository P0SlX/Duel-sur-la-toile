import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void getContent() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Message msg = new Message("rend mon quad", "2020-25-12",p.getPseudo(),p2.getPseudo());
        assertEquals(msg.getContent(),"rend mon quad");
        assertNotEquals(msg.getContent(),"rend mon scooter");

    }

    @Test
    void getDate() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Message msg = new Message("rend mon quad", "2020-25-12",p.getPseudo(),p2.getPseudo());
        assertEquals(msg.getDate(),"2020-25-12");
        assertNotEquals(msg.getDate(),"2020-25-13");
    }

    @Test
    void getSenderPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        Message msg = new Message("rend mon quad", "2020-25-12",p.getPseudo(),p2.getPseudo());
        assertEquals(msg.getSenderPseudo(),p.getPseudo());
        assertNotEquals(msg.getSenderPseudo(),p3);
    }

    @Test
    void setSenderPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        Message msg = new Message("rend mon quad", "2020-25-12",p.getPseudo(),p2.getPseudo());
        msg.setSenderPseudo(p3.getPseudo());
        assertEquals(msg.getSenderPseudo(),p3.getPseudo());
        assertNotEquals(msg.getSenderPseudo(),p2.getPseudo());
    }

    @Test
    void getReceiverPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        Message msg = new Message("rend mon quad", "2020-25-12",p.getPseudo(),p2.getPseudo());
        assertEquals(msg.getReceiverPseudo(),p2.getPseudo());
        assertNotEquals(msg.getReceiverPseudo(),p3);
    }

    @Test
    void setReceiverPseudo() {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();
        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("Jordan");
        Player p3 = db.getPlayer("L'ananas");
        Message msg = new Message("rend mon quad", "2020-25-12",p.getPseudo(),p2.getPseudo());
        msg.setReceiverPseudo(p3.getPseudo());
        assertEquals(msg.getReceiverPseudo(),p3.getPseudo());
        assertNotEquals(msg.getReceiverPseudo(),p2.getPseudo());
    }
}