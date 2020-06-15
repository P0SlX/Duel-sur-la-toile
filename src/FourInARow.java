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

    /**
     * @return int score of a game
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * @return int state of a game
     */
    @Override
    public int getState() {
        return this.state;
    }

    /**
     * @return Date (sql) when start the game
     */
    @Override
    public java.sql.Date getStartTime() {
        return this.startTime;
    }

    /**
     * @return Date (sql) when finish the game
     */
    @Override
    public java.sql.Date getFinishTime() {
        /** return the date when the game finished */

        return this.finishTime;
    }

    /**
     * @return String, the plate of a game
     */
    public String getPlate() {
        return this.plate;
    }

    /**
     * @return String the name of the game
     */
    @Override
    public String getNomJeu() {
        return this.nomJeu;
    }

    /**
     * @return int, the id of a game
     */
    @Override
    public int getGameID() {
        return this.gameID;
    }

    /**
     * @return int, the number of pawns placed
     */
    public int getElementPlaced() {
        return this.elementPlaced;
    }

    /**
     * @return long, the duration of a game
     */
    @Override
    public long getGameDuration() {
        return this.finishTime.getTime() - this.startTime.getTime();
    }

    /**
     * @return An Array of players which contains the players of a game
     */
    @Override
    public Player[] getPlayers() {
        return new Player[]{this.player1, this.player2};
    }

    /**
     * @return Player whose the turn it is
     */
    @Override
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * @return Player, the winner of a game
     */
    @Override
    public Player getWinner() {
        return this.winner;
    }

    /**
     * @return Player, the looser of a game
     */
    @Override
    public Player getLooser() {
        return this.looser;
    }

    /**
     * @return Player, the first player of a game
     */
    @Override
    public Player getPlayer1() {
        return this.player1;
    }

    /**
     * @return Player, the second player of a game
     */
    @Override
    public Player getPlayer2() {
        return this.player2;
    }

    /**
     * Allow to modify the name of the game
     * @param nomJeu String, the new name of the game
     */
    @Override
    public void setNomJeu(String nomJeu) {
        this.nomJeu = nomJeu;
    }

    /**
     * Allow to modify the player whose turn it is
     * @param currentPlayer Player, the player whose turn it is
     */
    @Override
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Allow to change the id of the game
     * @param gameID int, the new id
     */
    @Override
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Allow to change the number of pawns placed
     * @param elementPlaced int, the new number of pawns placed
     */
    public void setElementPlaced(int elementPlaced) {
        this.elementPlaced = elementPlaced;
    }

    /**
     * Allow to change the plate of a game
     * @param plate String, the new plate
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Allow to modify the start time of a game
     * @param startTime Date (sql) the new start time
     */
    @Override
    public void setStartTime(java.sql.Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Allow to chnage the finish time of a game
     * @param finishTime Date (sql) the new finish time
     */
    @Override
    public void setFinishTime(java.sql.Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * Allow to change the inner of a game
     * @param winner Player, the new winner of a game
     */
    @Override
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Allow to change the looser of a game
     * @param looser Player, the new looser of the game
     */
    @Override
    public void setLooser(Player looser) {
        this.looser = looser;
    }

    /**
     * Allow to change the score of a game
     * @param score int, the new score
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * Allow to change the state of a game
     * @param state int, the state of the game
     *              - canceled = -2
     *              - ended = -1
     *              - in_progress = 0
     */
    @Override
    public void setState(int state) {
        this.state = state;
    }


}