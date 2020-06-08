import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainView extends Application {

    private SceneController sceneController;
    private Scene scene;

    private static Pane loadRoot(String fxmlPath) throws FxmlLoadingError {
        URL url = null;
        try {
            url = new File(fxmlPath).toURI().toURL();
            return FXMLLoader.load(url);
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
            Pane loginScene = new Pane(loadRoot("UI/Connexion.fxml"));
            Pane registerScene   = new Pane((loadRoot("UI/Inscription.fxml")));
            Pane mainMenuScene   = new Pane(loadRoot("UI/Menu_principal.fxml"));
            Pane playerAccount    = new Pane(loadRoot("UI/Profil_joueur.fxml"));
            Pane fourInARowScene = new Pane(loadRoot("UI/Puissance4_ingame.fxml"));

            this.scene = new Scene(registerScene);
            this.sceneController = new SceneController(scene);

            sceneController.addScene(SceneController.ViewType.Login, loginScene);
            sceneController.addScene(SceneController.ViewType.Register, registerScene);
            sceneController.addScene(SceneController.ViewType.MainMenu, mainMenuScene);
            sceneController.addScene(SceneController.ViewType.PlayerAccount, playerAccount);
            sceneController.addScene(SceneController.ViewType.FourInARowGame, fourInARowScene);

            sceneController.showScene(SceneController.ViewType.Login);

            // Temporary code to test scene switch
            scene.setOnMousePressed(mouseEvent -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
                    sceneController.showScene(SceneController.ViewType.FourInARowGame);
            });

            primaryStage.setScene(scene);
            primaryStage.setTitle("Duel sur la toile - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
