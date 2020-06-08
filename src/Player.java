import java.util.ArrayList;

public class Player {

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
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Byte> getAvatar() {
        return avatar;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArrayList<Player> getFriends() {
        return friends;
    }
}
