import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OngoingGamesController extends Controller implements Initializable {

    private final String PSEUDO = "-fx-font-size: 13pt; -fx-font-family: \"Segoe UI Light\";" +
            "-fx-text-fill: white;" +
            "-fx-opacity: 1;";

    private final String BUTTON = "-fx-background-color: #1E90FF; -fx-border-color: #1E90FF; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-text-fill: white; -fx-padding: 0;";

    private MainMenuController mainMenuController;

    @FXML
    private Label pseudo;

    @FXML
    private Label ratio;

    @FXML
    private VBox friendList;

    @FXML
    private ImageView avatar;

    @FXML
    public VBox activeGames;

    private FourInARowController fourInARowController;

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @FXML
    private void onBackHomeAction() throws IOException, SQLException {
        awaitBackgroundTasksAndShutdown();
        this.mainMenuController.initMainControllerWithPlayer(loggedPlayer);
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void initOnGoingGameView() throws IOException, SQLException {
        loggedPlayer = getLoggedPlayer();
        this.friendList.getChildren().clear();

        ArrayList<Player> friends = databaseConnection.getFriends(loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(p, this.friendList, actionEvent -> System.out.println("Can't do that yet !"),
                    actionEvent -> {
                        try {
                            databaseConnection.createInv(loggedPlayer, p, true); // game invitation
                            showAlert("Invitation successfully sent",
                                    String.format("Invitation sent to %s.", p.getPseudo()));
                        } catch(SQLException exception) {
                            showAlert("Couldnt send invitation", "Check your internet connection and try again.");
                            exception.printStackTrace();
                        }
                    });

        this.pseudo.setText(loggedPlayer.getPseudo());
        this.ratio.setText("Ratio: " + String.format("%.3g%n",databaseConnection.getPlayerStatistics(loggedPlayer).getRatio()));

        avatar.setImage(loggedPlayer.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(30);
        circle.setRadiusY(30);
        avatar.setClip(circle);
        circle.setCenterX(30);
        circle.setCenterY(30);

        this.activeGames.getChildren().clear();

        if(this.scheduledExecutorService.isShutdown())
            this.scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable scheduledTask = () -> {
            try {
                ArrayList<Game> games = databaseConnection.getActivesGames(loggedPlayer, GameType.FourInARow);
                Platform.runLater(() -> {
                    this.activeGames.getChildren().clear();
                    // In case the player has not active game show a message
                    if (games == null) {
                        HBox content = new HBox();
                        content.setAlignment(Pos.CENTER);

                        Label message = new Label("Nothing to show here :(\nStart a new game !");
                        message.setFont(new Font(20));
                        content.getChildren().add(message);

                        this.activeGames.getChildren().add(content);
                        return;
                    }

                    for (Game g : games) {
                        HBox gameContainer = new HBox();
                        gameContainer.setPrefHeight(50.0);
                        gameContainer.setPrefWidth(200.0);
                        gameContainer.setSpacing(50);

                        String opponentName = loggedPlayer.getPseudo().equals(g.getPlayer1().getPseudo()) ?
                                g.getPlayer2().getPseudo() : g.getPlayer1().getPseudo();

                        Label opponent = new Label("Against : " + opponentName);
                        opponent.setPrefHeight(40.0);
                        opponent.setPrefWidth(223.0);
                        opponent.setWrapText(true);
                        opponent.setFont(new Font(17));
                        opponent.setTextFill(Color.WHITE);
                        opponent.setAlignment(Pos.CENTER_LEFT);

                        Label startedOn = new Label("Started on : " + g.getStartTime());
                        startedOn.setPrefHeight(40.0);
                        startedOn.setPrefWidth(217.0);
                        startedOn.setFont(new Font(17));
                        startedOn.setTextFill(Color.WHITE);
                        startedOn.setAlignment(Pos.CENTER);

                        Label yourTurn = new Label();
                        if (g.getCurrentPlayer().getPseudo().equals(loggedPlayer.getPseudo())) {
                            yourTurn.setText("Your turn");
                            yourTurn.setTextFill(Color.web("#3cb929"));
                        } else {
                            yourTurn.setText("Waiting ...");
                            yourTurn.setTextFill(Color.web("#b8612a"));
                        }

                        yourTurn.setPrefHeight(40.0);
                        yourTurn.setPrefWidth(138.0);
                        yourTurn.setFont(new Font(20));
                        yourTurn.setAlignment(Pos.CENTER);

                        Button play = new Button("Play !");
                        // Temp
                        play.setOnAction(actionEvent -> {
                            FourInARow.setDatabaseConnection(databaseConnection);
                            try {
                                this.fourInARowController.initController((FourInARow) g);
                            } catch (IOException | SQLException e) {
                                e.printStackTrace();
                            }
                            sceneController.showScene(SceneController.ViewType.FourInARowGame);
                        });

                        play.setDisable(!g.getCurrentPlayer().getPseudo().equals(loggedPlayer.getPseudo()));
                        play.setMnemonicParsing(false);
                        play.setStyle("-fx-background-color: #3F7FBF; -fx-border-radius: 30px; -fx-cursor: hand;");
                        play.setTextFill(Color.WHITE);
                        play.setFont(new Font(21));
                        play.setPrefWidth(100.0);

                        Separator separator = new Separator();

                        gameContainer.getChildren().addAll(opponent, startedOn, yourTurn, play);
                        this.activeGames.getChildren().add(gameContainer);
                        this.activeGames.getChildren().add(separator);
                    }
                });
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        };
        this.scheduledExecutorService.scheduleAtFixedRate(scheduledTask, 0, 1, TimeUnit.SECONDS);
    }

    public FourInARowController getFourInARowController() {
        return fourInARowController;
    }

    public void setFourInARowController(FourInARowController fourInARowController) {
        this.fourInARowController = fourInARowController;
    }

    @FXML
    protected void onDisconnectAction() throws SQLException {
        this.awaitBackgroundTasksAndShutdown();
        databaseConnection.setStatus(loggedPlayer, 0); // Set disconnected
        sceneController.showScene(SceneController.ViewType.Login);
    }

    @FXML
    public void onInvitationsAction() throws IOException, SQLException {
        this.awaitBackgroundTasksAndShutdown();
        invitationController.initInvitationController();
        sceneController.showScene(SceneController.ViewType.Invitations);
    }

    /**
     * Wait half a second for the backgrounds tasks still running and destroy the thread pool.
     */
    private void awaitBackgroundTasksAndShutdown()  {
        try {
            this.scheduledExecutorService.awaitTermination(500, TimeUnit.MILLISECONDS);
            this.scheduledExecutorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
