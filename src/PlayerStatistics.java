public class PlayerStatistics {

    private int playedGames;
    private int wonGames;
    private double turnPlayedPerGamesAverage;
    private int activeGames;
    private int abandonedGames;

    /**
     * builder of PlayerStatistic
     * @param playedGames int, the number of games played by a player
     * @param wonGames int, the number of games won by a player
     * @param turnPlayedPerGamesAverage, double, the average number of turns played by game
     * @param activeGames int, number of games in_progress
     * @param abandonedGames int, number of games abandoned
     */
    public PlayerStatistics(int playedGames, int wonGames, double turnPlayedPerGamesAverage, int activeGames, int abandonedGames){
        this.playedGames=playedGames;
        this.wonGames=wonGames;
        this.turnPlayedPerGamesAverage=turnPlayedPerGamesAverage;
        this.activeGames=activeGames;
        this.abandonedGames=abandonedGames;

    }

    /**
     * @return int, the number of games played
     */
    public int getPlayedGames() {
        return this.playedGames;
    }

    /**
     * @return int, the number of games won
     */
    public int getWonGames() {
        return this.wonGames;
    }

    /**
     * @return double, the average number of turns per game
     */
    public double getTurnPlayedPerGamesAverage() {
        return this.turnPlayedPerGamesAverage;
    }

    /**
     * @return int, the number of games in_progress
     */
    public int getActiveGames() {
        return this.activeGames;
    }

    /**
     * @return int, the number of games abandonned
     */
    public int getAbandonedGames() {
        return this.abandonedGames;
    }
}
