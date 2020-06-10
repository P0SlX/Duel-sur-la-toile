public class Executable {
    public static void main(String[] args) {
        DatabaseConnection c = new DatabaseConnection();
        c.connexion();
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
        //Player p = new Player("test", "test@gmail.com", "cocopops", null, 0, false, false);
        //c.createPlayer(p);
        Player coco = c.getPlayer("Coco");
        Player p0slx = c.getPlayer("p0slx");
        System.out.println(p0slx.getFriends());
        //c.createInv(coco, p0slx);
        //coco.setAdmin(true);
        //c.updatePlayer(coco);

    }
}
