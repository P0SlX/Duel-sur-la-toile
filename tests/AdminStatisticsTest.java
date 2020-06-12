import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminStatisticsTest {
    @Test
    void getGamesLaunchedPerHour() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertEquals(as.getGamesLaunchedPerHour(), 10.0);
    }

    @Test
    void getGetGamesLaunchedPerDay() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertEquals(as.getGetGamesLaunchedPerDay(), 20.0);
    }

    @Test
    void getGamesLaunchedPerMonth() {
        AdminStatistics as = new AdminStatistics(10, 20 ,30);
        assertEquals(as.getGamesLaunchedPerMonth(), 30.0);
    }
}