import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        c.connexion();
//        Player Anatole = new Player("L'ananas", "cocolastico@gmail.com", "cocopops", new ArrayList<>(), 0, true, false);
//        Player p0slx = new Player("p0slx", "florian.savoure@gmail.com", "unMotDePasseSecure", new ArrayList<>(), 0, true, false);
//        c.setStatus(Anatole, Anatole.CONNECTED);
//        System.out.println(c.getStatus(Anatole));
//        c.setStatus(Anatole, Anatole.DISCONNECTED);
//        System.out.println(c.getStatus(Anatole));
//        Game g1 = new FourInARow(Anatole, p0slx);
        System.out.println(c.getMaxIDGame());
//        System.out.println(g1.getNomJeu());
        //c.addNewGame(Anatole, p0slx, g1);

        System.out.println(c.getPlayer("p0slx"));
        c.getGameList();
        c.getPlayer("p0slx");
        Player p = new Player("test", "test@gmail.com", "cocopops", null, 0, false, false);
        c.createPlayer(p);

    }
}
