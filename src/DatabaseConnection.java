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

    }

    public void acceptInvit(Invitation inv) {

    }

    public void declineInvit(Invitation inv) {

    }
}