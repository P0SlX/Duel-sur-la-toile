import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onCancelAction() {
        sceneController.showScene(SceneController.ViewType.Login);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}
