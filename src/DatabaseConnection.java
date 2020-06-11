import javax.print.DocFlavor;
import  java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private Connection c;

    public DatabaseConnection() { }

    public void connexion() {
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

    public boolean connectPlayer(String pseudo, String password) {
        try {
            PreparedStatement ps = c.prepareStatement("select * from JOUEUR where pseudo=? and mdp=?");
            ps.setString(1, pseudo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getMaxIDGame() {
        try {
            PreparedStatement ps = c.prepareStatement("select gameID from PARTIE natural join JOUER order by gameID DESC");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("gameID");
            } else {
                return 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int getStatus(Player p) {
        int etat = 0;
        try {
            PreparedStatement ps = c.prepareStatement("select etat from JOUEUR where pseudo = ?");
            ps.setString(1, p.getPseudo());
            ResultSet rs = ps.executeQuery();
            rs.next();
            etat = rs.getInt("etat");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        return etat;
    }

    public void setStatus(Player p, int etat) {
        try{
            PreparedStatement ps= c.prepareStatement("update JOUEUR set etat = ? where pseudo = ?");
            ps.setInt(1, etat);
            ps.setString(2, p.getPseudo());
            ps.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public ArrayList<Game> getActivesGames(Player p) {
        ArrayList<Game> listActiveGames = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("select * from JOUER natural join PARTIE where pseudo=? or adversaire=? and state=0");
            ps.setString(1, p.getPseudo());
            ps.setString(2, p.getPseudo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Game g = new FourInARow(
                        this.getPlayer(rs.getString("pseudo")),
                        this.getPlayer(rs.getString("adversaire")),
                        this.getPlayer(rs.getString("currentPlayer")),
                        rs.getString("plate"),
                        rs.getDate("startTime"),
                        rs.getDate("finishTime"),
                        rs.getInt("elementPlaced"),
                        rs.getInt("gameID"),
                        rs.getInt("state"),
                        rs.getInt("score"),
                        rs.getString("nomJeu"),
                        this.getPlayer(rs.getString("winner")),
                        this.getPlayer(rs.getString("looser"))
                );
                listActiveGames.add(g);
            }
            return listActiveGames;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<Game> getPlayerHistory(Player p) { //TODO
        return new ArrayList<Game>();
    }

    public void playForInARowTurn(Player p, int line, int col) {    //TODO

    }

    public ArrayList<Player> getPlayerFriends(Player p) {   //TODO
        return new ArrayList<Player>();
    }


    public ArrayList<Player> getFriends(Player p) {
        try {
            ArrayList<Player> friendList = new ArrayList<>();
            PreparedStatement ps = c.prepareStatement("select * from  JOUEUR where pseudo IN (select amis from ETREAMIS natural  join  JOUEUR where pseudo = ?);");
            ps.setString(1, p.getPseudo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player pq = new Player(
                        rs.getString("pseudo"),
                        rs.getString("email"),
                        rs.getString("mdp"),
                        rs.getBytes("avatar"),
                        rs.getInt("etat"),
                        rs.getBoolean("desactive"),
                        rs.getBoolean("admin")
                        );
                friendList.add(pq);
            }
            return friendList;
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }


    /********** PLAYER **********/
    public Player getPlayer(String pseudo) {
        try {
            PreparedStatement ps = c.prepareStatement("select * from JOUEUR where pseudo=?");
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Player p = new Player(
                        rs.getString("pseudo"),
                        rs.getString("email"),
                        rs.getString("mdp"),
                        rs.getBytes("avatar"),
                        rs.getInt("etat"),
                        rs.getBoolean("desactive"),
                        rs.getBoolean("admin")
                        );
                p.setFriends(this.getFriends(p));
                return p;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updatePlayer(Player p) {
        try {
            PreparedStatement ps = c.prepareStatement("update JOUEUR set mdp=?, avatar=?, etat=?, desactive=?, admin=? where pseudo=?");
            ps.setString(1, p.getMdp());
            ps.setBytes(2, p.getAvatar());
            ps.setInt(3, p.getEtat());
            ps.setBoolean(4, p.isDesactivated());
            ps.setBoolean(5, p.isAdmin());
            ps.setString(6, p.getPseudo());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPlayer(Player p) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into JOUEUR values (?,?,?,?,?,?,?)");
            ps.setString(1, p.getPseudo());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getMdp());
            ps.setBytes(4, p.getAvatar());
            ps.setInt(5, p.getEtat());
            ps.setBoolean(6, p.isDesactivated());
            ps.setBoolean(7, p.isAdmin());
            ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /***************************/


    public ArrayList<Game> getGameList() {
        ArrayList<Game> gameList = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("select * from PARTIE natural join JOUER");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs.getString("nomJeu").equals("Puissance 4")){
                    Game g = new FourInARow(
                            this.getPlayer(rs.getString("pseudo")),
                            this.getPlayer(rs.getString("adversaire")),
                            this.getPlayer(rs.getString("currentPlayer")),
                            rs.getString("plate"),
                            rs.getDate("startTime"),
                            rs.getDate("finishTime"),
                            rs.getInt("elementPlaced"),
                            rs.getInt("gameID"),
                            rs.getInt("state"),
                            rs.getInt("score"),
                            rs.getString("nomJeu"),
                            this.getPlayer(rs.getString("winner")),
                            this.getPlayer(rs.getString("looser"))
                            );
                    gameList.add(g);
                }
            }
            return gameList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String getFourInRowPlate(Game g) {       //TODO
        return "";
    }

    public ArrayList<Message> getPlayerMessage(Player sender, Player receiver) {
        ArrayList<Message> messageArrayList = new ArrayList<>();

        try {
            PreparedStatement ps = c.prepareStatement(
                    "select contenumessage, datemessage " +
                            "from MESSAGE natural join COMMUNIQUER " +
                            "where pseudo=? and destinataire=?" +
                            "order by datemessage"
            );

            ps.setString(1, sender.getPseudo());
            ps.setString(2, receiver.getPseudo());

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                messageArrayList.add(
                        new Message(resultSet.getString("contenumessage"),
                                resultSet.getDate("datemessage").toString())
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messageArrayList;
    }

    public void addNewGame(Player p1, Player p2, Game g) {
        // Ajout dans la table PARTIE
        try {
            PreparedStatement ps = c.prepareStatement("insert into PARTIE values (?,?,?,?, 0, CURDATE(), CURDATE(), 0, null, null)");
            ps.setInt(1, this.getMaxIDGame() + 1);
            ps.setString(2, g.getNomJeu());
            ps.setString(3, "contenuGrille");       // TODO
            ps.setString(4, p1.getPseudo());
            ps.executeUpdate();
            PreparedStatement ps2 = c.prepareStatement("insert into JOUER values (?,?,?,0)");
            ps2.setString(1, p1.getPseudo());
            ps2.setString(2, p2.getPseudo());
            ps2.setInt(3, this.getMaxIDGame() + 1);
            ps2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /********** INVITATIONS **********/
    public int getMaxIdInv() {
        try {
            PreparedStatement ps = c.prepareStatement("select idinv from INVITATION natural join INVITER order by idinv DESC");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idinv");
            } else {
                return 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void createInv(Player expediteur, Player destinataire) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into INVITATION values (?, CURDATE(), ?)");
            int gameId = this.getMaxIdInv() + 1;
            ps.setInt(1, gameId);
            ps.setInt(2, GameInvitation.PENDING);
            ps.executeUpdate();
            PreparedStatement ps2 = c.prepareStatement("insert into INVITER values (?, ?, ?)");
            ps2.setString(1, expediteur.getPseudo());
            ps2.setString(2, destinataire.getPseudo());
            ps2.setInt(3, gameId);
            ps2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changeStateInv(Invitation inv) {
        try {
            PreparedStatement ps = c.prepareStatement("update INVITATION set etatinv = ? where idinv = ?");
            ps.setInt(1, inv.getEtatInv());
            ps.setInt(2, inv.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}