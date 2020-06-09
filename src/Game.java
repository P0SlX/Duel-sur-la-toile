public interface Game {
    String getStartTime();
    String getFinishTime();
    String getPlate();
    String getNomJeu();
    int getGameID();
    int getElementPlaced();
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
    void setElementPlaced(int elementPlaced);
    void setPlate(String plate);
    void setStartTime(String startTime);
    void setFinishTime(String finishTime);
    void setWinner(Player winner);
    void setLooser(Player looser);
}
