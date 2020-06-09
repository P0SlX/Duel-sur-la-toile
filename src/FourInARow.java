public class FourInARow implements Game{

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private String plate;
    private String startTime;
    private String finishTime;
    private int elementPlaced;
    private int gameID;
    private int state;
    private String nomJeu;
    private Player winner;
    private Player looser;

    public FourInARow(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.plate = "";        // TODO
        this.startTime = "";
        this.finishTime = "";
        this.elementPlaced = 0;
        this.gameID = -1;
        this.nomJeu = "Puissance 4";
        this.winner = null;
        this.looser = null;
        this.state = 0;
    }

    @Override
    public String getStartTime() {
        return this.startTime;
    }

    @Override
    public String getFinishTime() {
        return this.finishTime;
    }

    @Override
    public String getPlate() {
        return this.plate;
    }

    @Override
    public String getNomJeu() {
        return this.nomJeu;
    }

    @Override
    public int getGameID() {
        return this.gameID;
    }

    @Override
    public int getElementPlaced() {
        return this.elementPlaced;
    }

    @Override
    public long getGameDuration() {
        return 0;                           // TODO
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
        return this.winner;
    }

    @Override
    public Player getLooser() {
        return this.looser;
    }

    @Override
    public Player getPlayer1() {
        return this.player1;
    }

    @Override
    public Player getPlayer2() {
        return this.player2;
    }

    @Override
    public void setNomJeu(String nomJeu) {
        this.nomJeu = nomJeu;
    }

    @Override
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void setElementPlaced(int elementPlaced) {
        this.elementPlaced = elementPlaced;
    }

    @Override
    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    @Override
    public void setLooser(Player looser) {
        this.looser = looser;
    }

}