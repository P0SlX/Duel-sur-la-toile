class AdminStatisticsTest {
    @test
    void getGamesLaunchedPerHour() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertTrue(as.getGamesLaunchedPerHour(), 10.0);
    }

    @test
    void getGetGamesLaunchedPerDay() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertTrue(as.getGetGamesLaunchedPerDay(), 20.0);
    }

    @test
    void getGamesLaunchedPerMonth() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertTrue(as.getGamesLaunchedPerMonth(), 30.0);
    }
}