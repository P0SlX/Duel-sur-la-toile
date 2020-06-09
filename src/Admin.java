import java.util.ArrayList;

public class Admin extends Player {
    public Admin(String pseudo, String email, String mdp, ArrayList<Byte> avatar, int etat, boolean activated, boolean admin, ArrayList<Player> friends) {
        super(pseudo, email, mdp, avatar, etat, activated, admin, friends);
    }

    public AdminStatistics getAdminStatistics() {
        return null;
    }
}
