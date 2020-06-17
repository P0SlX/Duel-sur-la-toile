import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private GridPane fourInARowGrid;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label pseudo;

    @FXML
    private Label senderPseudo;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.grid = new Circle[6][7];
    }

    public void initController(FourInARow currentGame) throws IOException, SQLException {
        updateGrid(currentGame.getPlate());
        this.game = currentGame;
        Player enemy = currentGame.getPlayer1().equals(loggedPlayer) ?
                currentGame.getPlayer2() : currentGame.getPlayer1();

        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        this.currentPlayerLabel.setText(String.format("%s, it's your turn to play !", loggedPlayer.getPseudo()));

        pseudo.setText(loggedPlayer.getPseudo());
        // TODO: Ratio

        senderPseudo.setText(enemy.getPseudo());

        ArrayList<Player> friends = databaseConnection.getFriends(databaseConnection.getPlayer(pseudo.getText()));

        for(Player p : friends)
            Controller.addFriend(p, this.friendList, actionEvent -> System.out.println("Can't do that yet !"), actionEvent -> System.out.println("Not implemened here"));

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

        this.scheduledExecutorService.scheduleAtFixedRate(fetchMessages, 500, 500, TimeUnit.MILLISECONDS);

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
     * Wait 500 seconds for the backgrounds task still running and destroy the thread pool.
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
        game.playerPlayTurn(column);
        updateGrid(game.getPlate());
        game.switchCurrentPlayer();
        game.checkWin();

        try {
            databaseConnection.updateFourInARowPlate(game);

            if(game.getWinner() != null) {
                showAlert("Congratulation", "You won the game !!");
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
}
