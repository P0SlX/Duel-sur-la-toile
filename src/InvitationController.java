import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InvitationController extends Controller implements Initializable {

    @FXML
    private Label pseudo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onDisconnectAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
    }

    @FXML
    public void onBackHomeAction() {
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }

    public void initInvitationController() {

    }
}
