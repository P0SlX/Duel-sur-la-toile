import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class MainView extends Application {

    private SceneController sceneController;
    private DatabaseConnection databaseConnection;
    private OngoingGamesController ongoingGamesController;

    private MainMenuController mainMenuController;

    private AnchorPane loadRootWithController(String fxmlPath) throws FxmlLoadingError {
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            AnchorPane root = loader.load();
            Controller controller = loader.getController();
            controller.setSceneController(this.sceneController);
            controller.setDatabaseConnection(databaseConnection);

            if (controller instanceof LoginController) {
                LoginController loginController = (LoginController) controller;
                loginController.setMainMenuController(mainMenuController);
            } else if (controller instanceof MainMenuController) {
                this.mainMenuController = (MainMenuController) controller;
                this.ongoingGamesController.setMainMenuController(mainMenuController);
            } else if (controller instanceof OngoingGamesController)
                this.ongoingGamesController = (OngoingGamesController) controller;

            return root;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FxmlLoadingError();
        }
    }

    private Pane loadRoot(String fxmlPath) throws FxmlLoadingError {
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

            Pane ongoingGames = new Pane(loadRootWithController("UI/Parties.fxml"));
            Pane mainMenuScene = new Pane(loadRootWithController("UI/Menu_principal.fxml"));
            Pane loginScene = new Pane(loadRootWithController("UI/Connexion.fxml"));
            Pane registerScene = new Pane(loadRootWithController("UI/Inscription.fxml"));
            //Pane playerAccount    = new Pane(loadRoot("UI/Profil_joueur.fxml"));
            //Pane fourInARowScene = new Pane(loadRoot("UI/Puissance4_ingame.fxml"));

            this.mainMenuController.setOngoingGamesController(this.ongoingGamesController);

            sceneController.addScene(SceneController.ViewType.Login, loginScene);
            sceneController.addScene(SceneController.ViewType.Register, registerScene);
            sceneController.addScene(SceneController.ViewType.MainMenu, mainMenuScene);
            sceneController.addScene(SceneController.ViewType.OngoingGames, ongoingGames);
            //sceneController.addScene(SceneController.ViewType.PlayerAccount, playerAccount);
            //sceneController.addScene(SceneController.ViewType.FourInARowGame, fourInARowScene);

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
        if (Controller.getLoggedPlayer() != null) {
            Controller.getLoggedPlayer().setEtat(0);
            databaseConnection.updatePlayer(Controller.getLoggedPlayer());
        }
    }
}
