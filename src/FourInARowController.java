import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FourInARowController extends Controller implements Initializable {

    @FXML
    public Label color;

    @FXML
    public ImageView avatar;

    @FXML
    public ImageView avatar1;

    @FXML
    public ImageView avatar11;

    @FXML
    public ImageView avatar111;

    @FXML
    private GridPane fourInARowGrid;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label pseudo;

    @FXML
    private Label senderPseudo;

    @FXML
    private Label ratio;

    @FXML
    private VBox friendList;

    @FXML
    private TextField textMessage;

    @FXML
    private AnchorPane messageZone;

    @FXML
    private VBox messageList;

    private Circle[][] grid;

    private FourInARow game;

    private ScheduledExecutorService scheduledExecutorService;

    private boolean gameEndShown = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.grid = new Circle[6][7];
    }

    private void roundAvatar(ImageView im, int radius) {
        Ellipse circle = new Ellipse();
        circle.setRadiusX(radius);
        circle.setRadiusY(radius);
        im.setClip(circle);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
    }


    public void initController(FourInARow currentGame) throws IOException, SQLException {
        gameEndShown = false;
        updateGrid(currentGame.getPlate());
        this.game = currentGame;
        Player enemy = currentGame.getPlayer1().equals(loggedPlayer) ?
                currentGame.getPlayer2() : currentGame.getPlayer1();

        this.scheduledExecutorService = Executors.newScheduledThreadPool(1); // A thread for each task running
        this.currentPlayerLabel.setText(String.format("%s, it's your turn to play !", loggedPlayer.getPseudo()));

        this.pseudo.setText(loggedPlayer.getPseudo());
        this.senderPseudo.setText(enemy.getPseudo());
        this.ratio.setText("Ratio: " + String.format("%.3g%n",databaseConnection.getPlayerStatistics(loggedPlayer).getRatio()));

        // loggedPlayer's avatar
        this.avatar.setImage(loggedPlayer.getPlayerAvatar());
        this.roundAvatar(this.avatar, 30);
        this.avatar111.setImage(loggedPlayer.getPlayerAvatar());
        this.roundAvatar(this.avatar111, 30);

        // Enemy's avatar
        this.avatar11.setImage(enemy.getPlayerAvatar());
        this.roundAvatar(this.avatar11, 30);
        this.avatar1.setImage(enemy.getPlayerAvatar());
        this.roundAvatar(this.avatar1, 25);

        // Team Color
        if (currentGame.getPlayer1().getPseudo().equals(loggedPlayer.getPseudo())) {
            this.color.setText("TEAM: RED");
            this.color.setStyle("-fx-background-color: #c50000; -fx-font-size:  14pt; -fx-text-fill: white; -fx-background-radius: 10px;");
        } else {
            this.color.setText("TEAM: YELLOW");
            this.color.setStyle("-fx-background-color: #f4ad05; -fx-font-size:  14pt; -fx-text-fill: white; -fx-background-radius: 10px;");
        }

        this.friendList.getChildren().clear();
        ArrayList<Player> friends = databaseConnection.getFriends(databaseConnection.getPlayer(pseudo.getText()));

        for(Player p : friends)
            Controller.addFriend(p, this.friendList, actionEvent -> System.out.println("Can't do that yet !"), actionEvent -> {
                try {
                    databaseConnection.createInv(loggedPlayer, p, true); // game invitation
                    showAlert("Invitation successfully sent",
                            String.format("Invitation sent to %s.", p.getPseudo()));
                } catch(SQLException exception) {
                    showAlert("Could'nt send invitation", "Check your internet connection and try again.");
                    exception.printStackTrace();
                }
            });

        try {
            loadMessage(enemy, messageList, messageZone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            showAlert("Something wrong just happened :(", "Couldn't load your opponent messages :(");
        }

        Runnable fetchMessages = () -> {
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
                } else
                    Platform.runLater(() -> senderPseudo.setText("Nobody"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable updateGrid = () -> {
            try {
                game.setPlate(databaseConnection.getFourInARowPlate(game));
                game.setCurrentPlayer(databaseConnection.getGameCurrentPlayer(game));

                game.checkWin();

                // This call back is only to update the javafx display since
                // it is not possible to access javafx components outside its thread
                Platform.runLater(() -> {
                    updateGrid(game.getPlate());
                    if(!gameEndShown) updateCurrentPlayerLabel();
                });
            } catch (SQLException | IOException exception) {
                exception.printStackTrace();
            }
        };

        this.scheduledExecutorService.scheduleAtFixedRate(fetchMessages, 500, 500, TimeUnit.MILLISECONDS);
        this.scheduledExecutorService.scheduleAtFixedRate(updateGrid, 500, 500, TimeUnit.MILLISECONDS);
    }


    public FourInARow getGame() {
        return game;
    }

    public void setGame(FourInARow game) {
        this.game = game;
    }

    @FXML
    public void onBackMenuAction() {
        awaitBackgroundTasksAndShutdown();
        messageList.getChildren().clear();
        messageZone.setVisible(false);
        sceneController.showScene(SceneController.ViewType.OngoingGames);
    }

    @FXML
    public void onSurrendAction() {
        Alert confirmAlert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmAlert.setContentText("Do you really want to give up ?\n" +
                "You can't undo that and the game will be lost.");

        confirmAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                try {
                    databaseConnection.updateGameStatus(game, Game.CANCELED);
                    awaitBackgroundTasksAndShutdown();
                    sceneController.showScene(SceneController.ViewType.OngoingGames);
                } catch (SQLException throwables) {
                    showAlert("Something went wrong :(", "Please check your internet connection and try again.");
                    throwables.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void onTextMessageKeyPressed(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            Player receiver = databaseConnection.getPlayer(senderPseudo.getText());
            databaseConnection.sendMessage(loggedPlayer, receiver, textMessage.getText());
            Controller.loadMessage(receiver, messageList, messageZone);
            textMessage.setText("");
        }
    }

    @FXML
    public void onQuitActionFourInARow() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
        awaitBackgroundTasksAndShutdown();
        Platform.exit();
    }

    @FXML
    public void onDisconnectActionFourInARow() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
        awaitBackgroundTasksAndShutdown();
        messageList.getChildren().clear();
        messageZone.setVisible(false);
        sceneController.showScene(SceneController.ViewType.Login);
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

    @FXML
    public void onColumn1Action() {
        doPlayerTurn(0);
    }

    @FXML
    public void onColumn2Action() {
        doPlayerTurn(1);
    }

    @FXML
    public void onColumn3Action() {
        doPlayerTurn(2);
    }

    @FXML
    public void onColumn4Action() {
        doPlayerTurn(3);
    }

    @FXML
    public void onColumn5Action() {
        doPlayerTurn(4);
    }

    @FXML
    public void onColumn6Action() {
        doPlayerTurn(5);
    }

    @FXML
    public void onColumn7Action() {
        doPlayerTurn(6);
    }

    private void doPlayerTurn(int column) {
        if (game.getCurrentPlayer().getPseudo().equals(loggedPlayer.getPseudo())){
            if (game.playerPlayTurn(column)) {
                updateGrid(game.getPlate());
                game.switchCurrentPlayer();
                game.checkWin();

                try {
                    databaseConnection.updateFourInARowPlate(game);

                    if (game.getWinner() != null) {
                        databaseConnection.updateGameStatus(game, Game.ENDED);
                        databaseConnection.setGameWinnerAndLooser(loggedPlayer, game.getCurrentPlayer(), game);
                        awaitBackgroundTasksAndShutdown();
                        sceneController.showScene(SceneController.ViewType.OngoingGames);
                    }

                    databaseConnection.updateCurrentGamePlayer(game);
                } catch (SQLException exception) {
                    showAlert("Something wrong happened", "Unable to update the database !");
                    exception.printStackTrace();
                }

                this.currentPlayerLabel.setText(String.format("Waiting %s to play ...", game.getCurrentPlayer().getPseudo()));
            } else
                showAlert("You can't play here", "Please choose another column ...");
        } else {
            showAlert("Please wait...", "Your enemy has not played yet");
        }
    }

    private void updateGrid(char[][] grid) {
        // Clear the grid
        this.fourInARowGrid.getChildren().clear();
        this.fourInARowGrid.setGridLinesVisible(true);

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != '*') {
                    Circle circle = new Circle(50.0);

                    if(grid[i][j] == 'R')
                        circle.setFill(Color.web("#c50000"));
                    else
                        circle.setFill(Color.web("#f4ad05"));

                    circle.setStroke(Color.BLACK);
                    circle.setStrokeType(StrokeType.INSIDE);

                    this.fourInARowGrid.add(circle, j, i);
                }
            }
        }
    }

    private void updateCurrentPlayerLabel() {
        if(loggedPlayer.equals(game.getCurrentPlayer()))
            this.currentPlayerLabel.setText(String.format("%s, it's your turn", loggedPlayer.getPseudo()));
        else
            this.currentPlayerLabel.setText(String.format("Waiting %s to play ...", game.getCurrentPlayer().getPseudo()));

        if(game.getWinner() != null) {
            if(game.getWinner().equals(loggedPlayer)) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Congratulation");
                al.setContentText("You won the game !!");
                al.show();
            }
            else
                showAlert("Better luck next time :(", "You loose :(\nKeep training you will get better !");

            gameEndShown = true;
            awaitBackgroundTasksAndShutdown();
            sceneController.showScene(SceneController.ViewType.OngoingGames);
        }
    }

    @FXML
    public void onInvitationsAction() throws IOException, SQLException {
        awaitBackgroundTasksAndShutdown();
        invitationController.initInvitationController();
        sceneController.showScene(SceneController.ViewType.Invitations);
    }
}
