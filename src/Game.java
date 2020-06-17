public interface Game {
    public static final int CANCELED    = -2;
    public static final int ENDED       = -1;
    public static final int IN_PROGRESS = 0;

    String getStartTime();
    String getFinishTime();
    String getGameName();
    int getGameID();
    int getScore();
    int getState();
    long getGameDuration();
    Player[] getPlayers();
    Player getCurrentPlayer();
    Player getLooser();
    Player getWinner();
    Player getPlayer1();
    Player getPlayer2();
    void setNomJeu(String nomJeu);
    void setCurrentPlayer(Player currentPlayer);
    void setGameID(int gameID);
    void setStartTime(String startTime);
    void setFinishTime(String finishTime);
    void setLooser(Player looser);
    void setWinner(Player winner);
    void setScore(int score);
    void setState(int state);
    void switchCurrentPlayer();
}
