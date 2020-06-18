public class Admin extends Player {
    public Admin(String pseudo, String email, String mdp, String avatar, int etat, boolean activated, boolean admin) {
        super(pseudo, email, mdp, avatar, etat, activated, admin);
    }

    public AdminStatistics getAdminStatistics() {
        return null;
    }
}
