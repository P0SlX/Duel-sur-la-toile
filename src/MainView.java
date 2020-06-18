import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;

public class MainView extends Application {

    private SceneController sceneController;
    private DatabaseConnection databaseConnection;
    private OngoingGamesController ongoingGamesController;
    private FourInARowController fourInARowController;
    private PlayerAccountController playerAccountController;
    private InvitationController invitationController;
    private AdminPanelController adminPanelController;

    private MainMenuController mainMenuController;

    private AnchorPane loadRootWithController(String fxmlPath) throws FxmlLoadingError {
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            AnchorPane root = loader.load();
            Controller controller = loader.getController();
            controller.setSceneController(this.sceneController);
            controller.setDatabaseConnection(databaseConnection);

            if(controller instanceof LoginController) {
                LoginController loginController = (LoginController)controller;
                loginController.setMainMenuController(mainMenuController);

            } else if(controller instanceof MainMenuController) {
                this.mainMenuController = (MainMenuController) controller;
                this.ongoingGamesController.setMainMenuController(mainMenuController);

            } else if (controller instanceof PlayerAccountController) {
                this.playerAccountController = (PlayerAccountController) controller;

            } else if(controller instanceof OngoingGamesController) {
                this.ongoingGamesController = (OngoingGamesController) controller;
                this.ongoingGamesController.setFourInARowController(this.fourInARowController);

            } else if(controller instanceof FourInARowController)
                this.fourInARowController = (FourInARowController) controller;

            else if(controller instanceof InvitationController)
                this.invitationController = (InvitationController)controller;
            else if(controller instanceof AdminPanelController) {
                this.adminPanelController = (AdminPanelController)controller;
                this.adminPanelController.setMainMenuController(this.mainMenuController);
            }

            return root;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FxmlLoadingError();
        }
    }

    private Pane loadRoot(String fxmlPath) throws FxmlLoadingError{
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FxmlLoadingError();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.databaseConnection = new DatabaseConnection();

        try {
            Scene scene = new Scene(new Pane());
            this.sceneController = new SceneController(scene);
            this.databaseConnection.connexion();


            // If avatar not set, set the default one
            for (Player p : this.databaseConnection.getAllPlayers()) {
                if (p.getPlayerAvatar().getWidth() == 0) {
                    p.setPlayerAvatar(new Image(new FileInputStream("img/avatarDefault.png")));
                    p.setAvatar("img/avatarDefault.png");
                    this.databaseConnection.updatePlayer(p);
                }
            }

            Pane fourInARowScene = new Pane(loadRootWithController("UI/Puissance4_ingame.fxml"));
            Pane ongoingGames    = new Pane(loadRootWithController("UI/Parties.fxml"));
            Pane invitation      = new Pane(loadRootWithController("UI/Invitations.fxml"));
            Pane mainMenuScene   = new Pane(loadRootWithController("UI/Menu_principal.fxml"));
            Pane loginScene      = new Pane(loadRootWithController("UI/Connexion.fxml"));
            Pane registerScene   = new Pane(loadRootWithController("UI/Inscription.fxml"));
            Pane playerAccount   = new Pane(loadRootWithController("UI/Profil_joueur.fxml"));
            Pane adminPanel      = new Pane(loadRootWithController("UI/Admin.fxml"));

            this.mainMenuController.setOngoingGamesController(this.ongoingGamesController);
            this.mainMenuController.setPlayerAccountController(this.playerAccountController);
            this.mainMenuController.setAdminPanelController(this.adminPanelController);
            Controller.setInvitationController(this.invitationController);

            sceneController.addScene(SceneController.ViewType.Login, loginScene);
            sceneController.addScene(SceneController.ViewType.Register, registerScene);
            sceneController.addScene(SceneController.ViewType.MainMenu, mainMenuScene);
            sceneController.addScene(SceneController.ViewType.FourInARowGame, fourInARowScene);
            sceneController.addScene(SceneController.ViewType.OngoingGames, ongoingGames);
            sceneController.addScene(SceneController.ViewType.Invitations, invitation);
            sceneController.addScene(SceneController.ViewType.PlayerAccount, playerAccount);
            sceneController.addScene(SceneController.ViewType.AdminPanel, adminPanel);

            sceneController.showScene(SceneController.ViewType.Login);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Duel sur la toile");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws SQLException, FileNotFoundException {
        if (Controller.getLoggedPlayer() != null){
            databaseConnection.setStatus(Controller.getLoggedPlayer(), 0);
        }
        Platform.exit();
    }
}
