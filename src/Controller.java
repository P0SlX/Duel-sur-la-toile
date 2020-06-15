import com.gluonhq.charm.glisten.control.TextField;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Controller {

    public static final String PSEUDO = "-fx-font-size: 13pt; -fx-font-family: \"Segoe UI Light\";" +
            "-fx-text-fill: white;" +
            "-fx-opacity: 1;";

    public static final String BUTTON = "-fx-background-color: #1e90ff; -fx-border-color: #1E90FF; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-text-fill: white; -fx-padding: 0;";

    @FXML
    private static MenuItem disconnect;

    @FXML
    private static Label pseudo;

    /**
     * Represent the right friend list
     */
    @FXML
    private static VBox friendList;

    @FXML
    private static Label ratio;

    @FXML
    private static VBox messageList;

    @FXML
    private static Label senderPseudo;

    @FXML
    private static ImageView avatar;

    @FXML
    private static AnchorPane messageZone;

    @FXML
    private static Button fourInARow;

    @FXML
    private static TextField textMessage;

    @FXML
    private static VBox activeGames;

    private static Player loggedPlayer;

    protected static DatabaseConnection databaseConnection;


    protected SceneController sceneController;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setDatabaseConnection(DatabaseConnection database) {
        this.databaseConnection = database;
    }

    public static void addFriend(Player friend, VBox friendList) {
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

        message.setOnAction(actionEvent -> {
            try {
                loadMessage(friend);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(avatar, friendPseudo, friendRatio, invite, message);

        friendList.getChildren().add(anchorPane);
        Separator separator = new Separator();
        separator.setOpacity(0.5);
        separator.setPrefHeight(20);
        separator.prefWidth(272.0);
        friendList.getChildren().add(separator);
    }

    static void loadMessage(Player sender) throws SQLException {
        ArrayList<Message> messages = databaseConnection.getPlayerMessage(loggedPlayer, sender);
        messageList.getChildren().clear();
        messageZone.setVisible(true);
        senderPseudo.setText(sender.getPseudo());

        for(Message m : messages) {
            if (! m.getContent().isBlank()) {
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

                messageList.getChildren().addAll(separator, messageVBox);
            }
        }
    }

    private static void setMessageLabelStyle(Label label) {
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
    public void onDisconnectAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, 0); // Set disconnected
        sceneController.showScene(SceneController.ViewType.Login);
    }

    protected static void setLoggedPlayer(Player p) {
        loggedPlayer = p;
    }

    protected static Player getLoggedPlayer() {
        return loggedPlayer;
    }

    public static MenuItem getDisconnect() {
        return disconnect;
    }

    public static void setDisconnect(MenuItem disconnect) {
        Controller.disconnect = disconnect;
    }

    public static Label getPseudo() {
        return pseudo;
    }

    public static void setPseudo(Label pseudo) {
        Controller.pseudo = pseudo;
    }

    public static VBox getFriendList() {
        return friendList;
    }

    public static void setFriendList(VBox friendList) {
        Controller.friendList = friendList;
    }

    public static Label getRatio() {
        return ratio;
    }

    public static void setRatio(Label ratio) {
        Controller.ratio = ratio;
    }

    public static VBox getMessageList() {
        return messageList;
    }

    public static void setMessageList(VBox messageList) {
        Controller.messageList = messageList;
    }

    public static Label getSenderPseudo() {
        return senderPseudo;
    }

    public static void setSenderPseudo(Label senderPseudo) {
        Controller.senderPseudo = senderPseudo;
    }

    public static ImageView getAvatar() {
        return avatar;
    }

    public static void setAvatar(ImageView avatar) {
        Controller.avatar = avatar;
    }

    public static AnchorPane getMessageZone() {
        return messageZone;
    }

    public static void setMessageZone(AnchorPane messageZone) {
        Controller.messageZone = messageZone;
    }

    public static TextField getTextMessage() {
        return textMessage;
    }

    public static void setTextMessage(TextField textMessage) {
        Controller.textMessage = textMessage;
    }

    public static VBox getActiveGames() {
        return activeGames;
    }

    public static void setActiveGames(VBox activeGames) {
        Controller.activeGames = activeGames;
    }
}
