import com.sun.javafx.css.CalculatedValue;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    public static final int DISCONNECTED = 0;
    public static final int CONNECTED    = 1;
    public static final int AFK          = 2;
    public static final int DND          = 3;

    private String pseudo;
    private String email;
    private String mdp;
    private String avatar;
    private Image playerAvatar;
    private ArrayList<Player> friends;
    private int etat;
    private boolean desactivated;
    private boolean admin;
    private DatabaseConnection DB;

    /**
     * Builder of a player
     * @param pseudo String, the player's pseudo
     * @param email String, the player's email
     * @param mdp String, the player's password
     * @param avatar String, the player's profil picture path
     * @param etat int, the situation of a player
     *         - disconnected = 0
     *         - connected = 1
     *         - afk = 2
     *         - do not disturb = 3
     * @param desactivated boolean, the situation of an account
     *         - false = account not desactivated
     *         - true = account desactivated
     * @param admin boolean, is the player an admin or not ?
     *         - false = player isn't an admin
     *         - true = player is an admin
     */
    public Player(String pseudo, String email, String mdp, String avatar, int etat, boolean desactivated, boolean admin) {
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
        this.avatar = avatar;
        this.etat = etat;
        this.desactivated = desactivated;
        this.admin = admin;
        this.friends = null;
    }

    public Player(String pseudo, String email, String mdp, Image playerAvatar, int etat, boolean desactivated, boolean admin) {
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
        this.avatar = "";
        this.etat = etat;
        this.desactivated = desactivated;
        this.admin = admin;
        this.friends = null;
        this.playerAvatar = playerAvatar;
    }

    /**
     * @return String le pseudo d'un joueur
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Allow to chnage the pseudo of a player
     * @param pseudo String, the new pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * @return String, the player's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Allow to change a player's email
     * @param email String, the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String, the player's password
     */
    public String getMdp() {
        return this.mdp;
    }

    /**
     * Allow to change a player's password
     * @param mdp String, the new password
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * @return String, the path ot the player's avatar
     */
    public String getAvatar() {
        return this.avatar;
    }

    /**
     * @return int the state of a player:
     *  - disconnected = 0
     *  - connected = 1
     *  - afk = 2
     *  - do not disturb = 3
     */
    public int getEtat() {
        return this.etat;
    }

    /**
     * Allow to change the path to a player's profil picture
     * @param avatar String, the new path
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Allow to change the state of a player
     * @param etat int, the new etat of the player
     */
    public void setEtat(int etat) {
        this.etat = etat;
    }

    /**
     * @return boolean, the situation of a player's account
     * - false: the account is not desactivated
     * - true: the account is desactivated
     */
    public boolean isDesactivated() {
        return this.desactivated;
    }

    /**
     * @param desactivated boolean, the situation of a player's account
     * - false: the account is not desactivated
     * - true: the account is desactivated
     */
    public void setDesactivated(boolean desactivated) {
        this.desactivated = desactivated;
    }


    /**
     * @return boolean, true = player is admin and false = player isn't admin
     */
    public boolean isAdmin() {
        return this.admin;
    }

    /**
     * @param admin boolean,
     *              - true = the player is going to be admin
     *              - false = the player isn't going to be admin anymore
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return ArrayList of player, the player's friends list
     */
    public ArrayList<Player> getFriends() {
        return this.friends;
    }

    /**
     * @param friends ArrayList of Player, the new player's friend list
     */
    public void setFriends(ArrayList<Player> friends) {
        this.friends = friends;
    }

    /**
     * @return String, the toString of a player retailing each parameters of this
     */
    @Override
    public String toString() {
        return "Player{" +
                "pseudo='" + this.pseudo + '\'' +
                ", email='" + this.email + '\'' +
                ", mdp='" + this.mdp + '\'' +
                ", avatar='" + this.avatar + '\'' +
                ", friends=" + this.friends +
                ", etat=" + this.etat +
                ", desactivated=" + this.desactivated +
                ", admin=" + this.admin +
                '}';
    }

    /**
     * @return Image, the player's profil picture
     */
    public Image getPlayerAvatar() {
        return this.playerAvatar;
    }

    /**
     * @param playerAvatar Image, the new player's profil picture
     */
    public void setPlayerAvatar(Image playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}
