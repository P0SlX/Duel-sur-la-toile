import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    public VBox history;

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
        this.ratio.setText("Ratio: " + ps.getRatio());

        avatar.setImage(loggedPlayer.getPlayerAvatar());
        this.setAvatar(avatar, loggedPlayer, 30);
        avatar1.setImage(loggedPlayer.getPlayerAvatar());
        this.setAvatar(avatar1, loggedPlayer, 80);

        ArrayList<Player> friends = databaseConnection.getFriends(loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(p, friendList, actionEvent -> {},
                    actionEvent -> {
                        try {
                            databaseConnection.createInv(loggedPlayer, p, true); // game invitation
                            showAlert("Invitation successfully sent",
                                    String.format("Invitation sent to %s.", p.getPseudo()));
                        } catch(SQLException exception) {
                            showAlert("Could'nt send invitation", "Check your internet connection and try again.");
                            exception.printStackTrace();
                        }
                    });

        this.displayPlayerStat(player);

        this.displayHistory(player);
    }

    private void setAvatar(ImageView im, Player p, int radius) {
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

    private void displayHistory(Player p) throws IOException, SQLException {
        this.history.getChildren().clear();
        ArrayList<Game> listGame = databaseConnection.getGameListPlayer(p);

        for (int i = 0; i < 4; i++){
            AnchorPane anchorPane = new AnchorPane();
            Separator separator = new Separator();
            AnchorPane.setRightAnchor(separator, 10.0);
            AnchorPane.setLeftAnchor(separator, 10.0);
            AnchorPane.setTopAnchor(separator, 0.0);

            Label player1 = new Label();
            player1.setText(listGame.get(i).getPlayer1().getPseudo());
            player1.setStyle("-fx-font-size: 16pt; -fx-text-fill: rgb(114, 199, 64);");
            player1.setAlignment(Pos.CENTER_RIGHT);
            AnchorPane.setTopAnchor(player1,33.0);
            AnchorPane.setLeftAnchor(player1,130.0);
            AnchorPane.setRightAnchor(player1,500.0);
            AnchorPane.setBottomAnchor(player1, 33.0);

            Label player2 = new Label();
            player2.setText(listGame.get(i).getPlayer2().getPseudo());
            player2.setStyle("-fx-font-size: 16pt; -fx-text-fill: rgb(248, 51, 51);");
            AnchorPane.setTopAnchor(player2,33.0);
            AnchorPane.setLeftAnchor(player2,500.0);
            AnchorPane.setRightAnchor(player2,130.0);
            AnchorPane.setBottomAnchor(player2, 33.0);

            Label vs = new Label();
            vs.setText("VS");
            vs.setAlignment(Pos.CENTER);
            vs.setStyle("-fx-font-size: 25pt; -fx-text-fill: rgb(43, 208, 219);");
            AnchorPane.setTopAnchor(vs,33.0);
            AnchorPane.setLeftAnchor(vs,361.0);
            AnchorPane.setRightAnchor(vs,361.0);
            AnchorPane.setBottomAnchor(vs, 33.0);

            ImageView player1avatar = new ImageView(listGame.get(i).getPlayer1().getPlayerAvatar());
            player1avatar.setFitHeight(80);
            player1avatar.setFitWidth(80);
            this.setAvatar(player1avatar, listGame.get(i).getPlayer1(), 40);
            AnchorPane.setTopAnchor(player1avatar,10.0);
            AnchorPane.setLeftAnchor(player1avatar,40.0);
            AnchorPane.setBottomAnchor(player1avatar, 10.0);

            ImageView player2avatar = new ImageView(listGame.get(i).getPlayer2().getPlayerAvatar());
            player2avatar.setFitHeight(80);
            player2avatar.setFitWidth(80);
            this.setAvatar(player2avatar, listGame.get(i).getPlayer2(), 40);
            AnchorPane.setTopAnchor(player2avatar,10.0);
            AnchorPane.setRightAnchor(player2avatar,40.0);
            AnchorPane.setBottomAnchor(player2avatar,10.0);

            anchorPane.getChildren().addAll(separator,player2, player1, vs, player1avatar, player2avatar);
            history.getChildren().add(anchorPane);
        }

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
