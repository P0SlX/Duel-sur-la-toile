import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        c.connexion();
        Player Anatole = new Player("L'ananas", "cocolastico@gmail.com", "cocopops", new ArrayList<>(), 0, true, false, new ArrayList<>());
        Player p0slx = new Player("p0slx", "florian.savoure@gmail.com", "unMotDePasseSecure", new ArrayList<>(), 0, true, false ,new ArrayList<>());
        c.setStatus(Anatole, Anatole.CONNECTED);
        System.out.println(c.getStatus(Anatole));
        c.setStatus(Anatole, Anatole.DISCONNECTED);
        System.out.println(c.getStatus(Anatole));
        Game g1 = new FourInARow(Anatole, p0slx);
        System.out.println(c.getMaxIDGame());
        System.out.println(g1.getNomJeu());
        c.addNewGame(Anatole, p0slx, g1);

    }
}
