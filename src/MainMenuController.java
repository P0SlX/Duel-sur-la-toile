import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Controller implements Initializable {

    @FXML
    private MenuItem disconnect;

    private Player loggedPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onDisconnectAction() {
        // TODO: Set player status as disconnected

        sceneController.showScene(SceneController.ViewType.Login);
    }

    /**
     * Initialize the main View menu with the player specified in parameter
     * This method should be called after an user logged in by the LoginController
     * @param player the player that just logged in
     */
    public void initMainControllerWithPlayer(Player player) {
        this.loggedPlayer = player;

        // TODO: Modify all components according to the player
    }

}
