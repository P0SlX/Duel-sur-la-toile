import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Button connect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onSignInAction() {
        // TODO
    }

    @FXML
    private void onSignUpAction() {
        sceneController.showScene(SceneController.ViewType.Register);
    }

}
