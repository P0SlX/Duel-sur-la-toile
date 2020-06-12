import com.gluonhq.charm.glisten.control.Avatar;
import javafx.application.Platform;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
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
    public void onDisconnectAction() {
        this.friendList.getChildren().clear();
        this.messageZone.setVisible(false);
        databaseConnection.setStatus(loggedPlayer, 0); // Set disconnected
        messageZone.setVisible(false);
        sceneController.showScene(SceneController.ViewType.Login);
    }

    @FXML
    public void onFourInARowAction() {
        this.ongoingGamesController.initOnGoingGameView();
        sceneController.showScene(SceneController.ViewType.OngoingGames);
    }

    @FXML
    public void onTextMessageKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
            Player receiver = databaseConnection.getPlayer(senderPseudo.getText());
            databaseConnection.sendMessage(loggedPlayer, receiver, textMessage.getText());
            loadMessage(receiver);
            textMessage.setText("");
        }
    }

    /**
     * Initialize the main View menu with the player specified in parameter
     * This method should be called after an user logged in by the LoginController
     * @param player the player that just logged in
     */
    public void initMainControllerWithPlayer(Player player) throws FileNotFoundException {
        this.loggedPlayer = player;

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
            addFriend(p);
    }

    public Player getLoggedPlayer() {
        return this.loggedPlayer;
    }

    private void addFriend(Player friend) throws FileNotFoundException {
        Button invite = new Button("Invite");
        Button message = new Button("Message");

        ImageView avatar = new ImageView(friend.getPlayerAvatar());
        Label friendPseudo = new Label(friend.getPseudo());
        Label friendRatio = new Label("Ratio : Unknown"); // TODO: When player statictics will be done

        // Design properties
        friendPseudo.setLayoutX(90.0);
        friendPseudo.setLayoutY(33.0);
        friendPseudo.setPrefHeight(21.0);
        friendPseudo.setPrefWidth(110);
        friendPseudo.setAlignment(Pos.CENTER_RIGHT);
        String PSEUDO = "-fx-font-size: 13pt; -fx-font-family: \"Segoe UI Light\";" +
                        "-fx-text-fill: white;" +
                        "-fx-opacity: 1;";
        friendPseudo.setStyle(PSEUDO);

        friendRatio.setAlignment(Pos.CENTER_RIGHT);
        friendRatio.setLayoutX(90.0);
        friendRatio.setLayoutY(19.0);
        friendRatio.setPrefWidth(110);
        String RATIO =  "-fx-font-size: 9pt;" +
                        "    -fx-font-family: \"Segoe UI Light\";" +
                        "    -fx-text-fill: rgb(200, 200, 200);" +
                        "    -fx-opacity: 1;";
        friendRatio.setStyle(RATIO);

        invite.setLayoutX(10.0);
        invite.setLayoutY(4.0);
        invite.setMnemonicParsing(false);
        invite.setPrefHeight(25.0);
        invite.setPrefWidth(75.0);
        invite.setStyle(BUTTON);

        message.setLayoutX(10.0);
        message.setLayoutY(38.0);
        message.setMnemonicParsing(false);
        message.setPrefHeight(25.0);
        message.setPrefWidth(75.0);
        message.setStyle(BUTTON);

        avatar.setLayoutX(206);
        avatar.setLayoutY(5.0);
        avatar.setFitHeight(60);
        avatar.setFitWidth(60);
        avatar.setSmooth(true);

        Ellipse circle = new Ellipse();
        circle.setRadiusX(30);
        circle.setRadiusY(30);
        avatar.setClip(circle);
        circle.setCenterX(30);
        circle.setCenterY(30);


        message.setOnAction(actionEvent -> loadMessage(friend));

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(avatar, friendPseudo, friendRatio, invite, message);

        friendList.getChildren().add(anchorPane);
        Separator separator = new Separator();
        separator.setOpacity(0.5);
        separator.setPrefHeight(20);
        separator.prefWidth(272.0);
        friendList.getChildren().add(separator);
    }

    private void loadMessage(Player sender) {
        ArrayList<Message> messages = databaseConnection.getPlayerMessage(this.loggedPlayer, sender);

        messageList.getChildren().clear();
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

            Label pseudo = new Label(m.getSenderPseudo());
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

    @FXML
    private void onQuitAction() {
        databaseConnection.setStatus(loggedPlayer, 0);
        Platform.exit();
    }

    public void setOngoingGamesController(OngoingGamesController ongoingGamesController) {
        this.ongoingGamesController = ongoingGamesController;
    }
}


