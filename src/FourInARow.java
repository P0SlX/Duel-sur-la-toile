import com.sun.media.jfxmediaimpl.platform.Platform;

import java.sql.Date;

public class FourInARow implements Game {

    public static final int CANCELED  = -1;
    public static final int IN_PROGRESS  = 0;
    public static final int ENDED = 1;

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private String plate;
    private java.sql.Date startTime;
    private java.sql.Date finishTime;
    private int elementPlaced;
    private int gameID;
    private int state;
    private int score;
    private String nomJeu;
    private Player winner;
    private Player looser;

    public FourInARow(Player player1, Player player2, Player currentPlayer, String plate, java.sql.Date startTime, java.sql.Date finishTime, int elementPlaced, int gameID, int state, int score, String nomJeu, Player winner, Player looser) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.plate = plate;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.elementPlaced = elementPlaced;
        this.gameID = gameID;
        this.state = state;
        this.score = score;
        this.nomJeu = nomJeu;
        this.winner = winner;
        this.looser = looser;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public java.sql.Date getStartTime() {
        return this.startTime;
    }

    @Override
    public java.sql.Date getFinishTime() {
        return this.finishTime;
    }

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

    public int getElementPlaced() {
        return this.elementPlaced;
    }

    @Override
    public long getGameDuration() {
        return finishTime.getTime() - startTime.getTime();
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

    public void setElementPlaced(int elementPlaced) {
        this.elementPlaced = elementPlaced;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public void setStartTime(java.sql.Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setFinishTime(java.sql.Date finishTime) {
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

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }


}