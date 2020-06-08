public interface Game {
    public String getStartTime();
    public int getGameID();
    public long getGameDuration();
    public String getFinishTime();
    public Player[] getPlayers();
    public Player getWinner();
}
