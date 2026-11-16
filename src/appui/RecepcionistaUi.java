package appui;

import Modelo.Cliente;
import Modelo.Turno;
import Persistencia.ClienteDAO;
import Persistencia.TurnoDAO;
import Servicio.GestorRecepcionista;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class RecepcionistaUi {
    private final GestorRecepcionista gestor;

    public RecepcionistaUi() {
        this(new GestorRecepcionista());
    }

    public RecepcionistaUi(GestorRecepcionista gestor) {
        this.gestor = gestor;
    }

    private TextField campo(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        return campo;
    }

    private GridPane formulario() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        return grid;
    }

    public void mostrar(Stage stage) {
        Label titulo = new Label("Bienvenido/a a Recepción");
        titulo.setStyle("-fx-font-size: 20px;");
        Label clientesTitulo = new Label("Clientes");
        Label turnosTitulo = new Label("Turnos");

        Button agregarCliente = new Button("Agregar cliente");
        Button buscarCliente = new Button("Buscar cliente");
        Button eliminarCliente = new Button("Eliminar cliente");
        Button mostrarTurnos = new Button("Mostrar turnos");
        Button agregarTurno = new Button("Agregar turno");
        Button buscarTurno = new Button("Buscar turno");
        Button modificarTurno = new Button("Modificar turno");
        Button eliminarTurno = new Button("Eliminar turno");
        Label resultado = new Label();
        resultado.setWrapText(true);

        VBox contenido = new VBox(10, titulo, clientesTitulo, agregarCliente, buscarCliente,
                eliminarCliente, turnosTitulo, mostrarTurnos, agregarTurno, buscarTurno,
                modificarTurno, eliminarTurno, resultado);

        agregarCliente.setOnAction(e -> {
            TextField dni = campo("DNI");
            TextField nombre = campo("Nombre");
            TextField apellido = campo("Apellido");
            TextField telefono = campo("Teléfono");
            TextField email = campo("Email");
            TextField domicilio = campo("Domicilio");
            Button guardar = new Button("Guardar cliente");
            GridPane grid = formulario();
            grid.addRow(0, new Label("DNI:"), dni);
            grid.addRow(1, new Label("Nombre:"), nombre);
            grid.addRow(2, new Label("Apellido:"), apellido);
            grid.addRow(3, new Label("Teléfono:"), telefono);
            grid.addRow(4, new Label("Email:"), email);
            grid.addRow(5, new Label("Domicilio:"), domicilio);
            guardar.setOnAction(ev -> {
                if (dni.getText().isBlank() || nombre.getText().isBlank() || apellido.getText().isBlank()
                        || telefono.getText().isBlank() || email.getText().isBlank() || domicilio.getText().isBlank()) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("Completá todos los campos.");
                    return;
                }
                Cliente cliente = new Cliente(dni.getText().trim(), nombre.getText().trim(),
                        apellido.getText().trim(), telefono.getText().trim(), email.getText().trim(), domicilio.getText().trim());
                resultado.setStyle("-fx-text-fill: green;");
                resultado.setText(gestor.agregarCliente(cliente));
            });
            contenido.getChildren().addAll(new Label("Nuevo cliente"), grid, guardar);
        });

        buscarCliente.setOnAction(e -> {
            TextField dni = campo("DNI del cliente");
            Button buscar = new Button("Buscar");
            buscar.setOnAction(ev -> {
                try {
                    Cliente cliente = new ClienteDAO().listarClientes().stream()
                            .filter(c -> c.getDni().equals(dni.getText().trim())).findFirst().orElse(null);
                    resultado.setStyle(cliente == null ? "-fx-text-fill: red;" : "-fx-text-fill: black;");
                    resultado.setText(cliente == null ? "No se encontró el cliente." : cliente.toString());
                } catch (Exception ex) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("No se pudo buscar el cliente: " + ex.getMessage());
                }
            });
            contenido.getChildren().addAll(new Label("DNI del cliente:"), dni, buscar);
        });

        eliminarCliente.setOnAction(e -> {
            TextField dni = campo("DNI del cliente");
            Button eliminar = new Button("Eliminar");
            eliminar.setOnAction(ev -> {
                try {
                    boolean eliminado = new ClienteDAO().eliminar(dni.getText().trim());
                    resultado.setStyle(eliminado ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                    resultado.setText(eliminado ? "Cliente eliminado." : "No se encontró el cliente.");
                } catch (Exception ex) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("No se pudo eliminar el cliente: " + ex.getMessage());
                }
            });
            contenido.getChildren().addAll(new Label("DNI del cliente:"), dni, eliminar);
        });

        mostrarTurnos.setOnAction(e -> resultado.setText(gestor.mostrarTurnos()));

        agregarTurno.setOnAction(e -> {
            TextField fecha = campo("Fecha (AAAA-MM-DD)");
            TextField hora = campo("Hora (HH:MM)");
            TextField costo = campo("Costo");
            TextField dni = campo("DNI del cliente");
            TextField matricula = campo("Matrícula veterinaria");
            Button guardar = new Button("Guardar turno");
            GridPane grid = formulario();
            grid.addRow(0, new Label("Fecha:"), fecha);
            grid.addRow(1, new Label("Hora:"), hora);
            grid.addRow(2, new Label("Costo:"), costo);
            grid.addRow(3, new Label("DNI cliente:"), dni);
            grid.addRow(4, new Label("Matrícula veterinario:"), matricula);
            guardar.setOnAction(ev -> {
                try {
                    Turno turno = new Turno(LocalDate.parse(fecha.getText().trim()),
                            LocalTime.parse(hora.getText().trim()),
                            Double.parseDouble(costo.getText().trim().replace(',', '.')),
                            dni.getText().trim(), Integer.parseInt(matricula.getText().trim()));
                    resultado.setText(gestor.agregarTurno(turno));
                    resultado.setStyle("-fx-text-fill: green;");
                } catch (RuntimeException ex) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("Revisá fecha, hora, costo y matrícula.");
                }
            });
            contenido.getChildren().addAll(new Label("Nuevo turno"), grid, guardar);
        });

        buscarTurno.setOnAction(e -> {
            TextField id = campo("ID del turno");
            Button buscar = new Button("Buscar");
            buscar.setOnAction(ev -> {
                try {
                    int idTurno = Integer.parseInt(id.getText().trim());
                    Turno turno = new TurnoDAO().listarTurnos().stream()
                            .filter(t -> t.getID() == idTurno).findFirst().orElse(null);
                    resultado.setStyle(turno == null ? "-fx-text-fill: red;" : "-fx-text-fill: black;");
                    resultado.setText(turno == null ? "No se encontró el turno." : turno.toString());
                } catch (Exception ex) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("Ingresá un ID válido. " + ex.getMessage());
                }
            });
            contenido.getChildren().addAll(new Label("ID del turno:"), id, buscar);
        });

        modificarTurno.setOnAction(e -> {
            TextField id = campo("ID del turno");
            TextField fecha = campo("Nueva fecha (AAAA-MM-DD)");
            TextField hora = campo("Nueva hora (HH:MM)");
            Button guardar = new Button("Guardar cambios");
            guardar.setOnAction(ev -> {
                try {
                    String mensaje = gestor.modificarTurno(Integer.parseInt(id.getText().trim()),
                            Date.valueOf(LocalDate.parse(fecha.getText().trim())),
                            Time.valueOf(LocalTime.parse(hora.getText().trim())));
                    resultado.setText(mensaje);
                    resultado.setStyle(mensaje.startsWith("Turno Modificado") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                } catch (RuntimeException ex) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("Ingresá un ID, fecha y hora válidos.");
                }
            });
            contenido.getChildren().addAll(new Label("Modificar turno"), id, fecha, hora, guardar);
        });

        eliminarTurno.setOnAction(e -> {
            TextField id = campo("ID del turno");
            Button eliminar = new Button("Eliminar");
            eliminar.setOnAction(ev -> {
                try {
                    String mensaje = gestor.eliminarTurno(Integer.parseInt(id.getText().trim()));
                    resultado.setText(mensaje);
                    resultado.setStyle(mensaje.contains("correctamente") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                } catch (NumberFormatException ex) {
                    resultado.setStyle("-fx-text-fill: red;");
                    resultado.setText("Ingresá un ID entero válido.");
                }
            });
            contenido.getChildren().addAll(new Label("ID del turno:"), id, eliminar);
        });

        Scene scene = new Scene(contenido, 750, 600);
        stage.setTitle("Zona recepcionista");
        stage.setScene(scene);
        stage.show();
    }
}
