import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerAccountController extends Controller implements Initializable {

    private MainMenuController mainMenuController;

    @FXML
    public ImageView avatar;

    @FXML
    public ImageView avatar1;

    public final String PSEUDO = "-fx-font-size: 13pt; -fx-font-family: \"Segoe UI Light\";" +
            "-fx-text-fill: white;" +
            "-fx-opacity: 1;";

    public final String BUTTON = "-fx-background-color: #1E90FF; -fx-border-color: #1E90FF; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-text-fill: white; -fx-padding: 0;";

    @FXML
    public MenuItem disconnect;

    @FXML
    public MenuItem quit;

    @FXML
    public Label pseudo;

    @FXML
    public Label ratio;

    @FXML
    public Label victories;

    @FXML
    public Label defeat;

    @FXML
    public Label activeGames;

    @FXML
    public Label consecWin;

    @FXML
    public Label playedGames;

    @FXML
    public Label abandGames;

    @FXML
    public Label pseudoLeft;

    /**
     * Represent the left friend list bar
     */
    @FXML
    public VBox friendList;

    private PlayerStatistics ps;

    private Player loggedPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void initPlayerAccountController(Player player) throws IOException, SQLException {

        this.friendList.getChildren().clear();
        this.ps = databaseConnection.getPlayerStatistics(player);

        this.loggedPlayer = Controller.getLoggedPlayer();
        this.pseudo.setText(player.getPseudo());
        this.ratio.setText(String.valueOf(ps.getRatio()));

        this.setAvatar(avatar, loggedPlayer, 30);
        this.setAvatar(avatar1, loggedPlayer, 80);

        ArrayList<Player> friends = databaseConnection.getFriends(loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(p, friendList, actionEvent -> {});

        this.displayPlayerStat(player);

        this.displayHistory(player);
    }

    private void setAvatar(ImageView im, Player p, int radius) {
        im.setImage(loggedPlayer.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(radius);
        circle.setRadiusY(radius);
        im.setClip(circle);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
    }

    private void displayPlayerStat(Player p) throws SQLException {
        this.pseudoLeft.setText(p.getPseudo());

        this.ratio.setText("Ratio: " + this.ps.getRatio());
        this.victories.setText("Victories: " + this.ps.getVictories());
        this.defeat.setText("Defeat: " + this.ps.getDefeats());
        this.activeGames.setText("Active Games: " + this.ps.getActiveGames());
        this.consecWin.setText("Consecutive wins: " + this.ps.getConsecutiveWins());
        this.playedGames.setText("Played games: " + this.ps.getPlayedGames());
        this.abandGames.setText("Abandoned games: " + this.ps.getAbandonedGames());
    }

    private void displayHistory(Player p) {

    }

    @FXML
    public void onLogoutAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, 0); // Set disconnected
        sceneController.showScene(SceneController.ViewType.Login);
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    public void onBackAction() throws IOException, SQLException {
        this.mainMenuController.initMainControllerWithPlayer(this.loggedPlayer);
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }
}
