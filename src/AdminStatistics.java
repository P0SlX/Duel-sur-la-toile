public class AdminStatistics {
    private final double gamesLaunchedPerHour;
    private final double getGamesLaunchedPerDay;
    private final double gamesLaunchedPerMonth;

    /**
     * Builder of adminStatistics
     * @param gamesLaunchedPerHour double, the number of games launched per hour by the application
     * @param getGamesLaunchedPerDay double, the number of games launched per day by the application
     * @param gamesLaunchedPerMonth double, the number of games launched per month by the application
     */
    public AdminStatistics(double gamesLaunchedPerHour, double getGamesLaunchedPerDay, double gamesLaunchedPerMonth) {
        this.gamesLaunchedPerHour = gamesLaunchedPerHour;
        this.getGamesLaunchedPerDay = getGamesLaunchedPerDay;
        this.gamesLaunchedPerMonth = gamesLaunchedPerMonth;
    }

    /**
     * @return double, the number of games launched per hour by the application
     */
    public double getGamesLaunchedPerHour() {
        return gamesLaunchedPerHour;
    }

    /**
     * @return double, the number of games launched per day by the application
     */
    public double getGetGamesLaunchedPerDay() {
        return getGamesLaunchedPerDay;
    }

    /**
     * @return double, the number of games launched per month by the application
     */
    public double getGamesLaunchedPerMonth() {
        return gamesLaunchedPerMonth;
    }
}
