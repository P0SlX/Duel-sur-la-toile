import com.sun.javafx.css.CalculatedValue;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    public final int DISCONNECTED = 0;
    public final int CONNECTED    = 1;
    public final int AFK          = 2;
    public final int DND          = 3;

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
        this.avatar = new String();
        this.etat = etat;
        this.desactivated = desactivated;
        this.admin = admin;
        this.friends = null;
        this.playerAvatar = playerAvatar;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public boolean isDeactivated() {
        return this.desactivated;
    }

    public void setDeactivated(boolean desactivated) {
        this.desactivated = desactivated;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Player> getFriends() {
        return this.friends;
    }

    public void setFriends(ArrayList<Player> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "Player{" +
                "pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", friends=" + friends +
                ", etat=" + etat +
                ", desactivated=" + desactivated +
                ", admin=" + admin +
                '}';
    }

    public Image getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(Image playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}
