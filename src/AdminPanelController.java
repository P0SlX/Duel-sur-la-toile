import com.sun.javafx.scene.layout.region.LayeredBackgroundPositionConverter;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Ellipse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminPanelController extends Controller implements Initializable {

    @FXML
    public TextField searchBar;

    @FXML
    public ImageView avatar;

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
    public TextField email;

    @FXML
    public TextField password;

    @FXML
    public TextField state;

    @FXML
    public Button adminButton;

    @FXML
    public Button desacButton;

    @FXML
    public Button updateButton;

    @FXML
    public Button backButton;

    private Player player;

    private PlayerStatistics playerStatistics;

    private MainMenuController mainMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onUpdateButton(ActionEvent actionEvent) throws FileNotFoundException, SQLException {
        this.player.setEmail(this.email.getText());
        this.player.setMdp(this.password.getText());
        this.player.setEtat(Integer.parseInt(this.state.getText()));
        databaseConnection.updatePlayer(this.player);
    }

    @FXML
    public void onPlayerAction() throws IOException, SQLException {
        playerAccountController.initPlayerAccountController(loggedPlayer);
        sceneController.showScene(SceneController.ViewType.PlayerAccount);
    }

    @FXML
    public void onDeactivatedButtonAction() {
        if (this.desacButton.getText().equals("True")) {
            this.desacButton.setText("False");
            this.player.setDesactivated(false);
        } else {
            this.desacButton.setText("True");
            this.player.setDesactivated(true);

        }
    }

    @FXML
    public void onAdminButtonAction() {
        if (this.adminButton.getText().equals("True")) {
            this.adminButton.setText("False");
            this.player.setAdmin(false);
        } else {
            this.adminButton.setText("True");
            this.player.setAdmin(true);
        }
    }

    @FXML
    public void onBackAction() throws IOException, SQLException {
        this.mainMenuController.initMainControllerWithPlayer(loggedPlayer);
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }


    @FXML
    public void onSearchBarAction(KeyEvent event) throws IOException, SQLException {
        if(event.getCode().equals(KeyCode.ENTER)) {
            this.player = databaseConnection.getPlayer(this.searchBar.getText());
            if (this.player != null) {
                this.playerStatistics = databaseConnection.getPlayerStatistics(this.player);
                this.displayInfoPlayer(this.player, this.playerStatistics);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error while fetching player " + this.searchBar.getText());
                alert.setContentText("Player not found...");
                alert.show();
            }
        }
    }

    private void displayInfoPlayer(Player p, PlayerStatistics ps) {
        this.avatar.setImage(p.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(50);
        circle.setRadiusY(50);
        avatar.setClip(circle);
        circle.setCenterX(50);
        circle.setCenterY(50);
        this.pseudo.setText(p.getPseudo());
        this.email.setText(p.getEmail());
        this.password.setText(p.getMdp());
        this.state.setText(String.valueOf(p.getEtat()));
        if (p.isDesactivated()) {
            this.desacButton.setText("True");
        } else {
            this.desacButton.setText("False");
        }
        if (p.isAdmin()) {
            this.adminButton.setText("True");
        } else {
            this.adminButton.setText("False");
        }

        this.ratio.setText("Ratio: " + ps.getRatio());
        this.victories.setText("Victories: " + ps.getVictories());
        this.defeat.setText("Defeats: " + ps.getDefeats());
        this.activeGames.setText("Active games: " + ps.getActiveGames());
        this.consecWin.setText("Consecutive wins: " + ps.getConsecutiveWins());
        this.playedGames.setText("Played games: " + ps.getPlayedGames());
        this.abandGames.setText("Abandoned games: " + ps.getAbandonedGames());
    }

    @FXML
    public void onDisconnectAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
        sceneController.showScene(SceneController.ViewType.Login);
    }
}
