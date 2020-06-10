public abstract class Controller {

    protected SceneController sceneController;
    protected DatabaseConnection databaseConnection;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void setDatabaseConnection(DatabaseConnection database) {
        this.databaseConnection = database;
    }
}
