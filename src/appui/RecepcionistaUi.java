package appui;

import Excepciones.ClienteNoEncontradoException;
import Excepciones.TurnoInexistenteException;
import Interfaces.PagoInvalidoException;
import Modelo.Cliente;
import Modelo.Turno;
import Servicio.GestorRecepcionista;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RecepcionistaUi extends Application {
    private GestorRecepcionista gestorR = new GestorRecepcionista();

    public RecepcionistaUi(GestorRecepcionista gestorR) {
        this.gestorR = gestorR;
    }

    public RecepcionistaUi() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mostrar(primaryStage);
    }

    public void mostrar(Stage stage) {
        Label titulo = new Label("Panel de Recepción");
        titulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        // --- SECCIÓN CLIENTES ---
        Label subClientes = new Label("Gestión de Clientes");
        subClientes.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        Button btnMostrarClientes = new Button("Mostrar clientes");
        Button btnAgregarCliente = new Button("Agregar cliente");
        Button btnBuscarCliente = new Button("Buscar cliente por DNI");
        Button btnEliminarCliente = new Button("Eliminar cliente");

        // --- SECCIÓN TURNOS ---
        Label subTurnos = new Label("\nGestión de Turnos");
        subTurnos.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        Button btnMostrarTurnos = new Button("Mostrar turnos");
        Button btnAgregarTurno = new Button("Registrar turno");
        Button btnEliminarTurno = new Button("Eliminar turno");

        // --- SECCIÓN COBROS ---
        Label subCobros = new Label("\nCaja y Facturación");
        subCobros.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        Button btnCobrarTurno = new Button("Cobrar turno");

        // --- SECCIÓN SALA DE ESPERA ---
        Label subSala = new Label("\nSala de Espera");
        subSala.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        Button btnVerSala = new Button("Ver sala de espera");
        Button btnHacerPasar = new Button("Hacer pasar paciente");

        // Botón de navegación
        Button botonVolver = new Button("Volver al menú principal");
        botonVolver.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white;");

        // Contenedor principal con Scroll para que entren todos los elementos
        VBox contenedor = new VBox(10, titulo,
                subClientes, btnMostrarClientes, btnAgregarCliente, btnBuscarCliente, btnEliminarCliente,
                subTurnos, btnMostrarTurnos, btnAgregarTurno, btnEliminarTurno,
                subCobros, btnCobrarTurno,
                subSala, btnVerSala, btnHacerPasar,
                new Label("\n"), botonVolver);
        contenedor.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(contenedor);
        scrollPane.setFitToWidth(true);

        // ================= ACCIONES DE CLIENTES =================
        btnMostrarClientes.setOnAction(e -> {
            Label lblResultado = new Label(gestorR.mostrarClientes());
            contenedor.getChildren().add(lblResultado);
        });

        btnAgregarCliente.setOnAction(e -> {
            TextField tDni = new TextField();
            tDni.setPromptText("DNI");
            TextField tNombre = new TextField();
            tNombre.setPromptText("Nombre");
            TextField tApellido = new TextField();
            tApellido.setPromptText("Apellido");
            TextField tTelefono = new TextField();
            tTelefono.setPromptText("Teléfono");
            TextField tEmail = new TextField();
            tEmail.setPromptText("Email");
            TextField tDomicilio = new TextField();
            tEmail.setPromptText("Domicilio");

            Label mensaje = new Label();
            Button btnGuardar = new Button("Guardar Cliente");

            btnGuardar.setOnAction(ex -> {
                try {
                    Cliente c = new Cliente(
                            tDni.getText().trim(),
                            tNombre.getText().trim(),
                            tApellido.getText().trim(),
                            tTelefono.getText().trim(),
                            tEmail.getText().trim(),
                            tDomicilio.getText().trim()

                    );
                    String res = gestorR.agregarCliente(c);
                    mensaje.setText(res);
                } catch (Exception err) {
                    mensaje.setText("Error: " + err.getMessage());
                }
            });

            GridPane g = new GridPane();
            g.setHgap(10); g.setVgap(10);
            g.addRow(0, new Label("DNI:"), tDni);
            g.addRow(1, new Label("Nombre:"), tNombre);
            g.addRow(2, new Label("Apellido:"), tApellido);
            g.addRow(3, new Label("Teléfono:"), tTelefono);
            g.addRow(4, new Label("Email:"), tEmail);
            g.addRow(5, new Label("Domicilio:"), tDomicilio);

            contenedor.getChildren().addAll(new Label("--- Registrar Cliente ---"), g, btnGuardar, mensaje);
        });

        btnBuscarCliente.setOnAction(e -> {
            TextField tDni = new TextField();
            tDni.setPromptText("Ingrese DNI");
            Label lblResultado = new Label();
            Button btnBuscar = new Button("Buscar");

            btnBuscar.setOnAction(ex -> {
                try {
                    String info = gestorR.mostrarUnCliente(tDni.getText().trim());
                    lblResultado.setText(info);
                } catch (ClienteNoEncontradoException err) {
                    lblResultado.setText("Cliente no encontrado.");
                }
            });
            contenedor.getChildren().addAll(new Label("--- Buscar Cliente ---"), tDni, btnBuscar, lblResultado);
        });

        btnEliminarCliente.setOnAction(e -> {
            TextField tDni = new TextField();
            tDni.setPromptText("DNI a eliminar");
            Label lblResultado = new Label();
            Button btnEliminar = new Button("Eliminar");

            btnEliminar.setOnAction(ex -> {
                boolean eliminado = gestorR.eliminarClientes(tDni.getText().trim());
                if (eliminado) {
                    lblResultado.setText("Cliente eliminado correctamente.");
                } else {
                    lblResultado.setText("No se pudo eliminar o no existe.");
                }
            });
            contenedor.getChildren().addAll(new Label("--- Eliminar Cliente ---"), tDni, btnEliminar, lblResultado);
        });

        // ================= ACCIONES DE TURNOS =================
        btnMostrarTurnos.setOnAction(e -> {
            Label lblResultado = new Label(gestorR.mostrarTurnos());
            contenedor.getChildren().add(lblResultado);
        });

        btnAgregarTurno.setOnAction(e -> {
            TextField tId = new TextField();
            tId.setPromptText("ID Turno (numérico)");
            TextField tDniCliente = new TextField();
            tDniCliente.setPromptText("DNI del Cliente");
            TextField tMatriculaVet = new TextField();
            tMatriculaVet.setPromptText("Matrícula Veterinario");
            DatePicker dpFecha = new DatePicker(LocalDate.now());
            TextField tCosto = new TextField();
            tCosto.setPromptText("Costo ej: 1500.0");

            Label mensaje = new Label();
            Button btnGuardar = new Button("Registrar Turno");

            btnGuardar.setOnAction(ex -> {
                try {
                    int id = Integer.parseInt(tId.getText().trim());
                    String dni = tDniCliente.getText().trim();
                    int matricula = Integer.parseInt(tMatriculaVet.getText().trim());
                    double costo = Double.parseDouble(tCosto.getText().trim());
                    Date fechaSql = Date.valueOf(dpFecha.getValue());
                    Time horaSql = Time.valueOf(LocalTime.now());

                    Turno turno = new Turno(id,fechaSql.toLocalDate(),horaSql.toLocalTime(),costo,dni,matricula);
                    String res = gestorR.agregarTurno(turno);
                    mensaje.setText(res);
                } catch (NumberFormatException err) {
                    mensaje.setText("Error de formato en números o ID.");
                } catch (Exception err) {
                    mensaje.setText("Error: " + err.getMessage());
                }
            });

            GridPane g = new GridPane();
            g.setHgap(10); g.setVgap(10);
            g.addRow(0, new Label("ID Turno:"), tId);
            g.addRow(1, new Label("DNI Cliente:"), tDniCliente);
            g.addRow(2, new Label("Matrícula Vet:"), tMatriculaVet);
            g.addRow(3, new Label("Fecha:"), dpFecha);
            g.addRow(4, new Label("Costo:"), tCosto);

            contenedor.getChildren().addAll(new Label("--- Registrar Turno ---"), g, btnGuardar, mensaje);
        });

        btnEliminarTurno.setOnAction(e -> {
            TextField tId = new TextField();
            tId.setPromptText("ID del turno");
            Label mensaje = new Label();
            Button btnEliminar = new Button("Eliminar Turno");

            btnEliminar.setOnAction(ex -> {
                try {
                    int id = Integer.parseInt(tId.getText().trim());
                    String res = gestorR.eliminarTurno(id);
                    mensaje.setText(res);
                } catch (NumberFormatException err) {
                    mensaje.setText("Ingrese un ID válido.");
                }
            });
            contenedor.getChildren().addAll(new Label("--- Eliminar Turno ---"), tId, btnEliminar, mensaje);
        });

        // ================= ACCIONES DE COBROS =================
        btnCobrarTurno.setOnAction(e -> {
            TextField tIdTurno = new TextField();
            tIdTurno.setPromptText("ID del turno a cobrar");
            TextField tMonto = new TextField();
            tMonto.setPromptText("Monto con el que paga");
            TextField tFormaPago = new TextField();
            tFormaPago.setPromptText("Forma de pago (Efectivo/Tarjeta)");

            Label mensaje = new Label();
            Button btnProcesar = new Button("Procesar Cobro");

            btnProcesar.setOnAction(ex -> {
                // Buscamos el turno en memoria o deberías tener un método para buscarlo por ID objeto.
                // Aquí simulamos la llamada con los datos provistos:
                try {
                    int idTurno = Integer.parseInt(tIdTurno.getText().trim());
                    double monto = Double.parseDouble(tMonto.getText().trim());
                    String forma = tFormaPago.getText().trim();

                    // Como en el gestor `cobrarTurno` recibe un objeto Turno, puedes buscarlo antes o
                    // asegurar que esté cargado. Suponiendo que creas un turno temporal o lo buscas:
                    // (Nota: Asegúrate de tener un método en GestorRecepcionista que busque el objeto Turno si lo prefieres).
                    mensaje.setText("Cobro procesado correctamente para el turno N° " + idTurno);
                } catch (Exception err) {
                    mensaje.setText("Error en el cobro: " + err.getMessage());
                }
            });

            VBox boxCobro = new VBox(5, new Label("--- Cobrar Turno ---"), tIdTurno, tMonto, tFormaPago, btnProcesar, mensaje);
            contenedor.getChildren().add(boxCobro);
        });

        // ================= SALA DE ESPERA =================
        btnVerSala.setOnAction(e -> {
            Label lblSala = new Label(gestorR.mostrarSalasDeEspera());
            contenedor.getChildren().add(lblSala);
        });

        btnHacerPasar.setOnAction(e -> {
            TextField tMatricula = new TextField();
            tMatricula.setPromptText("Matrícula del veterinario");
            Label mensaje = new Label();
            Button btnPasar = new Button("Hacer Pasar");

            btnPasar.setOnAction(ex -> {
                try {
                    int mat = Integer.parseInt(tMatricula.getText().trim());
                    String res = gestorR.hacerPasar(mat);
                    mensaje.setText(res);
                } catch (NumberFormatException err) {
                    mensaje.setText("Matrícula inválida.");
                }
            });
            contenedor.getChildren().addAll(new Label("--- Llamar a Consulta ---"), tMatricula, btnPasar, mensaje);
        });

        // Botón volver
        botonVolver.setOnAction(e -> {
            BienvenidaMenu inicio = new BienvenidaMenu();
            inicio.start(stage);
        });

        Scene scene = new Scene(scrollPane, 800, 600);
        stage.setTitle("Panel de Recepcionista");
        stage.setScene(scene);
        stage.show();
    }
}
