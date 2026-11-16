package appui;

import Excepciones.TurnoInexistenteException;
import Modelo.Consulta;
import Modelo.Mascota;
import Servicio.GestorVeterinario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VeterinarioUi {
    private GestorVeterinario gestorV = new GestorVeterinario();

    public VeterinarioUi(GestorVeterinario gestorV) {
        this.gestorV = gestorV;
    }

    public VeterinarioUi() {

    }
    private TextField crearCampo(String placeholder) {
        TextField tf = new TextField();
        tf.setStyle("-fx-font-size: 11px;");
        tf.setPromptText(placeholder);
        return tf;
    }
    public void mostrar(Stage stage){

        //Botones de accion
        Label titulo = new Label("Bienvenido a Veterinario");
        titulo.setStyle("-fx-font-size: 20");
        Label subtitulo = new Label("Acciones con mascotas");
        subtitulo.setStyle("-fx-font-size: 15");

        Button boton1 = new Button("Mostrar pacientes");
        boton1.setStyle("-fx-font-size: 10");
        Button boton6 = new Button("Agregar paciente");
        boton6.setStyle("-fx-font-size: 10");
        Button boton3 = new Button("Eliminar paciente de la base de datos");
        boton3.setStyle("-fx-font-size: 10");
        Button boton4 = new Button("Modificar datos de un paciente");
        boton4.setStyle("-fx-font-size: 10");
        Button boton5 = new Button("Buscar paciente");
        boton5.setStyle("-fx-font-size: 10");
        //botones acciones consultas
        Label subtitulo2 = new Label("\nConsultas");
        subtitulo.setStyle("-fx-font-size: 15");
        Button botonC1 = new Button("Mostrar consultas");
        botonC1.setStyle("-fx-font-size: 10");
        Button botonC2 = new Button("Agregar consultas");
        botonC2.setStyle("-fx-font-size: 10");
        Button botonC3 = new Button("Eliminar consultas");
        botonC3.setStyle("-fx-font-size: 10");
        Button botonC4 = new Button("Modificar consultas");
        botonC4.setStyle("-fx-font-size: 10");
        Button botonC5 = new Button("Buscar consultas");
        botonC5.setStyle("-fx-font-size: 10");
        Button botonC6 = new Button("Mostrar historial clinico");
        botonC6.setStyle("-fx-font-size: 10");
        Button botonVolver = new Button("Volver al menu principal");
        botonVolver.setStyle("-fx-font-size: 10");
        VBox contenedor = new VBox(15, titulo, subtitulo,boton1, boton6, boton3, boton4, boton5, subtitulo2
                                    , botonC1, botonC2, botonC3, botonC4, botonC5, botonC6, botonVolver);
        ScrollPane scroll = new ScrollPane(contenedor);
        scroll.setFitToWidth(true);
        ///ACCIONES DE LOS BOTONES MASCOTAS
        boton1.setOnAction(e -> {
           Label mostrar = new Label(gestorV.mostrarMascotas());
           mostrar.setStyle("-fx-font-size: 10");
           contenedor.getChildren().add(mostrar);
        });
        boton6.setOnAction(e -> {
            TextField tNombre = crearCampo("Nombre");
            TextField tEspecie = crearCampo("Especie");
            TextField tRaza = crearCampo("Raza");
            TextField tEdad = crearCampo("Edad (en años)");
            TextField tDniCliente = crearCampo("DNI del dueño");

            TextArea tDescripcion = new TextArea();
            tDescripcion.setStyle("-fx-font-size: 11px;");
            tDescripcion.setPromptText("Descripción o notas de la mascota...");
            tDescripcion.setPrefRowCount(3);

            Label mensaje = new Label();
            Button btnRegistrar = new Button("Registrar mascota");
            btnRegistrar.setStyle("-fx-font-size: 11px;");

            btnRegistrar.setOnAction(e2 -> {
                String nombre = tNombre.getText().trim();
                String especie = tEspecie.getText().trim();
                String raza = tRaza.getText().trim();
                String edadTexto = tEdad.getText().trim();
                String dni = tDniCliente.getText().trim();
                String desc = tDescripcion.getText().trim();

                if (nombre.isEmpty() || especie.isEmpty() || dni.isEmpty() || edadTexto.isEmpty()) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error: Completa todos los campos obligatorios.");
                    return;
                }

                try {
                    int edadAux = Integer.parseInt(edadTexto);

                    Mascota aux = new Mascota();
                    aux.setNombre(nombre);
                    aux.setEspecie(especie);
                    aux.setRaza(raza);
                    aux.setEdad(edadAux);
                    aux.setCliente_dni(dni);

                    gestorV.agregarMascota(aux);

                    tNombre.clear();
                    tEspecie.clear();
                    tRaza.clear();
                    tEdad.clear();
                    tDniCliente.clear();
                    tDescripcion.clear();

                    mensaje.setStyle("-fx-text-fill: green;");
                    mensaje.setText("Mascota registrada exitosamente");

                } catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error: La edad debe ser un número válido.");
                }
            });
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(8);
            grid.addRow(0, new Label("Nombre:"), tNombre);
            grid.addRow(1, new Label("Especie:"), tEspecie);
            grid.addRow(2, new Label("Raza:"), tRaza);
            grid.addRow(3, new Label("Edad:"), tEdad);
            grid.addRow(4, new Label("DNI Cliente:"), tDniCliente);
            grid.addRow(5, new Label("Descripción:"), tDescripcion);

            contenedor.getChildren().addAll(new Label("Registro de Mascota"), grid, btnRegistrar, mensaje);
            });



        boton3.setOnAction(e ->{
            Label mensaje = new Label("Ingrese el id para eliminar:");
            TextField tid = new TextField();
            tid.setStyle("-fx-font-size: 10");
            tid.setPromptText("ID");
            Button eliminar = new Button("Eliminar");
            eliminar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tid.getText().trim());
                    mensaje.setText(gestorV.eliminarMascota(id));
                } catch (NumberFormatException ex) {
                    mensaje.setText("Ingrese un ID numerico valido");
                }
            });
            contenedor.getChildren().addAll(mensaje, tid, eliminar);
        });
        boton4.setOnAction(e ->{
            Label mensaje = new Label("Ingrese el ID de la mascota:");
            TextField tid = new TextField();
            tid.setPromptText("ID");
            Button buscar = new Button("Buscar");
            buscar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tid.getText().trim());
                    if (!gestorV.buscarMascota(id)) {
                        mensaje.setText("No existe la mascota");
                        return;
                    }

                    TextField tnombre = crearCampo("Nombre");
                    TextField tespecie = crearCampo("Especie");
                    TextField traza = crearCampo("Raza");
                    TextField tedad = crearCampo("Edad");
                    Label resultado = new Label();
                    Button guardar = new Button("Guardar cambios");
                    guardar.setOnAction(ev2 -> {
                        try {
                            int edad = Integer.parseInt(tedad.getText().trim());
                            resultado.setText(gestorV.modificarMascota(id, tnombre.getText().trim(),
                                    tespecie.getText().trim(), traza.getText().trim(), edad));
                        } catch (NumberFormatException ex) {
                            resultado.setText("Ingrese una edad numerica valida");
                        }
                    });
                    contenedor.getChildren().addAll(tnombre, tespecie, traza, tedad, guardar, resultado);
                } catch (NumberFormatException ex) {
                    mensaje.setText("Ingrese un ID numerico valido");
                }
            });
            contenedor.getChildren().addAll(mensaje, tid, buscar);
        });
        boton5.setOnAction(e ->{
            Label mensaje = new Label("Ingrese el nombre y el dni para buscar");
            TextField tnombre = new TextField();
            tnombre.setStyle("-fx-font-size: 10");
            tnombre.setPromptText("Nombre");
            TextField tdni_cliente = new TextField();
            tdni_cliente.setStyle("-fx-font-size: 10");
            tdni_cliente.setPromptText("DNI");
            TextArea descripcion = new TextArea();
            descripcion.setStyle("-fx-font-size: 10");
            descripcion.setPromptText("Buscar mascota---");

            String nombre = tnombre.getText();
            String dni = tdni_cliente.getText();

            Label mostrar = new Label();
            Button botonM = new Button("Buscar mascota");
            botonM.setStyle("-fx-font-size: 10");
            botonM.setOnAction(e2 -> {
                if(gestorV.buscarMascotaxdniyNombre(nombre, dni)){
                    try {
                        mostrar.setText("Mascota encontrar" + gestorV.mostrarUnaMascota(nombre, dni));
                    } catch (TurnoInexistenteException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    mostrar.setText("Mascota no encontrada");
                }
                tnombre.clear();
                tdni_cliente.clear();
            });
            contenedor.getChildren().addAll(mensaje,tnombre,tdni_cliente, botonM, mostrar);

        });

        ///ACCIONES DE LOS BOTONES CONSULTAS
        botonC1.setOnAction(e2 -> {
            Label mostrar = new Label(gestorV.mostrarConsultas());
            mostrar.setStyle("-fx-font-size: 10");
            contenedor.getChildren().addAll(mostrar);
        });
        botonC2.setOnAction(e2 -> {
//            private int mascota_id;
//            private int matricula_veterinario;
//            private LocalDateTime fecha;
//            private String diagnostico;
//            private String tratamiento;
//            private String receta;
            Label mensaje = new Label("Nueva Consulta");
            TextField tMascotaId = new TextField();
            tMascotaId.setPromptText("id de la mascota");
            TextField tMatricula = new TextField();
            tMatricula.setPromptText("matricula");
            DatePicker tFecha = new DatePicker(LocalDate.now());
            TextArea tDiagnostico = new TextArea();
            tDiagnostico.setPromptText("Diagnostico");
            tDiagnostico.setPrefRowCount(2);

            TextArea tTratamiento = new TextArea();
            tTratamiento.setPromptText("Tratamiento indicado");
            tTratamiento.setPrefRowCount(2);

            TextArea tReceta = new TextArea();
            tReceta.setPromptText("Medicamentos y dosis");
            tReceta.setPrefRowCount(2);

            Label mensaje2 = new Label();
            Button botonGuardar = new Button("Guardar Consulta");

            botonGuardar.setOnAction(e -> {
                try {
                    int mascotaId = Integer.parseInt(tMascotaId.getText().trim());
                    int matriculaVet = Integer.parseInt(tMatricula.getText().trim());


                    LocalDate fechaElegida = tFecha.getValue();
                    if (fechaElegida == null) {
                        mensaje2.setText("Error: Debe seleccionar una fecha.");
                        return;
                    }
                    LocalDateTime fechaHora = LocalDateTime.of(fechaElegida, LocalTime.now());

                    String diagnostico = tDiagnostico.getText().trim();
                    String tratamiento = tTratamiento.getText().trim();
                    String receta = tReceta.getText().trim();

                    if (diagnostico.isEmpty() || tratamiento.isEmpty()) {
                        mensaje.setText("Error Diagnóstico y tratamiento son obligatorios.");
                        return;
                    }
                    Consulta aux = new Consulta(mascotaId, matriculaVet, fechaHora, diagnostico, tratamiento, receta);
                    gestorV.agregarConsulta(aux);

                    tMascotaId.clear();
                    tMatricula.clear();
                    tFecha.setValue(LocalDate.now());
                    tDiagnostico.clear();
                    tTratamiento.clear();
                    tReceta.clear();

                    mensaje2.setText("Consulta registrada correctamente");

                } catch (NumberFormatException ex) {
                    mensaje2.setStyle("-fx-text-fill: red;");
                    mensaje2.setText("Error: El ID de mascota y la matrícula deben ser números enteros.");
                } catch (Exception ex) {
                    mensaje2.setStyle("-fx-text-fill: red;");
                    mensaje2.setText("Error al guardar: " + ex.getMessage());
                }
            });
            GridPane g = new GridPane();
            g.setHgap(10);
            g.setVgap(10);

            g.addRow(0, new Label("ID Mascota:"), tMascotaId);
            g.addRow(1, new Label("Matrícula Vet.:"), tMatricula);
            g.addRow(2, new Label("Fecha:"), tFecha);
            g.addRow(3, new Label("Diagnóstico:"), tDiagnostico);
            g.addRow(4, new Label("Tratamiento:"), tTratamiento);
            g.addRow(5, new Label("Receta:"), tReceta);

            contenedor.getChildren().addAll(mensaje, g, botonGuardar, mensaje2);
        });
        botonC3.setOnAction(e -> {
            Label mensaje = new Label("Ingrese el id para eliminar:");
            TextField tid = new TextField();
            tid.setStyle("-fx-font-size: 10");
            tid.setPromptText("ID");
            Button eliminar = new Button("Eliminar");
            eliminar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tid.getText().trim());
                    mensaje.setText(gestorV.eliminarConsulta(id));
                } catch (NumberFormatException ex) {
                    mensaje.setText("Ingrese un ID numérico válido");
                }
            });
            contenedor.getChildren().addAll(mensaje, tid, eliminar);
        });
        botonC4.setOnAction(e -> {
            Label mensaje = new Label("Ingrese el id para buscar:");
            TextField tid = new TextField();
            tid.setStyle("-fx-font-size: 10");
            tid.setPromptText("ID");
            Button buscar = new Button("Buscar");
            buscar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tid.getText().trim());
                    if (!gestorV.buscarConsulta(id)) {
                        mensaje.setText("No existe la consulta");
                        return;
                    }

                    TextField tnuevoTratamiento = new TextField();
                    tnuevoTratamiento.setPromptText("Nuevo tratamiento");
                    TextField tnuevoReceta = new TextField();
                    tnuevoReceta.setPromptText("Nueva receta");
                    Button guardar = new Button("Guardar cambios");
                    guardar.setOnAction(ev2 -> mensaje.setText(
                            gestorV.modificarConsulta(id, tnuevoTratamiento.getText().trim(), tnuevoReceta.getText().trim())));
                    contenedor.getChildren().addAll(tnuevoTratamiento, tnuevoReceta, guardar);
                } catch (NumberFormatException ex) {
                    mensaje.setText("Ingrese un ID numérico válido");
                }
            });
            contenedor.getChildren().addAll(mensaje, tid, buscar);
        });
        botonC5.setOnAction(e ->{
            Label mensaje = new Label("Ingrese el id para buscar:");
            TextField tid = new TextField();
            tid.setStyle("-fx-font-size: 10");
            tid.setPromptText("ID");
            Label resultado = new Label();
            Button buscar = new Button("Buscar");
            buscar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tid.getText().trim());
                    if(gestorV.buscarConsulta(id)){
                        resultado.setText("Se encontro la consulta: " + gestorV.mostrarUnaConsulta(id));
                    }else{
                        resultado.setText("No existe la consulta");
                    }
                } catch (NumberFormatException ex) {
                    resultado.setText("Ingrese un ID numerico valido");
                }
            });
            contenedor.getChildren().addAll(mensaje, tid, buscar, resultado);
        });
        botonC6.setOnAction(e ->{
            Label mensaje = new Label("Ingrese el id para buscar:");
            TextField tid = new TextField();
            tid.setStyle("-fx-font-size: 10");
            tid.setPromptText("ID");

            Label resultado = new Label();
            Button buscar = new Button("Buscar historial");
            buscar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tid.getText().trim());
                    if(gestorV.buscarMascota(id)){
                        resultado.setText("Se encontro la mascota:" + gestorV.historialClinico(id));
                    }else{
                        resultado.setText("No existe la mascota");
                    }
                } catch (NumberFormatException ex) {
                    resultado.setText("Ingrese un ID numérico válido");
                }
            });
            contenedor.getChildren().addAll(mensaje, tid, buscar, resultado);
        });
        botonVolver.setOnAction(e ->{
            BienvenidaMenu inicio = new BienvenidaMenu();
            inicio.start(stage);
        });
        Scene scena = new Scene(scroll, 750, 500);
        stage.setTitle("Pagina veterinarios");
        stage.setScene(scena);
    }
}
