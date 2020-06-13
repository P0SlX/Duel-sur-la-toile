import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Button connect;

    @FXML
    private TextField pseudo;

    @FXML
    private TextField password;

    private MainMenuController mainMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    private void onSignInAction() throws FileNotFoundException {
        String pseudo = this.pseudo.getText();
        String password = this.password.getText();

        if(databaseConnection.connectPlayer(pseudo, password)) {
            Player p = databaseConnection.getPlayer(pseudo);
            databaseConnection.setStatus(p, 1);
            this.mainMenuController.initMainControllerWithPlayer(p); // Init main menu view
            sceneController.showScene(SceneController.ViewType.MainMenu);

            // If the player log out later, it is preferable to not show password again
            this.password.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setHeaderText("Oops, an error just occurred !");
            alert.setContentText("Incorrect username or password.\nPlease try again.");
            alert.show();
        }
    }

    @FXML
    private void onSignUpAction() {
        sceneController.showScene(SceneController.ViewType.Register);
    }

    @FXML
    private void onQuitAction() {
        Platform.exit();
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;

        if(mainMenuController == null) throw new IllegalStateException();
    }

    public void setPseudo(String pseudo) {
        this.pseudo.setText(pseudo);
    }
}
