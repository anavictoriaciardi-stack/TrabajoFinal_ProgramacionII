package appui;

import Modelo.TIPOdeUSUARIO;
import Modelo.Usuario;
import Servicio.GestorAdministrador;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BienvenidaMenu extends Application {
    private final GestorAdministrador gestor = new GestorAdministrador();

    public TextField campo(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        return campo;
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        Label titulo = new Label("Veterinaria BCS\nBienvenido.");
        TextField tUsername = campo("Nombre de usuario");
        PasswordField tPassword = new PasswordField();
        tPassword.setPromptText("Contraseña");
        Button boton1 = new Button("Iniciar sesión");
        Button salir = new Button("Salir");
        Label mensaje = new Label();
        VBox contenedor = new VBox(10, titulo, tUsername, tPassword, boton1, mensaje, salir);

        boton1.setDefaultButton(true);
        boton1.setOnAction(e -> {
            String username = tUsername.getText().trim();
            String password = tPassword.getText();
            if (username.isEmpty() || password.isEmpty()) {
                mensaje.setText("Ingrese su nombre de usuario y contraseña.");
                return;
            }

            Usuario usuario = gestor.buscarUsuarioXcontrasena(password);
            if (usuario == null || !usuario.getUsername().equals(username)) {
                mensaje.setText("Usuario o contraseña incorrectos.");
                tPassword.clear();
                return;
            }

            mensaje.setText("");
            if (usuario.getRol() == TIPOdeUSUARIO.ADMINISTRADOR) {
                new AdministradorUi().mostrarA(primaryStage);
            } else if (usuario.getRol() == TIPOdeUSUARIO.RECEPCIONISTA) {
                new RecepcionistaUi().mostrar(primaryStage);
            } else if (usuario.getRol() == TIPOdeUSUARIO.VETERINARIO) {
                new VeterinarioUi().mostrar(primaryStage);
            }
        });
        salir.setOnAction(e -> Platform.exit());

        Scene scena = new Scene(contenedor, 750, 500);
        primaryStage.setTitle("App Veterinaria");
        primaryStage.setScene(scena);
        primaryStage.show();
    }
}
