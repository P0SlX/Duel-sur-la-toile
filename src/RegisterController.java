import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button cancel;

    @FXML
    private Button signUp;

    @FXML
    private TextField pseudo;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    private void onCancelAction() {
        sceneController.showScene(SceneController.ViewType.Login);
    }

    @FXML
    private void onSignUpAction() throws IOException, SQLException {
        if (this.password.getText().equals(this.confirmPassword.getText())) {       // if password matches

            if (!this.pseudo.getText().equals("") && !this.email.getText().equals("") &&
                    !this.password.getText().equals("") && !this.confirmPassword.getText().equals("")) { // if a box is empty

                if (databaseConnection.getPlayer(this.pseudo.getText()) == null) {      // if password already used

                    for (Player p : databaseConnection.getAllPlayers()) {               // check if email already used
                        if (p.getEmail().equals(this.email.getText())){
                            this.showAlert("Email adress already used.\nPlease try again.");
                            return;
                        }
                    }

                    // ******* NEW USER ******* //
                    Player newPlayer = new Player(
                            this.pseudo.getText(),
                            this.email.getText(),
                            this.password.getText(),
                            "img/avatarDefault.png",
                            Player.DISCONNECTED,
                            false,
                            false
                            );
                    try {
                        databaseConnection.createPlayer(newPlayer);
                        sceneController.showScene(SceneController.ViewType.Login);
                        // USER REGISTERED !!
                    } catch (FileNotFoundException | SQLException e) {
                        e.printStackTrace();
                        this.showAlert("Failed to register a new user.\nError code : -1");
                    }
                } else {
                    this.showAlert("Pseudo already used.\nPlease try again.");
                }
            } else {
                this.showAlert("Please fill all the boxes before trying to register.");
            }

        } else {
            this.showAlert("Passwords does not match.\nPlease try again.");
        }
    }

    private void showAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration error");
        alert.setHeaderText("Oops, an error just occurred !");
        alert.setContentText(error);
        alert.show();
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}
