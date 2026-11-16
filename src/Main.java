
import Modelo.Veterinario;
import appui.BienvenidaMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
public void start(Stage primaryStage) {
    BienvenidaMenu tp = new BienvenidaMenu();
    tp.start(primaryStage);
}

    public static void main(String[] args) {
        launch(args);
    }
}
