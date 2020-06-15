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
        /** Return an int indicating the number of games the player played */

        return playedGames;
    }

    public int getWinnedGames() {
        /** Return an int indicating the number of games the player won */

        return winnedGames;
    }

    public double getTurnPlayedPerGamesAverage() {
        /** Return a double indicating the average number of turn per game of the player */

        return turnPlayedPerGamesAverage;
    }

    public int getActiveGames() {
        /** Return an int indicating the number of active games of the player */

        return activeGames;
    }

    public int getAbandonedGames() {
        /** Return an int indicating the number of games the player abandoned */

        return abandonedGames;
    }
}
