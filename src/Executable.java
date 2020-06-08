import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        Player Anatole = new Player("L'ananas", "cocolastico@gmail.com", new ArrayList<Byte>() , true, new ArrayList<Player>());
        Player p0slx = new Player("p0slx", "florian.savoure@gmail.com", new ArrayList<Byte>() , true, new ArrayList<Player>());
        c.setStatus(Anatole, c.CONNECTED);
        System.out.println(c.getStatus(Anatole));
        c.setStatus(Anatole, c.DISCONNECTED);
        System.out.println(c.getStatus(Anatole));
        Game g1 = new FourInARow("00:00", Anatole, p0slx);
        System.out.println(c.getMaxIDGame());
        System.out.println(g1.getNomJeu());
        c.addNewGame(Anatole, p0slx, g1);

    }
}
