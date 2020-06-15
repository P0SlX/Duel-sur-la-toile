import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FourInARowController extends Controller implements Initializable {

    @FXML
    public GridPane fourInARowGrid;

    private Button[][] grid;

    private FourInARow game;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grid = new Button[7][7];

        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                grid[i][j] = new Button();
                fourInARowGrid.add(grid[i][j], j, i);
                grid[i][j].setOnAction(event -> {
                    // TODO: Click handle
                });
            }
        }
    }

    public void initController(FourInARow currentGame) {
        char[][] content = currentGame.getPlate();

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

    public void onQuitAction(ActionEvent actionEvent) {
    }

    public void onPlayerProfilAction(ActionEvent actionEvent) {
    }
}
