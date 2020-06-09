import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Button connect;

    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onSignInAction() {
        System.out.println("Hello depuis le bouton login !!");
        connect.setText("Le bouton");
    }

    @FXML
    private void onSignUpAction() {
        sceneController.showScene(SceneController.ViewType.Register);
    }

    public void setSceneController(SceneController sceneController) {
        System.out.println("Couocu");
        this.sceneController = sceneController;
    }
}
