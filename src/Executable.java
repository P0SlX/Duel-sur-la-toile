import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Executable {
    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConnection c = new DatabaseConnection();
        c.connexion();
        //Player p = new Player("test", "test@gmail.com", "cocopops", null, 0, false, false);
        //c.createPlayer(p);
        //Player coco = c.getPlayer("Coco");
        Player p0slx = c.getPlayer("p0slx");
        p0slx.setAvatar("img/arouf_vogue.png");
        c.updatePlayer(p0slx);
        System.out.println(p0slx.getAvatar());
        for (Game g : c.getGameList()) {
            System.out.println(g);
        }
        //System.out.println("game list  " + c.getGameList());
        //ArrayList<Game> gameList = c.getGameList();
        //System.out.println("getActiveGames  " + c.getActivesGames(p0slx));

        //System.out.println(p0slx.getFriends());
        //c.createInv(coco, p0slx);
        //coco.setAdmin(true);
        //c.updatePlayer(coco);


        //c.createInv(coco, p0slx,false);

        //Player player = new Player("dummy", "dummy@dum", "password", "UI/assets/logo.png",
                //0, false, false);

        //c.createPlayer(player);

    }
}
