import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        Player Anatole = new Player("Anatole", "mail22", new ArrayList<Byte>() , true, new ArrayList<Player>());
        c.setEtat(Anatole, 0);
        System.out.println(c.getEtat(Anatole));
        c.setEtat(Anatole, 1);
        System.out.println(c.getEtat(Anatole));
    }
}
