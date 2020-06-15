import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FourInARowController extends Controller implements Initializable {

    @FXML
    public GridPane fourInARowGrid;

    private FourInARowButton[][] grid;

    private FourInARow game;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grid = new FourInARowButton[7][7];

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

                            if(game.getPlayer1().equals(loggedPlayer))
                                fourInARowButton.setText("R");
                            else
                                fourInARowButton.setText("B");

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

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++)
                setButton(i, j, content[i][j]);
        }
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
}
