public class PlayerStatistics {

    private int playedGames;
    private int wonGames;
    private double turnPlayedPerGamesAverage;
    private int activeGames;
    private int abandonedGames;

    /**
     * builder of PlayerStatistic
     * @param playedGames int, the number of game played by a player
     * @param wonGames int, the number of game won by a player
     * @param turnPlayedPerGamesAverage, double, the average number of turn played by game
     * @param activeGames int, number of game in_progress
     * @param abandonedGames int, number of game abandonned
     */
    public PlayerStatistics(int playedGames, int wonGames, double turnPlayedPerGamesAverage, int activeGames, int abandonedGames){
        this.playedGames=playedGames;
        this.wonGames=wonGames;
        this.turnPlayedPerGamesAverage=turnPlayedPerGamesAverage;
        this.activeGames=activeGames;
        this.abandonedGames=abandonedGames;

    }

    /**
     * @return int, the number of game played
     */
    public int getPlayedGames() {
        return this.playedGames;
    }

    /**
     * @return int, the number of game won
     */
    public int getWonGames() {
        return this.wonGames;
    }

    /**
     * @return double, the average number of turn per game
     */
    public double getTurnPlayedPerGamesAverage() {
        return this.turnPlayedPerGamesAverage;
    }

    /**
     * @return int, the number of game in_progress
     */
    public int getActiveGames() {
        return this.activeGames;
    }

    /**
     * @return int, the number of game abandonned
     */
    public int getAbandonedGames() {
        return this.abandonedGames;
    }
}
