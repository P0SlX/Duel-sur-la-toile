import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    public void onQuitAction(ActionEvent actionEvent) {
    }

    @FXML
    public void onPlayerProfilAction(ActionEvent actionEvent) {
    }

}
