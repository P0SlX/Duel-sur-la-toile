import com.sun.media.jfxmediaimpl.platform.Platform;

import java.sql.Date;

public class FourInARow implements Game {

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

    /* return the score of a game by an int */
    @Override
    public int getScore() {
        return this.score;
    }

    /* return the situation of a game */
    @Override
    public int getState() {
        return this.state;
    }

    /* return the date when the game started */
    @Override
    public java.sql.Date getStartTime() {
        return this.startTime;
    }

    /* return the date when the game finished */
    @Override
    public java.sql.Date getFinishTime() {
        return this.finishTime;
    }

    /* return the path of the .json file where the plate is stock */
    public String getPlate() {
        return this.plate;
    }

    /* return the name of the game */
    @Override
    public String getNomJeu() {
        return this.nomJeu;
    }

    /* return the id of the game indicated by an int */
    @Override
    public int getGameID() {
        return this.gameID;
    }

    /* return an int indicating the number of pawns played */
    public int getElementPlaced() {
        return this.elementPlaced;
    }

    /* return the duration of a game */
    @Override
    public long getGameDuration() {
        return finishTime.getTime() - startTime.getTime();
    }

    /* return the two player of a game in an Array*/
    @Override
    public Player[] getPlayers() {
        return new Player[]{this.player1, this.player2};
    }

    /* return the player whose turn it is */
    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /* return the winner of a game */
    @Override
    public Player getWinner() {
        return this.winner;
    }

    /* return the looser of a game */
    @Override
    public Player getLooser() {
        return this.looser;
    }

    /* return the first player of a game */
    @Override
    public Player getPlayer1() {
        return this.player1;
    }

    /* return the second player of a game */
    @Override
    public Player getPlayer2() {
        return this.player2;
    }

    /* allows to change the name of a game */
    @Override
    public void setNomJeu(String nomJeu) {
        this.nomJeu = nomJeu;
    }

    /* allow to change the player whose turn it is */
    @Override
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /* allow to change the id of a game */
    @Override
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /* allow to change the number of pawns placed */
    public void setElementPlaced(int elementPlaced) {
        this.elementPlaced = elementPlaced;
    }

    /* allows to change the path to the .json file where the grid of the game is stock */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /* allow to change the start time of a game */
    @Override
    public void setStartTime(java.sql.Date startTime) {
        this.startTime = startTime;
    }

    /* allow to change to finish time of a game */
    @Override
    public void setFinishTime(java.sql.Date finishTime) {
        this.finishTime = finishTime;
    }

    /* allow to chnager the winner of a game */
    @Override
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /* allow to change the looser of a game */
    @Override
    public void setLooser(Player looser) {
        this.looser = looser;
    }

    /* allow the change the score of a game */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /* change the statement of a game:
        - canceled = -2
        - ended = -1
        - in_progress = 0
     */
    @Override
    public void setState(int state) {
        this.state = state;
    }


}