import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void connectPlayer() throws SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        assertTrue(db.connectPlayer("Coco", "cocopops"));
        assertTrue(db.connectPlayer("L'ananas", "jaimeLesPommes"));
        assertFalse(db.connectPlayer("lananas@gmail.com", "j'aimeLesPommes"));

        assertFalse(db.connectPlayer("", ""));
    }

    @Test
    void getPlayer() throws IOException, SQLException {

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
    void getStatus() throws IOException, SQLException {
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
    void setStatus() throws IOException, SQLException {
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
    void getPlayerMessage() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player ananas = db.getPlayer("L'ananas");
        Player posix  = db.getPlayer("p0slx");
        Player chef  = db.getPlayer("Le chef");

        ArrayList<Message> messages = db.getPlayerMessage(ananas, posix);
        assertEquals(messages.size(), 12);

        messages = db.getPlayerMessage(ananas, chef);
        assertEquals(messages.size(), 1);
    }

    @Test
    void updatePlayer() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("Coco");
        p.setMdp("cocopops");
        db.updatePlayer(p);
        assertSame("cocopops", p.getMdp());
        p.setMdp("cocopops");
    }


    @Test
    void getMaxIdInv() throws SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Connection c = db.getConnexion();

        String reqS = "select MAX(idinv) from INVITATION";
        PreparedStatement ps =c.prepareStatement(reqS);
        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.println(rs.getString(1));
        System.out.println(db.getMaxIdInv());
        assertEquals(db.getMaxIdInv(), (rs.getInt(1)));
    }


    @Test
    void createInv() throws SQLException, IOException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p = db.getPlayer("Coco");
        Player p2 = db.getPlayer("L'ananas");
        db.createInv(p,p2, Invitation.INVFRIEND);

        Connection c = db.getConnexion();
        String reqS = "select idinv from INVITATION where idinv IN(select MAX(idinv) from INVITATION)";
        PreparedStatement ps = c.prepareStatement(reqS);
        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.println(rs.getString(1));
        System.out.println(db.getInv(db.getMaxIdInv()).getId());
        assertEquals(db.getInv(db.getMaxIdInv()).getId(), rs.getInt(1));
    }

    @Test
    void sendMessage() throws IOException, SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.connexion();

        Player p1 = db.getPlayer("Coco");
        Player p2 = db.getPlayer("p0slx");

        assertNotNull(p1);
        assertNotNull(p2);

        assertTrue(db.sendMessage(p1, p2, "Hello bg !"));
    }

    @Test
    void createJSONFromPlate() {
        final char[][] PLATE = {
                { '*', '*', '*', '*', '*', '*', '*' },
                { '*', '*', '*', '*', '*', '*', '*' },
                { '*', '*', '*', '*', '*', '*', '*' },
                { '*', '*', '*', '*', '*', '*', '*' },
                { '*', '*', '*', '*', '*', '*', '*' },
                { '*', '*', '*', '*', '*', '*', '*' },
                { '*', '*', '*', '*', '*', '*', '*' },
        };

        final String expected = "[\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "[ *, *, *, *, *, *, *, ],\n" +
                "]\n";

        try {
            JSONArray array = new JSONArray(DatabaseConnection.createJSONFromPlate(PLATE));
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(DatabaseConnection.createJSONFromPlate(PLATE), expected);
    }
}