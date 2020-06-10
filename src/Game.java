public interface Game {
    java.sql.Date getStartTime();
    java.sql.Date getFinishTime();
    String getNomJeu();
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
    void setStartTime(java.sql.Date startTime);
    void setFinishTime(java.sql.Date finishTime);
    void setLooser(Player looser);
    void setWinner(Player winner);
    void setScore(int score);
    void setState(int state);
}
