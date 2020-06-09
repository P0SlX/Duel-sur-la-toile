public interface Game {
    public String getStartTime();
    public String getFinishTime();
    public String getNomJeu();
    public int getGameID();
    public long getGameDuration();
    public Player[] getPlayers();
    public Player getCurrentPlayer();
    public Player getWinner();
}
