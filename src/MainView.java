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

    private LoginView loginScene;
    private RegisterView registerScene;
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
            this.loginScene = new LoginView(loadRoot("UI/Connexion.fxml"));
            this.registerScene = new RegisterView((loadRoot("UI/Inscription.fxml")));
            this.scene = new Scene(registerScene);

            scene.setOnMouseClicked(keyEvent -> {
                if(keyEvent.getButton().equals(MouseButton.PRIMARY))
                    scene.setRoot(loginScene);
            });

            primaryStage.setScene(scene);
            primaryStage.setTitle("Duel sur la toile - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
