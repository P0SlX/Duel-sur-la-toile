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

    /* return playser's pseudo */
    public String getPseudo() {
        return this.pseudo;
    }

    /* modify player's pseudo */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /* return player's email */
    public String getEmail() {
        return this.email;
    }

    /* modify player's email */
    public void setEmail(String email) {
        this.email = email;
    }

    /* return player's password */
    public String getMdp() {
        return this.mdp;
    }

    /* modify player's password */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /* return player's profil picture path */
    public String getAvatar() {
        return this.avatar;
    }

    /* modify player's profil picture path */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /* return player's situation:
        - disconnected = 0
        - connected = 1
        - afk = 2
        - do not disturb = 3
     */
    public int getEtat() {
        return this.etat;
    }

    /* modify player's situation */
    public void setEtat(int etat) {
        this.etat = etat;
    }

    /* return true if the player's account is decativated,
    return false if the player's account is not desactivated
     */
    public boolean isDeactivated() {
        return this.desactivated;
    }

    /* allow to activate or desactivate an account
       only admin can do this
     */
    public void setDeactivated(boolean desactivated) {
        this.desactivated = desactivated;
    }

    /* return true if the account is an admin account
       return false if the account is not an admin acount
     */
    public boolean isAdmin() {
        return this.admin;
    }

    /* allow to a player, admin's permission
       or take off an admin his permission
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /* return player's friend list */
    public ArrayList<Player> getFriends() {
        return this.friends;
    }

    /* modify player's friend list */
    public void setFriends(ArrayList<Player> friends) {
        this.friends = friends;
    }

    /* toString of a player */
    @Override
    public String toString() {
        return "Player{" +
                "pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", avatar='" + avatar + '\'' +
                ", friends=" + friends +
                ", etat=" + etat +
                ", desactivated=" + desactivated +
                ", admin=" + admin +
                '}';
    }

    /* return the profil picture of a player */
    public Image getPlayerAvatar() {
        return playerAvatar;
    }

    /* modify the profil picture of a player */
    public void setPlayerAvatar(Image playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}
