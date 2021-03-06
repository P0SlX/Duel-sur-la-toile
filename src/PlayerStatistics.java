public class PlayerStatistics{

    private final double ratio;
    private final int victories;
    private final int defeats;
    private final int activeGames;
    private final int consecutiveWins;
    private final int playedGames;
    private final int abandonedGames;

    /** Builder of PlayerStatistics
     * @param victories int, number of games won by a player
     * @param defeats int, number of games lose by a player
     * @param activeGames int, number of games in_progress
     * @param consecutiveWins int, number of consecutive win by a player
     * @param abandonedGames int, number of games abandoned
     */
    public PlayerStatistics(int victories, int defeats, int activeGames, int consecutiveWins, int abandonedGames) {
        if (defeats == 0) {
            this.ratio = victories;
        } else {
            this.ratio = (double)victories/(double)defeats;
        }
        this.victories = victories;
        this.defeats = defeats;
        this.activeGames = activeGames;
        this.consecutiveWins = consecutiveWins;
        this.playedGames = victories + defeats + abandonedGames;
        this.abandonedGames = abandonedGames;
    }

    /**
     * @return double, ratio between wins and defeat
     */
    public double getRatio() {
        return this.ratio;
    }

    /**
     * @return int, number of games lose by a player
     */
    public int getDefeats() {
        return this.defeats;
    }

    /**
     * @return int, number of consecutive win by a player
     */
    public int getConsecutiveWins() {
        return this.consecutiveWins;
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
    public int getVictories() {
        return this.victories;
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
