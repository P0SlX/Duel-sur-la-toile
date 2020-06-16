import com.gluonhq.charm.glisten.control.TextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private OngoingGamesController ongoingGamesController;

    private InvitationController invitationController;

    private ScheduledExecutorService scheduledExecutorService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onFourInARowAction() throws IOException, SQLException {
        this.ongoingGamesController.initOnGoingGameView();
        messageList.getChildren().clear();
        messageZone.setVisible(false);
        sceneController.showScene(SceneController.ViewType.OngoingGames);
    }

    @FXML
    public void onTextMessageKeyPressed(KeyEvent event) throws SQLException, IOException {
        if(event.getCode().equals(KeyCode.ENTER)) {
            Player receiver = databaseConnection.getPlayer(senderPseudo.getText());
            databaseConnection.sendMessage(loggedPlayer, receiver, textMessage.getText());
            Controller.loadMessage(receiver, messageList, messageZone);
            textMessage.setText("");
        }
    }

    /**
     * Initialize the main View menu with the player specified in parameter
     * This method should be called after an user logged in by the LoginController
     * @param player the player that just logged in
     */
    public void initMainControllerWithPlayer(Player player) throws IOException, SQLException {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        this.friendList.getChildren().clear();
        this.messageZone.setVisible(false);

        loggedPlayer = Controller.getLoggedPlayer();
        this.pseudo.setText(player.getPseudo());
        this.ratio.setText("Ratio : 9000"); // TODO: When player statistics will be done

        avatar.setImage(loggedPlayer.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(30);
        circle.setRadiusY(30);
        avatar.setClip(circle);
        circle.setCenterX(30);
        circle.setCenterY(30);

        ArrayList<Player> friends = databaseConnection.getFriends(loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(
                    p, friendList, actionEvent -> {
                    messageZone.setVisible(true);

                    senderPseudo.setText(p.getPseudo());
                    try {
                        loadMessage(loggedPlayer, messageList, messageZone);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        showAlert("Something wrong just happened !", "Unable to fetch message from database !");
                    }
                },
                actionEvent -> {
                    awaitBackgroundTasksAndShutdown();
                    invitationController.initInvitationController();
                    sceneController.showScene(SceneController.ViewType.Invitations);
            });

        Runnable scheduledTask = () -> {
            try {
                Player friend = databaseConnection.getPlayer(senderPseudo.getText());
                if (friend != null) {
                    Platform.runLater(() -> {
                        try {
                            loadMessage(friend, this.messageList, this.messageZone);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        senderPseudo.setText("Nobody");
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        this.scheduledExecutorService.scheduleAtFixedRate(scheduledTask, 500, 500, TimeUnit.MILLISECONDS);
    }

    public void setOngoingGamesController(OngoingGamesController ongoingGamesController) {
        this.ongoingGamesController = ongoingGamesController;
    }

    @FXML
    protected void onDisconnectAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, 0); // Set disconnected
        this.awaitBackgroundTasksAndShutdown();
        messageZone.setVisible(false);
        messageList.getChildren().clear();
        sceneController.showScene(SceneController.ViewType.Login);
    }

    @FXML
    protected void onQuitAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, 0);
        this.awaitBackgroundTasksAndShutdown();
        Platform.exit();
    }

    /**
     * Wait 2 seconds for the backgrounds task still running and destroy the thread pool.
     */
    private void awaitBackgroundTasksAndShutdown()  {
        try {
            this.scheduledExecutorService.awaitTermination(500, TimeUnit.MILLISECONDS);
            this.scheduledExecutorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onPlayerAccountAction() {
        awaitBackgroundTasksAndShutdown();
        sceneController.showScene(SceneController.ViewType.PlayerAccount);
    }

    public void setInvitationController(InvitationController invitationController) {
        this.invitationController = invitationController;
    }
}


