import com.gluonhq.charm.glisten.control.Avatar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController extends Controller implements Initializable {

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
    private AnchorPane messageZone;

    private Player loggedPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onDisconnectAction() {
        databaseConnection.setStatus(loggedPlayer, 0); // Set disconnected
        sceneController.showScene(SceneController.ViewType.Login);
    }

    /**
     * Initialize the main View menu with the player specified in parameter
     * This method should be called after an user logged in by the LoginController
     * @param player the player that just logged in
     */
    public void initMainControllerWithPlayer(Player player) {
        this.loggedPlayer = player;

        this.pseudo.setText(player.getPseudo());
        this.ratio.setText("Ratio : Unknown"); // TODO: When player statistics will be done

        ArrayList<Player> friends = databaseConnection.getFriends(this.loggedPlayer);

        for(Player p : friends)
            addFriend(p);
    }

    private void addFriend(Player friend) {
        Button invite = new Button("Invite");
        Button message = new Button("Message");

        Avatar avatar = new Avatar(30.0);
        Label friendPseudo = new Label(friend.getPseudo());
        Label friendRatio = new Label("Ratio : Unknown"); // TODO: When player statictics will be done

        // Design properties
        friendPseudo.setAlignment(Pos.CENTER_RIGHT);
        friendPseudo.setLayoutX(102.0);
        friendPseudo.setLayoutY(48.0);
        friendPseudo.prefHeight(21.0);
        friendPseudo.prefWidth(109.0);
        String PSEUDO = "-fx-font-size: 13pt; -fx-font-family: \"Segoe UI Light\";" +
                "-fx-text-fill: white;" +
                "-fx-opacity: 1;";
        friendPseudo.setStyle(PSEUDO);

        friendRatio.setAlignment(Pos.CENTER_RIGHT);
        friendRatio.setLayoutX(102.0);
        friendRatio.setLayoutY(33.0);

        invite.setLayoutX(15.0);
        invite.setLayoutY(4.0);
        invite.setMnemonicParsing(false);
        invite.prefHeight(32.0);
        invite.prefWidth(71.0);
        String BUTTON = "-fx-background-radius: 5px;";
        invite.setStyle(BUTTON);

        message.setLayoutX(15.0);
        message.setLayoutY(35.0);
        message.setMnemonicParsing(false);
        message.prefHeight(32.0);
        message.prefWidth(71.0);
        message.setStyle(BUTTON);

        message.setOnAction(actionEvent -> loadMessage(friend));

        avatar.setLayoutX(206.0);
        avatar.setLayoutY(6.0);
        //avatar.setImage(new Image("@assets/logo.png"));

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(avatar, friendPseudo, friendRatio, invite, message);

        friendList.getChildren().add(anchorPane);
        Separator separator = new Separator();
        separator.setOpacity(0.5);
        separator.prefWidth(272.0);
        friendList.getChildren().add(separator);
    }

    private void loadMessage(Player sender) {
        ArrayList<Message> messages = databaseConnection.getPlayerMessage(this.loggedPlayer, sender);

        messageZone.setVisible(true);
        senderPseudo.setText(sender.getPseudo());

        for(Message m : messages) {
            HBox messageVBox = new HBox();
            messageVBox.prefWidth(250.0);

            VBox content = new VBox();
            content.setAlignment(Pos.CENTER);
            content.maxHeight(1.7976931348623157E308);
            content.maxWidth(1.7976931348623157E308);
            content.minHeight(Double.NEGATIVE_INFINITY);
            content.minWidth(Double.NEGATIVE_INFINITY);

            Label pseudo = new Label(sender.getPseudo());
            Label time = new Label(m.getDate());

            setMessageLabelStyle(pseudo);
            setMessageLabelStyle(time);
            content.getChildren().addAll(pseudo, time);
            messageVBox.getChildren().add(content);

            Label messageContent = new Label(m.getContent());
            setMessageLabelStyle(messageContent);
            messageContent.setPrefWidth(175.0);
            messageVBox.getChildren().add(messageContent);

            Separator separator = new Separator();
            separator.prefHeight(16.0);
            separator.prefWidth(250.0);
            separator.setStyle("-fx-opacity: 0.5;");

            this.messageList.getChildren().addAll(separator, messageVBox);
        }
    }

    private void setMessageLabelStyle(Label label) {
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(1.7976931348623157E308);
        label.setMaxWidth(1.7976931348623157E308);
        label.setMinHeight(Double.NEGATIVE_INFINITY);
        label.setMinWidth(Double.NEGATIVE_INFINITY);
        label.setPrefWidth(75.0);
        String LABEL_BRIGHT = "-fx-font-size: 11pt;" +
                "-fx-font-family: \"Segoe UI Light\";" +
                "-fx-text-fill: white;" +
                "-fx-opacity: 1;";
        label.setStyle(LABEL_BRIGHT);
    }
}


