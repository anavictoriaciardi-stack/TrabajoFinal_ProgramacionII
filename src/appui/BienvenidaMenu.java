package appui;

import Modelo.Veterinario;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BienvenidaMenu extends Application {

    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage primaryStage) {
        //mensajes para el menu principal.
        Label titulo = new Label("Veterinaria BCS\nBienvenido.");
        Button boton1 = new Button("Veterinarios/a");
        Button boton2 = new Button("Administradores/a");
        Button boton3 = new Button("Recepcionistas");
        //contenedor VBox
        VBox contenedor = new VBox(10, titulo,boton1,boton2, boton3);

        //Acciones de botones

        boton1.setOnAction(e -> {
            VeterinarioUi vui = new VeterinarioUi();
        });
        boton2.setOnAction(e -> {
            AdministradorUi admUi = new AdministradorUi();
        });

        boton3.setOnAction(e -> {
            RecepcionistaUi rui = new RecepcionistaUi();
        });
        Scene scena = new Scene(contenedor, 750, 500);

        primaryStage.setTitle("App Veterinaria");
        primaryStage.setScene(scena);

        primaryStage.show();
    }
}
