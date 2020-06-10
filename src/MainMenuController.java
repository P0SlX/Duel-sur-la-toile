import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Controller implements Initializable {

    @FXML
    public MenuItem disconnect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onDisconnectAction() {
        // TODO: Set player status as disconnected

        sceneController.showScene(SceneController.ViewType.Login);
    }
}
