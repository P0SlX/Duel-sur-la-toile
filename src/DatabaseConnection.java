import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * All getters in this class took information in the database
 * All setters in this class modify the database
 */
public class DatabaseConnection {

    private Connection c;

    /**
     * Return a JSON string that represents the game plate
     * @param plate game plate
     * @return A String that represents the game plate formatted in JSON
     */
    public static String createJSONFromPlate(char[][] plate) {
        StringBuilder generatedJSON = new StringBuilder("[\n");

        for(char[] line : plate) {
            generatedJSON.append("[ ");
            for(char c : line)
                generatedJSON.append(String.format("%c, ", c));

            generatedJSON.append("],\n");
        }

        generatedJSON.append("]\n");
        System.out.println(generatedJSON.toString());
        return generatedJSON.toString();
    }

    public DatabaseConnection() {}

    /**
     * Allow to connect functions to the database.
     */
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

    /**
     * @return Connection, return the connexion to the database
     */
    public Connection getConnexion() {
        return this.c;
    }

    /**
     * @param p Player, the player whose we want the status
     * @return int, the status of a player which is store in the database (refer to the player class for more information)
     * @throws SQLException If the mariadb driver is not found.
     */
    @Deprecated
    public int getStatus(Player p) throws SQLException {
        int etat = 0;
        PreparedStatement ps = c.prepareStatement("select etat from JOUEUR where pseudo = ?");
        ps.setString(1, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        etat = rs.getInt("etat");
        return etat;
    }

    /**
     * @param p Player, the player concerning by the change of status
     * @param etat int, the new status of the player
     * @throws SQLException
     */
    @Deprecated
    public void setStatus(Player p, int etat) throws SQLException {
        PreparedStatement ps= c.prepareStatement("update JOUEUR set etat = ? where pseudo = ?");
        ps.setInt(1, etat);
        ps.setString(2, p.getPseudo());
        ps.executeUpdate();

    }


    /********** PLAYER **********/

    /**
     * Function which refer to the sign in page
     * @param pseudo The pseudo of the player
     * @param password The password of the player
     * @return boolean,
     *         - true = the player is connected
     *         - false = the player isn't connected
     * @throws SQLException
     */
    public boolean connectPlayer(String pseudo, String password) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select * from JOUEUR where pseudo=? and mdp=?");
        ps.setString(1, pseudo);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return true;
        return false;
    }

    /**
     * @return ArrayList of all the players registered in the database
     * @throws SQLException
     * @throws IOException problem with writing or reading file
     */
    public ArrayList<Player> getAllPlayers() throws SQLException, IOException {
        ArrayList<Player> listPlayers = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("select * from  JOUEUR");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Player pq = new Player(
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    rs.getString("mdp"),
                    loadImageFromStream(rs.getBinaryStream("avatar"), rs.getString("pseudo")),
                    rs.getInt("etat"),
                    rs.getBoolean("desactive"),
                    rs.getBoolean("admin")
            );
            listPlayers.add(pq);
        }
        return listPlayers;
    }

    /**
     * @param pseudo String, the pseudo of the player whose we want his information
     * @return Player, the player we want
     * @throws SQLException
     * @throws IOException
     */
    public Player getPlayer(String pseudo) throws SQLException, IOException {
        PreparedStatement ps = c.prepareStatement("select * from JOUEUR where pseudo=?");
        ps.setString(1, pseudo);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            InputStream stream = rs.getBinaryStream("avatar");

            Player p = new Player(
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    rs.getString("mdp"),
                    loadImageFromStream(stream, pseudo),
                    rs.getInt("etat"),
                    rs.getBoolean("desactive"),
                    rs.getBoolean("admin")
            );
            p.setFriends(this.getFriends(p));
            return p;
        }
        return null;
    }

    /**
     * update the information of the player in the database
     * @param p Player, the player we want to update in database
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void updatePlayer(Player p) throws SQLException, FileNotFoundException {
        if (p.getPlayerAvatar().getWidth()==0 || p.getAvatar().equals("")) {
            p.setAvatar("img/avatarDefault.png");
        }
        File playerAvatarFile = new File(p.getAvatar());
        FileInputStream fileInputStream = new FileInputStream(playerAvatarFile);

        PreparedStatement ps = c.prepareStatement("update JOUEUR set mdp=?, avatar=?, etat=?, desactive=?, admin=? where pseudo=?");
        ps.setString(1, p.getMdp());
        ps.setBinaryStream(2, fileInputStream, (int)playerAvatarFile.length());
        ps.setInt(3, p.getEtat());
        ps.setBoolean(4, p.isDesactivated());
        ps.setBoolean(5, p.isAdmin());
        ps.setString(6, p.getPseudo());
        ps.executeUpdate();
    }

    /**
     * create a new player in the database
     * @param p Player, the player whose we want to put information on the database
     * @throws FileNotFoundException Player not found
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void createPlayer(Player p) throws FileNotFoundException, SQLException {
        File playerAvatarFile = new File(p.getAvatar());
        FileInputStream fileInputStream = new FileInputStream(playerAvatarFile);

        PreparedStatement ps = c.prepareStatement("insert into JOUEUR values (?,?,?,?,?,?,?)");
        ps.setString(1, p.getPseudo());
        ps.setString(2, p.getEmail());
        ps.setString(3, p.getMdp());
        ps.setBinaryStream(4, fileInputStream, (int)playerAvatarFile.length());
        ps.setInt(5, p.getEtat());
        ps.setBoolean(6, p.isDesactivated());
        ps.setBoolean(7, p.isAdmin());
        ps.executeQuery();
    }

    /**
     * @param sender Player who send messages
     * @param receiver Player who receiveve the messages
     * @return ArrayList<Message> between those two players
     * @throws SQLException
     */
    public ArrayList<Message> getPlayerMessage(Player sender, Player receiver) throws SQLException {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement(
                "select * " +
                        "from MESSAGE natural join COMMUNIQUER " +
                        "where (pseudo=? and destinataire=?) or (pseudo=? and destinataire=?) " +
                        "order by datemessage"
        );

        ps.setString(1, sender.getPseudo());
        ps.setString(2, receiver.getPseudo());
        ps.setString(3, receiver.getPseudo());
        ps.setString(4, sender.getPseudo());

        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()) {
            messageArrayList.add(
                    new Message(resultSet.getString("contenumessage"),
                            resultSet.getDate("datemessage").toString(),
                            resultSet.getString("pseudo"),
                            resultSet.getString("destinataire"),
                            resultSet.getInt("idmessage"),
                            resultSet.getBoolean("messagelu")
                    )
            );
        }
        return messageArrayList;
    }

    public void markPlayerMessagesAsRead(Player sender, Player receiver) {

    }

    /**
     * @param sender Player who send the message
     * @param receiver Player who received the message
     * @param content String, the content of the message
     * @return boolean
     *         - true = the message is sent
     *         - false = error, the message wasn't sent
     * @throws SQLException
     */
    public boolean sendMessage(Player sender, Player receiver, String content) throws SQLException {
        if(!content.isBlank()) {
            int maxId = getIdMaxMessage() + 1;
            boolean result = true;
            PreparedStatement psMessage = c.prepareStatement("insert into MESSAGE values(?, CURDATE(), ?, true)");
            psMessage.setInt(1, maxId);
            psMessage.setString(2, content);
            psMessage.execute();

            psMessage = c.prepareStatement("insert into COMMUNIQUER values(?, ?, ?)");
            psMessage.setString(1, sender.getPseudo());
            psMessage.setString(2, receiver.getPseudo());
            psMessage.setInt(3, maxId);
            psMessage.execute();
            return result;
        }
        return false;
    }

    /**
     * @param p Player whose we want to have the friends
     * @return ArrayList<Player> The list of friends of our player
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<Player> getFriends(Player p) throws SQLException, IOException {
        ArrayList<Player> friendList = new ArrayList<>();

        PreparedStatement ps = c.prepareStatement("select * from  JOUEUR where pseudo IN (select amis from ETREAMIS natural  join  JOUEUR where pseudo = ?)");
        ps.setString(1, p.getPseudo());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Player pq = new Player(
                    rs.getString("pseudo"),
                    rs.getString("email"),
                    rs.getString("mdp"),
                    loadImageFromStream(rs.getBinaryStream("avatar"), p.getPseudo()),
                    rs.getInt("etat"),
                    rs.getBoolean("desactive"),
                    rs.getBoolean("admin")
            );
            friendList.add(pq);
        }
        return friendList;
    }

    /**
     * @param p Player whose we want the history
     * @return ArrayList<Game> The player's game history
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<Game> getPlayerHistory(Player p) throws SQLException, IOException {         //TODO
        ArrayList<Game> listGameHistory = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("select * from JOUER natural join PARTIE where pseudo=? or adversaire=? and state=1 or state=-1");
        fetchGames(p, listGameHistory, ps, GameType.FourInARow);
        return listGameHistory;
    }

    /**
     * recover all the party whatever the game
     * @param p Player whose we want the games
     * @param listGameHistory ArrayList<Game> all the game we want to recover
     * @param ps PreparedStatement
     * @param type GameType
     * @throws SQLException
     * @throws IOException
     */
    private void fetchGames(Player p, ArrayList<Game> listGameHistory, PreparedStatement ps, GameType type) throws SQLException, IOException {
        ps.setString(1, p.getPseudo());
        ps.setString(2, p.getPseudo());
        ps.setString(3, type.toString());
        ResultSet rs = ps.executeQuery();
        processFetchedGames(listGameHistory, rs);
    }

    /**
     * Load games in the database
     * @param listGameHistory ArrayList<Game> list of game we load in the database
     * @param rs ResultSet which contains the game we recover
     * @throws SQLException
     * @throws IOException
     */
    private void processFetchedGames(ArrayList<Game> listGameHistory, ResultSet rs) throws SQLException, IOException {
        while (rs.next()) {
            Game g = new FourInARow(
                    this.getPlayer(rs.getString("pseudo")),
                    this.getPlayer(rs.getString("adversaire")),
                    this.getPlayer(rs.getString("currentPlayer")),
                    rs.getString("plate"),
                    rs.getDate("startTime").toString(),
                    rs.getDate("finishTime").toString(),
                    rs.getInt("elementPlaced"),
                    rs.getInt("gameID"),
                    rs.getInt("state"),
                    rs.getInt("score"),
                    rs.getString("nomJeu"),
                    this.getPlayer(rs.getString("winner")),
                    this.getPlayer(rs.getString("looser"))
            );
            listGameHistory.add(g);
        }
    }
    /***************************/


    /********** GAMES **********/

    /**
     *
     * @return ArrayList<Game> All the game in the database
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<Game> getGameList() throws SQLException, IOException {
        ArrayList<Game> gameList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("select * from PARTIE natural join JOUER");
        ResultSet rs = ps.executeQuery();
        processFetchedGames(gameList, rs);
        return gameList;
    }

    public ArrayList<Game> getGameListPlayer(Player p) throws SQLException, IOException {
        ArrayList<Game> gameListPlayer = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("select * from PARTIE natural join JOUER where (pseudo=? or adversaire=?) and (state=-1 or state=-2) order by gameID desc");
        ps.setString(1, p.getPseudo());
        ps.setString(2, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        this.processFetchedGames(gameListPlayer,rs);
        return gameListPlayer;
    }

    /**
     * @return int, the bigger id in the database (which is normally the last game created)
     * @throws SQLException
     */
    public int getMaxIDGame() throws SQLException {
        PreparedStatement ps = c.prepareStatement("select gameID from PARTIE natural join JOUER order by gameID DESC");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("gameID");
        } else {
            return 0;
        }
    }

    /**
     * @param p Player concerned by the request
     * @param type GameType, type of the game (refer to game class for more information)
     * @return ArrayList<Game>, List of game in_progress where the Player p is playing
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<Game> getActivesGames(Player p, GameType type) throws SQLException, IOException {
        ArrayList<Game> listActiveGames = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("select * from JOUER natural join PARTIE " +
                "where (pseudo=? or adversaire=?) and nomJeu=? and state=0");
        fetchGames(p, listActiveGames, ps, type);
        return listActiveGames;
    }

    /**
     * create a new Game of the game g betwen two players, p1 and p2
     * @param p1 Player, first player in the game
     * @param p2 Player, second player in the game
     * @throws SQLException
     */
    public void addNewGame(Player p1, Player p2) throws SQLException {
        char[][] plate = new char[7][7];
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++)
                plate[i][j] = '*';
        }
        FourInARow g = new FourInARow(p1, p2, p1, createJSONFromPlate(plate), "FourInARow");

        PreparedStatement ps = c.prepareStatement("insert into PARTIE values (?,?,?,?, 0, CURDATE(), CURDATE(), 0, null, null)");
        ps.setInt(1, this.getMaxIDGame() + 1);
        ps.setString(2, g.getGameName());
        ps.setString(3, createJSONFromPlate(g.getPlate()));
        ps.setString(4, p1.getPseudo());
        ps.executeUpdate();
        PreparedStatement ps2 = c.prepareStatement("insert into JOUER values (?,?,?,0)");
        ps2.setString(1, p1.getPseudo());
        ps2.setString(2, p2.getPseudo());
        ps2.setInt(3, this.getMaxIDGame() + 1);
        ps2.executeUpdate();
    }

    /**
     * @param fourInARow Game, the game whose we want the plate
     * @return String, the plate of the game
     * @throws SQLException
     */
    public void updateFourInARowPlate(FourInARow fourInARow) throws SQLException {
        PreparedStatement ps = c.prepareStatement("update PARTIE set plate=? where gameID=?");

        ps.setString(1, createJSONFromPlate(fourInARow.getPlate()));
        ps.setInt(2, fourInARow.getGameID());
        ps.executeQuery();

        ps = c.prepareStatement("update PARTIE set elementPlaced=? where gameID=?");
        ps.setInt(1, getNumberOfElementPlaced(fourInARow) + 1);
        ps.setInt(2, fourInARow.getGameID());
        ps.executeQuery();
    }

    public String getFourInARowPlate(FourInARow game) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select plate from PARTIE where gameID=?");
        ps.setInt(1, game.getGameID());

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        return resultSet.getString("plate");
    }

    public void updateGameStatus(Game game, int status) throws SQLException {
        PreparedStatement ps = c.prepareStatement("update PARTIE set state=? where gameID=?");
        ps.setInt(1, status);
        ps.setInt(2, game.getGameID());
        ps.executeQuery();
    }

    /**
     * Update game current game player in database
     * @param game the concerned game
     * @throws SQLException An SQL exception
     */
    public void updateCurrentGamePlayer(Game game) throws SQLException {
        PreparedStatement ps = c.prepareStatement("update PARTIE set currentPlayer=? where gameID=?");
        ps.setString(1, game.getCurrentPlayer().getPseudo());
        ps.setInt(2, game.getGameID());
        ps.executeQuery();
    }

    public void addNewFriend(Player player, Player newFriend) throws SQLException {
        PreparedStatement ps = c.prepareStatement("insert into ETREAMIS values(?, ?)");
        ps.setString(1, player.getPseudo());
        ps.setString(2, newFriend.getPseudo());
        ps.executeQuery();

        ps = c.prepareStatement("insert into ETREAMIS values(?, ?)");
        ps.setString(1, newFriend.getPseudo());
        ps.setString(2, player.getPseudo());
        ps.executeQuery();
    }

    /********** INVITATIONS **********/

    /**
     * @return int, the bigger id of an invitation (game or friend) (normally the last invitation created)
     * @throws SQLException
     */
    public int getMaxIdInv() throws SQLException {
        PreparedStatement ps = c.prepareStatement("select idinv from INVITATION natural join INVITER order by idinv DESC");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("idinv");
        } else {
            return 0;
        }
    }

    /**
     * @param id int, ID of the invitation we recover
     * @return the Invitation corresponding to the ID
     * @throws SQLException
     * @throws IOException
     */
    public Invitation getInv(int id) throws SQLException, IOException {
        PreparedStatement ps = c.prepareStatement("select * from INVITATION natural join INVITER where idinv=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            if (rs.getBoolean("type")) { // SI C'EST UN JEU
                return new GameInvitation(
                        this.getPlayer(rs.getString("expediteurInvit")),
                        this.getPlayer(rs.getString("destinataireInvit")),
                        rs.getDate("dateinv"),
                        rs.getInt("etatinv"),
                        rs.getInt("idinv")
                );
            } else {
                return new FriendInvitation(
                        this.getPlayer(rs.getString("expediteurInvit")),
                        this.getPlayer(rs.getString("destinataireInvit")),
                        rs.getDate("dateinv"),
                        rs.getInt("etatinv"),
                        rs.getInt("idinv")
                );
            }
        }
        return null;
    }

    /**
     * @param sender Player, the sender of the invitation
     * @param receiver Player, the receiver of the invitation
     * @param type boolean, the type of the invitation
     *             - true = Invitation to play
     *             - false = invitation to be friends
     * @throws SQLException
     */
    public void createInv(Player sender, Player receiver, boolean type) throws SQLException {
        PreparedStatement ps = c.prepareStatement("insert into INVITATION values (?, CURDATE(), ?, ?)");
        int gameId = this.getMaxIdInv() + 1;
        ps.setInt(1, gameId);
        ps.setInt(2, GameInvitation.PENDING);
        ps.setBoolean(3, type);
        ps.executeUpdate();
        PreparedStatement ps2 = c.prepareStatement("insert into INVITER values (?, ?, ?)");
        ps2.setString(1, sender.getPseudo());
        ps2.setString(2, receiver.getPseudo());
        ps2.setInt(3, gameId);
        ps2.executeUpdate();
    }

    public ArrayList<? extends Invitation> getPlayerInvitations(Player player) throws SQLException, IOException {
        ArrayList<Invitation> invitations = new ArrayList<>();

        PreparedStatement ps = c.prepareStatement("select *" +
                "from INVITATION natural join INVITER where destinataireInvit=? and etatinv=0");

        ps.setString(1, player.getPseudo());
        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()) {
            if(resultSet.getBoolean("type")) {
                invitations.add(
                        new GameInvitation(
                                getPlayer(resultSet.getString("expediteurInvit")),
                                player,
                                resultSet.getDate("dateInv"),
                                resultSet.getInt("etatinv"),
                                resultSet.getInt("idinv")
                        )
                );
            } else {
                invitations.add(
                        new FriendInvitation(
                                getPlayer(resultSet.getString("expediteurInvit")),
                                player,
                                resultSet.getDate("dateInv"),
                                resultSet.getInt("etatinv"),
                                resultSet.getInt("idinv")
                        )
                );
            }
        }

        return invitations;
    }

    /**
     * @param inv Invitation, the invitation we change the state
     *            -1 = invitation refused
     *            0 = invitation pending
     *            1 =invitation accepted
     * @throws SQLException if something wrong happens with a SQL query
     */
    public void changeStateInv(Invitation inv) throws SQLException {
        PreparedStatement ps = c.prepareStatement("update INVITATION set etatinv = ? where idinv = ?");
        ps.setInt(1, inv.getEtatInv());
        ps.setInt(2, inv.getId());
        ps.executeUpdate();

    }
    /***************************/

    /**
     * Method to load an image from an InputStream (use for avatars)
     * @param inputStream, the InputStream of the image
     * @param pseudo String, the player's pseudo concerning by the avatar
     * @return Image, the player's avatar
     * @throws SQLException if something wrong happens with a SQL query
     * @throws IOException if something wrong happens with a Input/Output
     */
    private Image loadImageFromStream(InputStream inputStream, String pseudo) throws SQLException, IOException {
        String fileName = String.format("user_%s.png", pseudo);

        if(inputStream == null) return new Image(new FileInputStream("UI/assets/logo.png"));

        File avatar = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(avatar);

        byte[] buff = new byte[1024]; // Read 1kio block
        int fileLength = 0;

        while((fileLength = inputStream.read(buff)) != -1)
            fileOutputStream.write(buff, 0, fileLength);

        // Flush it to the file
        fileOutputStream.flush();
        fileOutputStream.close();
        return new Image(new FileInputStream("./" + fileName));
    }

    private int getNumberOfElementPlaced(Game g) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select elementPlaced from PARTIE where gameID=?");
        ps.setInt(1, g.getGameID());
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        return resultSet.getInt("elementPlaced");
    }

    /**
     * @return int, the bigger id of all the messages (normally the last message sent)
     * @throws SQLException
     */
    private int getIdMaxMessage() throws SQLException {
        PreparedStatement ps = c.prepareStatement("select max(idMessage) from MESSAGE");
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

    /********** Player Statistics **********/

    /**
     * @return int, the number of game won by a player
     * @throws SQLException
     */
    private int getVictories(Player p) throws SQLException {
        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM PARTIE where winner = ?");
        ps.setString(1, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }

    private int getDefeat(Player p) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select COUNT(*) from PARTIE where looser=?");
        ps.setString(1, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }

    private int getActiveGames(Player p) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select COUNT(*) from JOUER natural join PARTIE where (pseudo=? or adversaire=?) and state=0");
        ps.setString(1, p.getPseudo());
        ps.setString(2, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }

    private int getConsecutiveWins(Player p) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select * from JOUER natural join PARTIE where state=-1 and (pseudo=? or adversaire=?) order by gameID");
        ps.setString(1, p.getPseudo());
        ps.setString(2, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        int cpt = 0;
        int maxValue = 0;
        while (rs.next()) {
            if (rs.getString("winner").equals(p.getPseudo())) {
                cpt++;
            } else {
                maxValue = Math.max(cpt, maxValue);
                cpt = 0;
            }
        }
        return maxValue;
    }

    /**
     * @return int, the number of game played
     * @throws SQLException
     */
    private int getPlayedGames(Player p) throws SQLException {
        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM PARTIE natural join JOUER where pseudo=? or adversaire=?");
        ps.setString(1, p.getPseudo());
        ps.setString(2, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }

    private int getAbandonedGames(Player p) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select COUNT(*) from PARTIE where currentPlayer=? and state=-2");
        ps.setString(1, p.getPseudo());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }

    public PlayerStatistics getPlayerStatistics(Player p) throws SQLException {
        return new PlayerStatistics(
                this.getVictories(p),
                this.getDefeat(p),
                this.getActiveGames(p),
                this.getConsecutiveWins(p),
                this.getPlayedGames(p),
                this.getAbandonedGames(p)
        );
    }


//    private int getActiveGames() throws SQLException{
//        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM PARTIE where winner = ?");
//        ResultSet ag = ps.executeQuery();
//        return ag.getInt(1);
//    }

}