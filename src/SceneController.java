import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {

    public enum ViewType {
        Login, Register, MainMenu, PlayerAccount,
        FourInARowGame, OngoingGames, Invitations, AdminPanel
    }

    private final HashMap<ViewType, Pane> views;
    private final Scene mainScene;

    public SceneController(Scene mainScene) {
        this.views = new HashMap<>();
        this.mainScene = mainScene;
    }

    public void addScene(ViewType type, Pane scene) {
        /**
         * Add a scene and it type in this.views
         * @param type ViewType, the view type...
         * @param scene Pane, the new scene
         */
        this.views.put(type, scene);
    }

    public void showScene(ViewType type) {
        /**
         * Show a scene on the mainScene (contained in this.views)
         * @param type ViewType, the type of the scene
         */
        this.mainScene.setRoot(this.views.get(type));
    }

    public Pane getScene(ViewType type) {
        /**
         * get a scene in this.views
         * @param type ViewType, the type of the scene
         * @return Pane, the seeked scene
         */
        return this.views.get(type);
    }

    public HashMap<ViewType, Pane> getViews() {
        /**
         * get all the scenes
         * @return HashMap<ViewType, Pane>, simply this.views
         */
        return this.views;
    }
}
