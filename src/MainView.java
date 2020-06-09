import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainView extends Application {

    private SceneController sceneController;
    private Scene scene;

    private AnchorPane loadLogin(String fxmlPath) throws FxmlLoadingError {
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            AnchorPane root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setSceneController(this.sceneController);

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

        try {
            this.scene = new Scene(new Pane());
            this.sceneController = new SceneController(scene);

            Pane loginScene = new Pane(loadLogin("UI/Connexion.fxml"));
            Pane registerScene   = new Pane((loadRoot("UI/Inscription.fxml")));
            //Pane mainMenuScene   = new Pane(loadRoot("UI/Menu_principal.fxml"));
            //Pane playerAccount    = new Pane(loadRoot("UI/Profil_joueur.fxml"));
            //Pane fourInARowScene = new Pane(loadRoot("UI/Puissance4_ingame.fxml"));


            sceneController.addScene(SceneController.ViewType.Login, loginScene);
            sceneController.addScene(SceneController.ViewType.Register, registerScene);
            //sceneController.addScene(SceneController.ViewType.MainMenu, mainMenuScene);
            //sceneController.addScene(SceneController.ViewType.PlayerAccount, playerAccount);
            //sceneController.addScene(SceneController.ViewType.FourInARowGame, fourInARowScene);

            sceneController.showScene(SceneController.ViewType.Login);

            scene.setRoot(loginScene);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Duel sur la toile - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
