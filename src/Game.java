public interface Game {
    String getStartTime();
    String getFinishTime();
    String getNomJeu();
    int getGameID();
    int getScore();
    int getState();
    long getGameDuration();
    Player[] getPlayers();
    Player getCurrentPlayer();
    Player getWinner();
    Player getLooser();
    Player getPlayer1();
    Player getPlayer2();
    void setNomJeu(String nomJeu);
    void setCurrentPlayer(Player currentPlayer);
    void setGameID(int gameID);
    void setStartTime(String startTime);
    void setFinishTime(String finishTime);
    void setWinner(Player winner);
    void setLooser(Player looser);
    void setScore(int score);
    void setState(int state);
}
