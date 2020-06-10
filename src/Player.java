import java.util.ArrayList;

public class Player {
    public final int DISCONNECTED = 0;
    public final int CONNECTED    = 1;
    public final int AFK          = 2;
    public final int DND          = 3;

    private String pseudo;
    private String email;
    private String mdp;
    private ArrayList<Byte> avatar;
    private ArrayList<Player> friends;
    private int etat;
    private boolean activated;
    private boolean admin;
    private DatabaseConnection DB;

    public Player(String pseudo, String email, String mdp, ArrayList<Byte> avatar, int etat, boolean activated, boolean admin, ArrayList<Player> friends) {
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
        this.avatar = avatar;
        this.etat = etat;
        this.activated = activated;
        this.admin = admin;
        this.friends = friends;
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

    public ArrayList<Byte> getAvatar() {
        return this.avatar;
    }

    public void setAvatar(ArrayList<Byte> avatar) {
        this.avatar = avatar;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
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
}
