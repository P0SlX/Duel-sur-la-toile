import java.util.ArrayList;

public class Player {
    public final int DISCONNECTED = 0;
    public final int CONNECTED    = 1;
    public final int AFK          = 2;
    public final int DND          = 3;

    private String name;
    private String email;
    private ArrayList<Byte> avatar;
    private boolean activated;
    private ArrayList<Player> friends;

    public Player(String name, String email, ArrayList<Byte> avatar, boolean activated, ArrayList<Player> friends) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.activated = activated;
        this.friends = friends;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public ArrayList<Byte> getAvatar() {
        return this.avatar;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public ArrayList<Player> getFriends() {
        return this.friends;
    }
}
