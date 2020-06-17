import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FourInARowTest {

    private final char[][] WIN1 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', 'R', '*', '*', '*' },
            { '*', '*', '*', 'R', '*', '*', '*' },
            { '*', '*', '*', 'R', '*', '*', '*' },
            { '*', '*', '*', 'R', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
    };

    private final char[][] WIN2 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { 'B', 'B', 'B', 'B', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
    };

    private final char[][] WIN3 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', 'R', '*', '*', '*', '*', '*' },
            { '*', '*', 'R', '*', '*', '*', '*' },
            { '*', '*', '*', 'R', '*', '*', '*' },
            { '*', '*', '*', '*', 'R', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
    };

    private final char[][] WIN4 = {
            { '*', '*', '*', '*', '*', '*', 'B' },
            { '*', '*', '*', '*', '*', 'B', '*' },
            { '*', '*', '*', '*', 'B', '*', '*' },
            { '*', '*', '*', 'B', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
    };

    private final char[][] LOOSE1 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
    };

    private final char[][] LOOSE2 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', 'B', '*' },
            { '*', '*', 'R', 'R', 'R', 'B', '*' },
            { '*', '*', '*', '*', '*', 'B', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', 'B', '*' },
    };

    private final char[][] LOOSE3 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', 'B', '*', '*', '*', '*' },
            { '*', '*', '*', 'B', '*', '*', '*' },
            { '*', '*', 'R', '*', 'B', '*', '*' },
            { '*', '*', 'R', '*', '*', 'R', '*' },
            { '*', 'R', 'R', 'R', '*', '*', '*' },
    };

    private final char[][] TURN1 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { 'R', '*', '*', '*', '*', '*', '*' },
    };

    private final char[][] TURN2 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { 'R', '*', 'R', '*', '*', '*', '*' },
    };

    private final char[][] TURN3 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', 'R', '*', '*', '*', '*' },
            { 'R', '*', 'R', '*', '*', '*', '*' },
    };

    private final char[][] TURN4 = {
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', '*', '*', '*', '*', '*' },
            { '*', '*', 'R', '*', '*', '*', '*' },
            { '*', '*', 'R', '*', '*', '*', '*' },
            { 'R', '*', 'R', '*', '*', '*', '*' },
    };

    /**
     * Unit test for the four in a row WIN
     */
    @Test
    void checkWin() {
        assertTrue(new FourInARow(WIN1).checkWin());
        assertTrue(new FourInARow(WIN2).checkWin());
        assertTrue(new FourInARow(WIN3).checkWin());
        assertTrue(new FourInARow(WIN3).checkWin());
        assertTrue(new FourInARow(WIN4).checkWin());

        assertFalse(new FourInARow(LOOSE1).checkWin());
        assertFalse(new FourInARow(LOOSE2).checkWin());
        assertFalse(new FourInARow(LOOSE3).checkWin());
    }

    @Test
    void playerPlayTurn() {
        FourInARow fourInARow = new FourInARow(LOOSE1);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connexion();
        Player p;

        try {
            p = databaseConnection.getPlayer("Coco");
            assertNotNull(p);

            fourInARow.playerPlayTurn(0);

            char[][] gameContent = fourInARow.getPlate();

            assertArrayEquals(TURN1, gameContent);

            fourInARow.playerPlayTurn(2);
            gameContent = fourInARow.getPlate();
            assertArrayEquals(TURN2, gameContent);

            fourInARow.playerPlayTurn(2);
            gameContent = fourInARow.getPlate();
            assertArrayEquals(TURN3, gameContent);

            fourInARow.playerPlayTurn(2);
            gameContent = fourInARow.getPlate();
            assertArrayEquals(TURN4, gameContent);

        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
            fail();
        }
    }
}