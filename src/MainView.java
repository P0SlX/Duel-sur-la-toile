import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainView extends Application {

    private SceneController sceneController;
    private Scene scene;
    private DatabaseConnection databaseConnection;

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
            } else if(controller instanceof MainMenuController)
                this.mainMenuController = (MainMenuController)controller;

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
            this.scene = new Scene(new Pane());
            this.sceneController = new SceneController(scene);
            this.databaseConnection.connexion();

            Pane mainMenuScene   = new Pane(loadRootWithController("UI/Menu_principal.fxml"));
            Pane loginScene      = new Pane(loadRootWithController("UI/Connexion.fxml"));
            Pane registerScene   = new Pane(loadRootWithController("UI/Inscription.fxml"));
            Pane ongoingGames    = new Pane(loadRootWithController("UI/Parties.fxml"));
            //Pane playerAccount    = new Pane(loadRoot("UI/Profil_joueur.fxml"));
            //Pane fourInARowScene = new Pane(loadRoot("UI/Puissance4_ingame.fxml"));

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

}
