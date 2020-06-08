public class AdminStatistics {
    private double gamesLaunchedPerHour;
    private double getGamesLaunchedPerDay;
    private double gamesLaunchedPerMonth;

    public AdminStatistics(double gamesLaunchedPerHour, double getGamesLaunchedPerDay, double gamesLaunchedPerMonth) {
        this.gamesLaunchedPerHour = gamesLaunchedPerHour;
        this.getGamesLaunchedPerDay = getGamesLaunchedPerDay;
        this.gamesLaunchedPerMonth = gamesLaunchedPerMonth;
    }

    public double getGamesLaunchedPerHour() {
        return gamesLaunchedPerHour;
    }

    public double getGetGamesLaunchedPerDay() {
        return getGamesLaunchedPerDay;
    }

    public double getGamesLaunchedPerMonth() {
        return gamesLaunchedPerMonth;
    }
}
