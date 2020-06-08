import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SceneControllerTest {

    @Test
    void addScene() {
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();

        SceneController sceneController = new SceneController(new Scene(new Pane()));
        sceneController.addScene(SceneController.ViewType.Login, pane1);
        sceneController.addScene(SceneController.ViewType.Register, pane2);

        var view = sceneController.getViews();

        assertTrue(view.containsKey(SceneController.ViewType.Login));
        assertTrue(view.containsKey(SceneController.ViewType.Register));
        assertFalse(view.containsKey(SceneController.ViewType.FourInARowGame));
    }

}