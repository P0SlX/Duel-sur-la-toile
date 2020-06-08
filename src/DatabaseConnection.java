import javax.xml.crypto.Data;
import  java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection{
    public final int DISCONNECTED = 0;
    public final int CONNECTED    = 1;
    public final int AFK          = 2;
    public final int DND          = 3;

    private Connection c;

    public DatabaseConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MariaDB non trouvé");
            c = null;
            return;
        }
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/DUEL_SUR_LA_TOILE","user","user");
            System.out.println("Connexion BD réussie !");
        } catch (SQLException ex) {
            System.out.println("Echec de connexion à MariaDB");
            System.out.println("Msg:" + ex.getMessage() + ex.getErrorCode());
            c = null;
        }
    }

    public Connection getConnexion() {
        return this.c;
    }

    public int getMaxIDGame() {
        try {
            PreparedStatement ps = c.prepareStatement("select idPartie from JOUER order by idPartie DESC");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("idPartie");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    public int getStatus(Player p) {  // Anciennement isConnected
        int etat = 0;
        try {
            PreparedStatement ps = c.prepareStatement("select etat from JOUEUR where pseudo = ?");
            ps.setString(1, p.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            etat = rs.getInt("etat");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return etat;
    }


    /* Remplace connecteJoueur et décoJ
        DISCONNECTED = 0
        CONNECTED    = 1
        AFK          = 2
        DND          = 3
    */
    public void setStatus(Player p, int etat) {
        try{
            PreparedStatement ps= c.prepareStatement("update JOUEUR set etat = ? where pseudo = ?");
            ps.setInt(1, etat);
            ps.setString(2, p.getName());
            ps.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public ArrayList<Game> getActivesGames(Player p) {
        return new ArrayList<>();
    }

    public ArrayList<Game> getPlayerHistory(Player p) {
        return new ArrayList<Game>();
    }

    public void playForInARowTurn(Player p, int line, int col) {

    }

    public ArrayList<Player> getPlayerFriends(Player p) {
        return new ArrayList<Player>();
    }

    public ArrayList<Game> getGameList() {
        return new ArrayList<Game>();
    }

    public String getFourInRowPlate(Game g) {
        return "";
    }

    public ArrayList<Message> getPlayerMessage(Player sender, Player receiver) {
        return new ArrayList<Message>();
    }

    public void addNewGame(Player p1, Player p2, Game g) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into PARTIE values (?,?,?,?, 0, CURDATE(), CURDATE(), 0)");
            ps.setInt(1, this.getMaxIDGame() + 1);
            ps.setString(2, g.getNomJeu());
            ps.setString(3, "contenuGrille");       // TODO
            ps.setString(4, p1.getName());
            ps.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public void acceptInvit(Invitation inv) {

    }

    public void declineInvit(Invitation inv) {

    }
}