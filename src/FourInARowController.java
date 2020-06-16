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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    private FourInARowButton[][] grid;

    private FourInARow game;

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.grid = new FourInARowButton[7][7];

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                grid[i][j] = new FourInARowButton(i, j);
                fourInARowGrid.add(grid[i][j], j, i);

                grid[i][j].setOnAction(event -> {
                    FourInARowButton fourInARowButton = (FourInARowButton)event.getSource();
                    try {
                        if(game.playerPlayTurn(loggedPlayer,
                                fourInARowButton.getCoords().getFirst(), fourInARowButton.getCoords().getSecond())) {
                            databaseConnection.updateFourInARowPlate(game);

                            if(game.getPlayer1().equals(loggedPlayer)) {
                                fourInARowButton.setText("R");
                                game.setCurrentPlayer(game.getPlayer2());
                            } else {
                                fourInARowButton.setText("B");
                                game.setCurrentPlayer(game.getPlayer1());
                            }

                            updateCurrentPlayerLabelAndDB();

                            if(game.checkWin()) {
                                if(game.getWinner().equals(loggedPlayer))
                                    showAlert("You won the game !", "You're the winner congratulation !");
                                else
                                    showAlert("You loose the game !", "You lose the game, better luck next time !");

                                databaseConnection.updateGameStatus(game, Game.ENDED);

                                sceneController.showScene(SceneController.ViewType.OngoingGames);
                            }
                        } else
                            showAlert("You can't do that", "This case isn't empty !\nPlease play somewhere else !");
                    } catch (SQLException throwables) {
                        showAlert("Something went wrong with the database :(", "Check your internet connection and try again");
                        throwables.printStackTrace();
                    }
                });
            }
        }
    }

    public void initController(FourInARow currentGame) {
        char[][] content = currentGame.getPlate();
        this.game = currentGame;
        Player enemy = currentGame.getPlayer1().equals(loggedPlayer) ?
                currentGame.getPlayer2() : currentGame.getPlayer1();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);

        pseudo.setText(loggedPlayer.getPseudo());
        // TODO: Ratio

        senderPseudo.setText(enemy.getPseudo());

        try {
            loadMessage(enemy, messageList, messageZone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            showAlert("Something wrong just happened :(", "Couldn't load your opponent messages :(");
        }

        updateCurrentPlayerLabelAndDB();

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++)
                setButton(i, j, content[i][j]);
        }

        Runnable scheduledTask = () -> {
            try {
                game.setPlate(databaseConnection.getFourInARowPlate(game));
                char[][] updatedContent = game.getPlate();

                // We can't update javafx stuff outside its thread
                // we have to ask javafx to do it
                Platform.runLater(() -> {
                    for(int i = 0; i < 7; i++) {
                        for(int j = 0; j < 7; j++)
                            setButton(i, j, updatedContent[i][j]);
                    }
                });

                System.out.println("Updated game grid");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        };

        this.scheduledExecutorService.scheduleAtFixedRate(scheduledTask, 1, 1, TimeUnit.SECONDS);
    }

    private void setButton(int i, int j, char type) {
        // TODO: Colored buttons and proper style
        switch (type) {
            case 'B' -> grid[i][j].setText("B");
            case 'R' -> grid[i][j].setText("R");
            default -> grid[i][j].setText("*");
        }
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
        sceneController.showScene(SceneController.ViewType.OngoingGames);
    }

    @FXML
    public void onSurrendAction() {
        Alert confirmAlert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmAlert.setContentText("Do you really to surrend this game ?\n" +
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
    public void onPlayerProfilAction(ActionEvent actionEvent) {
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

    public void onQuitActionFourInARow() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
        awaitBackgroundTasksAndShutdown();
        Platform.exit();
    }

    public void onDisconnectActionFourInARow() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
        awaitBackgroundTasksAndShutdown();
        sceneController.showScene(SceneController.ViewType.Login);
    }

    /**
     * Wait 2 seconds for the backgrounds task still running and destroy the thread pool.
     * @throws InterruptedException in case something was still running when it stops to wait
     */
    private void awaitBackgroundTasksAndShutdown()  {
        try {
            this.scheduledExecutorService.awaitTermination(2, TimeUnit.SECONDS); // 2 seconds
            this.scheduledExecutorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentPlayerLabelAndDB() {
        if(loggedPlayer.equals(game.getCurrentPlayer())) {
            currentPlayerLabel.setText(String.format("%s, it's your turn to play", loggedPlayer.getPseudo()));
            for(int i = 0; i < 7; i++) {
                for(int j = 0; j < 7; j++)
                    grid[i][j].setVisible(true);
            }
        } else {
            currentPlayerLabel.setText(String.format("Waiting %s to play ...", game.getCurrentPlayer().getPseudo()));

            for(int i = 0; i < 7; i++) {
                for(int j = 0; j < 7; j++)
                    grid[i][j].setVisible(false);
            }
        }

        try {
            databaseConnection.updateCurrentGamePlayer(game);
        } catch (SQLException throwables) {
            showAlert("Something wrong just happenned", "Unable to update the database, please check your Internet connection.");
            throwables.printStackTrace();
        }
    }
}
