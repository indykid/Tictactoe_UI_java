package kg.jarkyn.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kg.jarkyn.GUI.JFXViewComponents.JFXGrid;

public class JFXGameLauncher extends Application{

    private Stage stage;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setScene();
        setupStage(primaryStage);
        addGameSelector();
        show();
    }

    private void setScene() {
        scene = new Scene(new JFXGrid(), Color.BLACK);
    }

    private void setupStage(Stage primaryStage) {
        setStage(primaryStage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
    }

    private void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    private void addGameSelector() {
        new GraphicalUI(new JFXVisualiser(scene)).displayGameSelector();
    }

    private void show() {
        stage.show();
    }
}
