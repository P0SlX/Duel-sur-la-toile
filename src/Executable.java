import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        Player Anatole = new Player("Anatole", "mail22", new ArrayList<Byte>() , true, new ArrayList<Player>());
        c.setStatus(Anatole, c.CONNECTED);
        System.out.println(c.getStatus(Anatole));
        c.setStatus(Anatole, c.DISCONNECTED);
        System.out.println(c.getStatus(Anatole));
    }
}
