import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class OngoingGamesController extends Controller implements Initializable {

    private MainMenuController mainMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onBackHomeAction() {
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }

    @FXML
    private void onDisconnectAction() {
        databaseConnection.setStatus(mainMenuController.getLoggedPlayer(), 0);
        sceneController.showScene(SceneController.ViewType.Login);
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}
