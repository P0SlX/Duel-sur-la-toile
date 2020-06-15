import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    private Player loggedPlayer;

    private FourInARowController fourInARowController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onBackHomeAction() throws IOException, SQLException {
        this.mainMenuController.initMainControllerWithPlayer(this.loggedPlayer);
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void initOnGoingGameView() throws IOException, SQLException {

        this.loggedPlayer = getLoggedPlayer();
        this.friendList.getChildren().clear();
        ArrayList<Player> friends = databaseConnection.getFriends(this.loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(p, this.friendList);

        this.pseudo.setText(loggedPlayer.getPseudo());
        this.ratio.setText("Ratio : 9000"); // TODO: When player statistics will be done

        avatar.setImage(this.loggedPlayer.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(30);
        circle.setRadiusY(30);
        avatar.setClip(circle);
        circle.setCenterX(30);
        circle.setCenterY(30);

        this.activeGames.getChildren().clear();


        ArrayList<Game> games = databaseConnection.getActivesGames(this.loggedPlayer, GameType.FourInARow);

        // In case the player has not active game show a message
        if(games == null) {
            HBox content = new HBox();
            content.setAlignment(Pos.CENTER);

            Label message = new Label("Nothing to show here :(\nStart a new game !");
            message.setFont(new Font(20));
            content.getChildren().add(message);

            this.activeGames.getChildren().add(content);
            return;
        }

        for(Game g : games) {
            HBox gameContainer = new HBox();
            gameContainer.prefHeight(100.0);
            gameContainer.prefWidth(200.0);
            gameContainer.setSpacing(50);

            String opponentName = this.loggedPlayer.getPseudo().equals(g.getPlayer1().getPseudo()) ?
                    g.getPlayer2().getPseudo() : g.getPlayer1().getPseudo();

            Label opponent = new Label("Against : " + opponentName);
            opponent.prefHeight(77.0);
            opponent.prefWidth(223.0);
            opponent.setWrapText(true);
            opponent.setFont(new Font(17));
            opponent.setTextFill(Color.WHITE);

            Label startedOn = new Label("Started on : " + g.getStartTime().toString());
            startedOn.prefHeight(80.0);
            startedOn.prefWidth(217.0);
            startedOn.setFont(new Font(17));
            startedOn.setTextFill(Color.WHITE);

            Label yourTurn = new Label();
            if(g.getCurrentPlayer().getPseudo().equals(this.loggedPlayer.getPseudo())) {
                yourTurn.setText("Your turn");
                yourTurn.setTextFill(Color.web("#3cb929"));
            } else {
                yourTurn.setText("Waiting ...");
                yourTurn.setTextFill(Color.web("#b8612a"));
            }

            yourTurn.setPrefHeight(20.0);
            yourTurn.setPrefWidth(138.0);
            yourTurn.setFont(new Font(20));

            Button play = new Button("Play !");
            // Temp
            play.setOnAction(actionEvent -> {
                FourInARow.setDatabaseConnection(databaseConnection);
                this.fourInARowController.initController((FourInARow)g);
                sceneController.showScene(SceneController.ViewType.FourInARowGame);
            });

            //play.setDisable(!g.getCurrentPlayer().getPseudo().equals(this.loggedPlayer.getPseudo()));
            play.setMnemonicParsing(false);
            play.setStyle("-fx-background-color: #3F7FBF; -fx-border-radius: 30px;");
            play.setTextFill(Color.WHITE);
            play.setFont(new Font(21));

            Separator separator = new Separator();
            separator.setPadding(new Insets(10, 10, 0, 0));

            gameContainer.getChildren().addAll(opponent, startedOn, yourTurn, play);
            this.activeGames.getChildren().add(gameContainer);
            this.activeGames.getChildren().add(separator);
        }
    }

    public FourInARowController getFourInARowController() {
        return fourInARowController;
    }

    public void setFourInARowController(FourInARowController fourInARowController) {
        this.fourInARowController = fourInARowController;
    }
}
