import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {

    public enum ViewType {
        Login, Register, MainMenu, PlayerAccount, FourInARowGame, OngoingGames
    };

    private HashMap<ViewType, Pane> views;
    private Scene mainScene;

    public SceneController(Scene mainScene) {
        this.views = new HashMap<>();
        this.mainScene = mainScene;
    }

    public void addScene(ViewType type, Pane scene) {
        views.put(type, scene);
    }

    public void showScene(ViewType type) {
        mainScene.setRoot(views.get(type));
    }

    public Pane getScene(ViewType type) {
        return views.get(type);
    }

    public HashMap<ViewType, Pane> getViews() {
        return views;
    }
}
