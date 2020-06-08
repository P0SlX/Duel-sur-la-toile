public class FourInARow implements Game{

    private String plate;
    private String startTime;
    private int elementPlaced;
    private int gameID;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private String nomJeu;

    public FourInARow(String startTime, Player player1, Player player2){
        this.startTime = startTime;
        this.player1 = player1;
        this.player2 = player2;
        this.plate = "";
        this.elementPlaced = 0;
        this.currentPlayer = null; //TODO
        this.nomJeu = "Puissance 4";
    }

    @Override
    public String getStartTime() {
        return this.startTime;
    }

    @Override
    public int getGameID() {
        return this.gameID;
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
    public String getNomJeu() {
        return this.nomJeu;
    }

    @Override
    public Player[] getPlayers() {
        return new Player[]{this.player1, this.player2};
    }

    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public Player getWinner() {
        return null;
    }
}