import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        Player Anatole = new Player("Anatole", "mail22", new ArrayList<Byte>() , true, new ArrayList<Player>());
        System.out.println(c.getEtat(Anatole));

    }
}
