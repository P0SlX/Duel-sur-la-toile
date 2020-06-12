import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;

class MessageTest {

    @Test
    void getContent() {
        Message msg = new Message("rend mon quad", "2020-25-65");
        assertEquals(msg.getContent(),"rend mon quad");
        assertNotEquals(msg.getContent(),"rend mon scooter");


    }

    @Test
    void getDate() {
        Message msg = new Message("rend mon quad", "2020-25-65");
        assertEquals(msg.getDate(), "2020-25-65");
        assertNotEquals(msg.getDate(), "2019-25-65");
    }
}