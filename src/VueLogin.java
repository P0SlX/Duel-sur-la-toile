import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class VueLogin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = new File("UI/Connexion.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root, 1280, 835);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Duel sur la toile - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
