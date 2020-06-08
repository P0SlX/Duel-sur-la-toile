import  java.sql.*;
import java.util.ArrayList;

public  class  DatabaseConnection{

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
            return;
        }
    }

    public boolean isConnected(Player p) {
        return false;
    }

    public void connectPlayer(Player p) {

    }

    public void disconnectPlayer(Player p) {

    }

    public ArrayList<Game> getActivesGames(Player p) {
        return new ArrayList<Game>();
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