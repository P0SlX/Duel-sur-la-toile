import org.json.JSONArray;
import org.json.JSONException;
import util.Pair;

import java.sql.SQLException;

public class FourInARow implements Game {

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private char[][] plate;
    private java.sql.Date startTime;
    private java.sql.Date finishTime;
    private int elementPlacedCount;
    private int gameID;
    private int state;
    private int score;
    private String gameName;
    private Player winner;
    private Player looser;

    private final Pair<Integer, Integer>[] DIRECTIONS = new Pair[]{
            new Pair<>(-1, 0),  // NORTH
            new Pair<>(1, 0),   // SOUTH
            new Pair<>(0, -1),  // WEST
            new Pair<>(0, 1),   // EAST
            new Pair<>(-1, -1), // NORTH WEST
            new Pair<>(1, 1),   // South WEST
            new Pair<>(1, -1),  // South EAST
            new Pair<>(-1, 1)   // North EAST
    };

    static private DatabaseConnection databaseConnection;

    public static void setDatabaseConnection(DatabaseConnection connection) {
        FourInARow.databaseConnection = connection;
    }

    public FourInARow(Player player1, Player player2, Player currentPlayer, String plate, java.sql.Date startTime,
                      java.sql.Date finishTime, int elementPlaced, int gameID, int state, int score,
                      String gameName, Player winner, Player looser) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.plate = jsonToPlate(plate);
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.elementPlacedCount = elementPlaced;
        this.gameID = gameID;
        this.state = state;
        this.score = score;
        this.gameName = gameName;
        this.winner = winner;
        this.looser = looser;
    }

    /**
     * This constructor is for the test class.
     * It shouldn't be used in any other way, all the attributes are initialized to null
     * @param plate a game plate
     */
    public FourInARow(char[][] plate) {
        this.plate = plate;

        this.player1 = null;
        this.player2 = null;
        this.currentPlayer = null;
        this.startTime = null;
        this.finishTime = null;
        this.elementPlacedCount = 0;
        this.gameID = 0;
        this.state = 0;
        this.score = 0;
        this.gameName = "FourInARow";
        this.winner = null;
        this.looser = null;
    }

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
     * @return char[][], the plate of a game
     */
    public char[][] getPlate() {
        return this.plate;
    }

    /**
     * @return String the name of the game
     */
    @Override
    public String getGameName() {
        return this.gameName;
    }

    /**
     * @return int, the id of a game
     */
    @Override
    public int getGameID() {
        return this.gameID;
    }

    /**
     * @return int, the number of placed pawns
     */
    public int getElementPlaced() {
        return this.elementPlacedCount;
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
     * @return Player whose turn it is
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
     * @param gameName String, the new name of the game
     */
    @Override
    public void setNomJeu(String gameName) {
        this.gameName = gameName;
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
     * Setter for the game plate
     * @param jsonCode the new game plate represented in JSON
     */
    public void setPlate(String jsonCode) {
        this.plate = jsonToPlate(jsonCode);
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
     * Allow to change the finish time of a game
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

    /**
     * @param p The player that is playing
     * @param x the x coordinate of the selected case
     * @param y the y coordinate of the selected case
     * @return a boolean : is it possible to play here ?
     * @throws SQLException if something went wrong with the database
     */
    public boolean playerPlayTurn(Player p, int x, int y) throws SQLException {
        if(inPlate(x, y)) {
            if(player1.equals(p)) {
                if(plate[x][y] == '*') {
                    char PLAYER1 = 'R';
                    plate[x][y] = PLAYER1;

                    if(checkWin()) {
                        this.winner = player1;
                        this.looser = player2;
                    }

                    databaseConnection.updateFourInARowPlate(this);
                    return true;
                }
            } else if(player2.equals(p)) {
                if(plate[x][y] == '*') {
                    char PLAYER2 = 'B';
                    plate[x][y] = PLAYER2;

                    if(checkWin()) {
                        this.winner = this.player2;
                        this.looser = this.player1;
                    }

                    databaseConnection.updateFourInARowPlate(this);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if a player won the game
     * @return a boolean : true if someone won the game
     */
    public boolean checkWin() {
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if(plate[i][j] != '*' && checkPosition(i, j))
                    return true;
            }
        }

        return false;
    }

    /**
     * Check if the coordinate is in the plate
     * @param x the x position
     * @param y the y position
     * @return a boolean, true if it is in the plate
     */
    private boolean inPlate(int x, int y) {
        return x >= 0 && x < 7 && y >= 0 && y < 7;
    }

    /**
     * @param x the x position of the case
     * @param y the y position of the case
     * @return a boolean : true if there's at least 4 consecutive cases with
     *          the same colour in any direction
     */
    private boolean checkPosition(int x, int y) {
        char side = plate[x][y];

        for(var dir : DIRECTIONS) {
            int line = x + dir.getFirst();
            int column = y + dir.getSecond();
            int consecutive = 1;

            while(inPlate(line, column) && plate[line][column] == side) {
                consecutive++;
                line += dir.getFirst();
                column += dir.getSecond();
            }

            if(consecutive >= 4) {
                if(side == 'R') {
                    this.winner = this.player1;
                    this.looser = this.player2;
                } else {
                    this.winner = this.player2;
                    this.looser = this.player1;
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Create a new Plate
     * @return A brand new game plate initialized with blank (*)
     */
    private char[][] initPlate() {
        char[][] plate = new char[7][7];

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++)
                plate[i][j] = '*';
        }

        return plate;
    }

    /**
     * Convert JSON code to its Java representation
     * @param jsonCode A String which contains the JSON code
     * @return a char[][] which holds the game plate
     */
    private char[][] jsonToPlate(String jsonCode) {
        char[][] res = new char[7][7];

        try {
            JSONArray jsonArray = new JSONArray(jsonCode);

            for(int i = 0; i < 7; i++) {
                JSONArray line = (JSONArray)jsonArray.get(i);

                for(int j = 0; j < 7; j++)
                    res[i][j] = ((String)line.get(j)).charAt(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}