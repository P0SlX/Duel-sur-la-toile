import com.gluonhq.charm.glisten.control.TextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController extends Controller implements Initializable {

    private final String PSEUDO = "-fx-font-size: 13pt; -fx-font-family: \"Segoe UI Light\";" +
                                  "-fx-text-fill: white;" +
                                  "-fx-opacity: 1;";

    private final String BUTTON = "-fx-background-color: #1E90FF; -fx-border-color: #1E90FF; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-text-fill: white; -fx-padding: 0;";
    @FXML
    private MenuItem disconnect;

    @FXML
    private Label pseudo;

    /**
     * Represent the left friend list bar
     */
    @FXML
    private VBox friendList;

    @FXML
    private Label ratio;

    @FXML
    private VBox messageList;

    @FXML
    private Label senderPseudo;

    @FXML
    private ImageView avatar;

    @FXML
    private AnchorPane messageZone;

    @FXML
    private Button fourInARow;

    @FXML
    private TextField textMessage;

    private Player loggedPlayer;

    private OngoingGamesController ongoingGamesController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    @FXML
    public void onFourInARowAction() throws IOException, SQLException {
        this.ongoingGamesController.initOnGoingGameView();
        sceneController.showScene(SceneController.ViewType.OngoingGames);
    }

    @FXML
    public void onTextMessageKeyPressed(KeyEvent event) throws SQLException, IOException {
        if(event.getCode().equals(KeyCode.ENTER)) {
            Player receiver = databaseConnection.getPlayer(senderPseudo.getText());
            databaseConnection.sendMessage(loggedPlayer, receiver, textMessage.getText());
            Controller.loadMessage(receiver);
            textMessage.setText("");
        }
    }

    /**
     * Initialize the main View menu with the player specified in parameter
     * This method should be called after an user logged in by the LoginController
     * @param player the player that just logged in
     */
    public void initMainControllerWithPlayer(Player player) throws IOException, SQLException {
        this.friendList.getChildren().clear();
        this.messageZone.setVisible(false);

        this.loggedPlayer = Controller.getLoggedPlayer();
        this.pseudo.setText(player.getPseudo());
        this.ratio.setText("Ratio : 9000"); // TODO: When player statistics will be done

        avatar.setImage(this.loggedPlayer.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(30);
        circle.setRadiusY(30);
        avatar.setClip(circle);
        circle.setCenterX(30);
        circle.setCenterY(30);

        ArrayList<Player> friends = databaseConnection.getFriends(this.loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(p, this.friendList);
    }


    @FXML
    private void onQuitAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, 0);
        Platform.exit();
    }

    public void setOngoingGamesController(OngoingGamesController ongoingGamesController) {
        this.ongoingGamesController = ongoingGamesController;
    }

    public VBox getVBoxFriendsList() {
        return this.friendList;
    }
}


