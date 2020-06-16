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

    @FXML
    public ImageView avatar;

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

    /**
     * Represent the left friend list bar
     */
    @FXML
    public VBox friendList;

    @FXML
    public Label ratio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void initPlayerAccountController(Player player) throws IOException, SQLException {

        loggedPlayer = Controller.getLoggedPlayer();
        this.pseudo.setText(player.getPseudo());
        this.ratio.setText("Ratio : 9000");

        avatar.setImage(loggedPlayer.getPlayerAvatar());
        Ellipse circle = new Ellipse();
        circle.setRadiusX(30);
        circle.setRadiusY(30);
        avatar.setClip(circle);
        circle.setCenterX(30);
        circle.setCenterY(30);

        ArrayList<Player> friends = databaseConnection.getFriends(loggedPlayer);

        for(Player p : friends)
            Controller.addFriend(p, friendList, actionEvent -> {});

    }

    @FXML
    public void onPlayerAction() {
    }

    @FXML
    public void onLogoutAction() {
    }

    @FXML
    public void onBackAction() {
    }
}
