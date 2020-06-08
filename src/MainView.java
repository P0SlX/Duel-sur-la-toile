import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainView extends Application {

    private LoginView loginScene;

    private static Parent loadRoot(String fxmlPath) throws FxmlLoadingError {
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

            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Duel sur la toile - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
