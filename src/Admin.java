import java.util.ArrayList;

public class Admin extends Player {
    public Admin(String name, String email, ArrayList<Byte> avatar, boolean activated, ArrayList<Player> friends) {
        super(name, email, avatar, activated, friends);
    }

    public AdminStatistics getAdminStatistics() {
        return null;
    }
}
