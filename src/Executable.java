import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        Player Anatole = new Player("Anatole", "mail22", new ArrayList<Byte>() , true, new ArrayList<Player>());
        Player p0slx = new Player("p0slx", "mail12", new ArrayList<Byte>() , true, new ArrayList<Player>());
        c.setStatus(Anatole, c.CONNECTED);
        System.out.println(c.getStatus(Anatole));
        c.setStatus(Anatole, c.DISCONNECTED);
        System.out.println(c.getStatus(Anatole));
        FourInARow g1 = new FourInARow("00:00", Anatole, p0slx);

    }
}
