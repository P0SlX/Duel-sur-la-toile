import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminStatisticsTest {
    @test
    void getGamesLaunchedPerHour() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertEquals(as.getGamesLaunchedPerHour(), 10.0);
    }

    @test
    void getGetGamesLaunchedPerDay() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertEquals(as.getGetGamesLaunchedPerDay(), 20.0);
    }

    @test
    void getGamesLaunchedPerMonth() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertEquals(as.getGamesLaunchedPerMonth(), 30.0);
    }
}