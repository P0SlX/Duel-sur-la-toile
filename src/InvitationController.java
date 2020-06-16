import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InvitationController extends Controller implements Initializable {

    @FXML
    public VBox activeInvitations;

    @FXML
    public Label pseudo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onDisconnectAction() throws SQLException {
        databaseConnection.setStatus(loggedPlayer, Player.DISCONNECTED);
    }

    @FXML
    public void onBackHomeAction() {
        sceneController.showScene(SceneController.ViewType.MainMenu);
    }

    public void initInvitationController() {
        this.pseudo.setText(loggedPlayer.getPseudo());
        ArrayList<? extends Invitation> invitations;

        try {
            invitations = databaseConnection.getPlayerInvitations(loggedPlayer);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            invitations = new ArrayList<>();
        }

        loadInvitations(invitations);
    }

    public void loadInvitations(ArrayList<? extends Invitation> invitations) {
        this.activeInvitations.getChildren().clear();

        for(Invitation inv : invitations) {
            HBox invitationHBox = new HBox();
            invitationHBox.setSpacing(20.0);

            invitationHBox.setAlignment(Pos.CENTER);
            invitationHBox.setPrefHeight(100.0);
            invitationHBox.setPrefWidth(100.0);

            Label playerName = new Label();
            playerName.prefHeight(77.0);
            playerName.prefWidth(240.0);
            playerName.setText(inv.getSender().getPseudo());
            playerName.setTextFill(Color.WHITE);
            playerName.setWrapText(true);
            playerName.setFont(new Font(17));

            Label invitationDate = new Label();
            invitationDate.prefHeight(80.0);
            invitationDate.prefWidth(217.0);
            invitationDate.setText(inv.getEmissionDate().toString());
            invitationDate.setTextFill(Color.WHITE);
            invitationDate.setWrapText(true);
            invitationDate.setFont(new Font(17));

            Label gameType = new Label();
            gameType.prefHeight(21.0);
            gameType.prefWidth(80.0);
            gameType.setTextFill(Color.WHITE);
            gameType.setWrapText(true);
            gameType.setFont(new Font(17));

            if(inv instanceof GameInvitation)
                gameType.setText("FourInARow");
            else
                gameType.setText("Friend request");

            Button accept = new Button();
            accept.setMnemonicParsing(false);
            accept.setPrefHeight(69.0);
            accept.setPrefWidth(169.0);
            accept.setStyle("-fx-background-color: #3F7FBF; -fx-border-radius: 30px;");
            accept.setText("Accept");
            accept.setFont(new Font(21.0));
            accept.setOnAction(actionEvent -> {
                try {
                    inv.accept();

                    if(inv instanceof GameInvitation) {
                        databaseConnection.addNewGame(inv.getReceiver(), inv.getSender());
                    } else {
                        databaseConnection.addNewFriend(loggedPlayer, inv.getSender());
                    }

                    databaseConnection.changeStateInv(inv);
                    loadInvitations(databaseConnection.getPlayerInvitations(loggedPlayer));
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                    showAlert("Something wrong occurred", "Failed to accept your Invitation");
                }
            });

            Button refused = new Button();
            refused.setMnemonicParsing(false);
            refused.setPrefHeight(69.0);
            refused.setPrefWidth(169.0);
            refused.setStyle("-fx-background-color: #ad1a07; -fx-border-radius: 30px;");
            refused.setText("Refused");
            refused.setFont(new Font(21.0));
            refused.setOnAction(actionEvent -> {
                try {
                    inv.decline();
                    databaseConnection.changeStateInv(inv);
                    loadInvitations(databaseConnection.getPlayerInvitations(loggedPlayer));
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                    showAlert("Something wrong occured", "Failed to decline your Invitation");
                }
            });

            invitationHBox.getChildren().addAll(playerName, invitationDate, gameType, accept, refused);
            this.activeInvitations.getChildren().add(invitationHBox);

            Separator separator = new Separator();
            separator.prefHeight(14.0);
            separator.prefWidth(1067.0);
            this.activeInvitations.getChildren().add(separator);
        }
    }

    @FXML
    public void onRefreshAction() {
        ArrayList<? extends Invitation> invitations;

        try {
            invitations = databaseConnection.getPlayerInvitations(loggedPlayer);
            loadInvitations(invitations);
        } catch (SQLException | IOException exception) {
            showAlert("Failed to refresh invitations", "Please check your Internet connection and try again.");
            exception.printStackTrace();
        }
    }
}
