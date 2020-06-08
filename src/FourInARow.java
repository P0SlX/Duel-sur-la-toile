public class FourInARow implements Game{

    private String plate;
    private String startTime;
    private int elementPlaced;
    private Player currentPlayer;
    private Player player1;
    private Player player2;

    public FourInARow(String startTime, Player player1, Player player2){
        this.startTime = startTime;
        this.player1 = player1;
        this.player2 = player2;
        this.plate = new String();
        this.elementPlaced = 0;
        this.currentPlayer = null; //TODO
    }

    @Override
    public String getStartTime() {
        return null;
    }

    @Override
    public int getGameID() {
        return 0;
    }

    @Override
    public long getGameDuration() {
        return 0;
    }

    @Override
    public String getFinishTime() {
        return null;
    }

    @Override
    public Player[] getPlayers() {
        return new Player[0];
    }

    @Override
    public Player getWinner() {
        return null;
    }
}