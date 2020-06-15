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
     * It shouldn't be used in any other ways, all the attributes are initialized to null
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

    public char[][] getPlate() {
        return this.plate;
    }

    @Override
    public String getGameName() {
        return this.gameName;
    }

    @Override
    public int getGameID() {
        return this.gameID;
    }

    public int getElementPlaced() {
        return this.elementPlacedCount;
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
    public void setNomJeu(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setElementPlacedCount(int elementPlaced) {
        this.elementPlacedCount = elementPlaced;
    }

    public void setPlate(char[][] plate) {
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

    public boolean checkWin() {
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if(plate[i][j] != '*' && checkPosition(i, j))
                    return true;
            }
        }

        return false;
    }

    private boolean inPlate(int x, int y) {
        return x >= 0 && x < 7 && y >= 0 && y < 7;
    }

    /**
     * @param x the x position of the case
     * @param y the y position of the case
     * @return a boolean : true if there's at least 4 consecutive case with
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

            if(consecutive >= 4)
                return true;
        }

        return false;
    }

    private char[][] initPlate() {
        char[][] plate = new char[7][7];

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++)
                plate[i][j] = '*';
        }

        return plate;
    }

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