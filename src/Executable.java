import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        c.connexion();
        //Player p = new Player("test", "test@gmail.com", "cocopops", null, 0, false, false);
        //c.createPlayer(p);
        Player coco = c.getPlayer("Coco");
        Player p0slx = c.getPlayer("p0slx");
        System.out.println("game list  " + c.getGameList());
        ArrayList<Game> gameList = c.getGameList();
        System.out.println("getActiveGames  " + c.getActivesGames(p0slx));

        //System.out.println(p0slx.getFriends());
        //c.createInv(coco, p0slx);
        //coco.setAdmin(true);
        //c.updatePlayer(coco);

    }
}
