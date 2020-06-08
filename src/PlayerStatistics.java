public class PlayerStatistics {

    private int playedGames;
    private int winnedGames;
    private double turnPlayedPerGamesAverage;
    private int activeGames;
    private int abandonedGames;

    public PlayerStatistics(int playedGames, int winnedGames, double turnPlayedPerGamesAverage, int activeGames, int abandonedGames){
        this.playedGames=playedGames;
        this.winnedGames=winnedGames;
        this.turnPlayedPerGamesAverage=turnPlayedPerGamesAverage;
        this.activeGames=activeGames;
        this.abandonedGames=abandonedGames;

    }

    public int getPlayedGames() {
        return playedGames;
    }

    public int getWinnedGames() {
        return winnedGames;
    }

    public double getTurnPlayedPerGamesAverage() {
        return turnPlayedPerGamesAverage;
    }

    public int getActiveGames() {
        return activeGames;
    }

    public int getAbandonedGames() {
        return abandonedGames;
    }
}
