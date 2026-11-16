package appui;

import Modelo.Veterinario;
import Modelo.Usuario;
import Modelo.TIPOdeUSUARIO;
import Servicio.GestorAdministrador;
import Persistencia.UsuarioDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AdministradorUi {
    private GestorAdministrador gestor = new GestorAdministrador();

    public AdministradorUi(GestorAdministrador gestor) {
        this.gestor = gestor;
    }

    public AdministradorUi() {
    }
    private TextField crearCampo(String placeholder) {
        TextField tf = new TextField();
        tf.setStyle("-fx-font-size: 11px;");
        tf.setPromptText(placeholder);
        return tf;
    }
    public void mostrarA (Stage stage){
        ///Botones de presentaciones
        Label titulo = new Label("Bienvenido administrador/a\n");
        titulo.setStyle("-fx-font-size: 20");
        Label subtitulo = new Label("Acciones Veterinarios");
        subtitulo.setStyle("-fx-font-size: 15");
        //botones de accion veterinarios
        Button mostrarVeterinarios = new Button("Mostrar Veterinarios");
        mostrarVeterinarios.setStyle("-fx-font-size: 10");
        Button agregarVeterinarios = new Button("Agregar Veterinarios");
        agregarVeterinarios.setStyle("-fx-font-size: 10");
        Button eliminarVeterinarios = new Button("Eliminar Veterinarios");
        eliminarVeterinarios.setStyle("-fx-font-size: 10");
        Button modificarVeterinarios = new Button("Modificar veterinarios");
        modificarVeterinarios.setStyle("-fx-font-size: 10");
        Button buscarVeterinarios = new Button("Buscar Veterinario");
        buscarVeterinarios.setStyle("-fx-font-size: 10");
        //botones de accion contabilidad
        Label mensaje2 = new Label("Acciones contabilidad");
        mensaje2.setStyle("-fx-font-size: 15");
        Button verFacturas = new Button("Ver facturas");
        verFacturas.setStyle("-fx-font-size: 10");
        Button modificarFactura = new Button("Modificar factura");
        modificarFactura.setStyle("-fx-font-size: 10");
        Button eliminarFactura = new Button ("Dar de baja la factura");
        eliminarFactura.setStyle("-fx-font-size: 10");
        //crear usuarios
        Label mensaje3 = new Label("Acciones contabilidad");
        mensaje3.setStyle("-fx-font-size: 15");
        Button agregarUsuario = new Button("Agregar usuario");
        agregarUsuario.setStyle("-fx-font-size: 10");
        Button eliminarUsuario = new Button("Eliminar usuario");
        eliminarUsuario.setStyle("-fx-font-size: 10");
        Button modificarUsuario = new Button("Modificar usuario");
        modificarUsuario.setStyle("-fx-font-size: 10");
        Button buscarUsuario = new Button("Buscar usuario");
        buscarUsuario.setStyle("-fx-font-size: 10");
        //volver al inicio
        Button volver = new Button("Volver al menu principal");
        volver.setStyle("-fx-font-size: 10");

        VBox contenedor = new VBox(10, titulo, subtitulo, mostrarVeterinarios,
                agregarVeterinarios,
                buscarVeterinarios, eliminarVeterinarios, modificarVeterinarios,
                mensaje2, verFacturas,
                eliminarFactura, modificarFactura, mensaje3
                , modificarUsuario, buscarUsuario,
                agregarUsuario, eliminarUsuario, volver);
        ScrollPane scroll = new ScrollPane(contenedor);
        scroll.setFitToWidth(true);

        ///Acciones de botones veterinarios
        mostrarVeterinarios.setOnAction(e -> {
            Label mensaje = new Label(gestor.mostrarVeterinarios());
            mensaje.setStyle("-fx-font-size: 10");

            contenedor.getChildren().add(mensaje);
        });
        agregarVeterinarios.setOnAction(e -> {
//            protected String dni;
//            protected String nombre;
//            protected String apellido;
//            protected String telefono;
//            protected String email;
//            private int matricula;
//            private String especialidad;

            TextField tDni = crearCampo("DNI:");
            TextField tNombre = crearCampo("Nombre:");
            TextField tApellido = crearCampo("Apellido:");
            TextField tTelefono = crearCampo("Telefono:");
            TextField tEmail = crearCampo("Email:");
            TextField tMatricula = crearCampo("Matricula:");
            TextField tEspecialidad = crearCampo("Especialidad:");

            Label mensaje = new Label();
            Button btnRegistrar = new Button("Registrar Veterinario");
            btnRegistrar.setStyle("-fx-font-size: 10");

            btnRegistrar.setOnAction(e2->{
                String dni = tDni.getText().trim();
                String  nombre = tNombre.getText().trim();
                String  apellido = tApellido.getText().trim();
                String  telefono = tTelefono.getText().trim();
                String  email = tEmail.getText().trim();
                int  matricula = Integer.parseInt(tMatricula.getText().trim());
                String  especialidad = tEspecialidad.getText().trim();

                if (nombre.isEmpty() || dni.isEmpty() || telefono.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error: Completa todos los campos obligatorios.");
                    return;
                }
                try {
                    Veterinario aux = new Veterinario(dni, nombre, apellido, telefono, email, matricula, especialidad);
                    tDni.clear();
                    tNombre.clear();
                    tApellido.clear();
                    tTelefono.clear();
                    tEmail.clear();
                    tMatricula.clear();
                    tEspecialidad.clear();

                    mensaje.setStyle("-fx-text-fill: green;");
                    mensaje.setText("Mascota registrada exitosamente");
                }catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error: La edad debe ser un número válido.");
                }
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(8);
                grid.addRow(0, new Label("DNI:"), tDni);
                grid.addRow(1, new Label("Nombre:"), tNombre);
                grid.addRow(2, new Label("Apellido:"), tApellido);
                grid.addRow(3, new Label("Telefono:"), tTelefono);
                grid.addRow(4, new Label("Email:"), tEmail);
                grid.addRow(5, new Label("Matricula:"), tMatricula);
                grid.addRow(6, new Label("Especialidad:"), tEspecialidad);

                contenedor.getChildren().addAll(new Label("Registro de veterinario"), grid, btnRegistrar, mensaje);

            });
        });
        buscarVeterinarios.setOnAction(e -> {
                Label mensaje = new Label();
                TextField tMatricula = crearCampo("Matricula:");

                int matricula = Integer.parseInt(tMatricula.getText().trim());
                if(gestor.buscarVeterinario(matricula)){
                    mensaje.setStyle("-fx-text-fill: 10;");
                    mensaje.setText("Veterinario buscado exitosamente: " + gestor.mostrarUnVeterinario(matricula));
                }else{
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Veterinario no existe");
                }
                tMatricula.clear();
                contenedor.getChildren().addAll(mensaje);
        });
        eliminarVeterinarios.setOnAction(e -> {
            Label mensaje = new Label();
            TextField tMatricula = crearCampo("Matricula:");

            int matricula = Integer.parseInt(tMatricula.getText().trim());
            if(gestor.buscarVeterinario(matricula)){
                if(gestor.eliminarVeterinario(matricula)){
                    mensaje.setText("Veterinario eliminado exitosamente");
                }else{
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Veterinario no se pudo eliminar");
                }
            }else{
                mensaje.setStyle("-fx-text-fill: red;");
                mensaje.setText("Veterinario no existe");
            }
            tMatricula.clear();
            contenedor.getChildren().addAll(mensaje);
        });
        modificarVeterinarios.setOnAction(e -> {
            Label mensaje = new Label();
            TextField tMatricula = crearCampo("Matricula:");

            int matricula = Integer.parseInt(tMatricula.getText().trim());
            if(gestor.buscarVeterinario(matricula)){
                TextField tEspecialidad = crearCampo("Especialidad:");
                tEspecialidad.setStyle("-fx-text-fill: 10;");
                String especialidad = tEspecialidad.getText().trim();
                tEspecialidad.clear();
                mensaje.setText(gestor.modificarVeterinario(matricula, especialidad));
            }else{
                mensaje.setStyle("-fx-text-fill: red;");
                mensaje.setText("Veterinario no existe");
            }
            tMatricula.clear();
            contenedor.getChildren().addAll(mensaje);
        });

        ///Acciones contabilidad
        verFacturas.setOnAction(e -> {
            Label mensaje = new Label();
            try {
                mensaje.setText(gestor.VerFacturas());
                mensaje.setStyle("-fx-font-size: 10");
            }catch(SQLException ex) {
                mensaje.setStyle("-fx-text-fill: red;");
                mensaje.setText("Error al obtener las facturas");
            }
            contenedor.getChildren().add(mensaje);
        });
        modificarFactura.setOnAction(e ->{
            TextField tId = crearCampo("ID de factura:");
            TextField tMonto = crearCampo("Nuevo monto total:");
            TextField tFormaPago = crearCampo("Nueva forma de pago:");
            Label mensaje = new Label();
            Button guardar = new Button("Guardar cambios");

            guardar.setOnAction(ev -> {
                String idTexto = tId.getText().trim();
                String montoTexto = tMonto.getText().trim();
                String formaPago = tFormaPago.getText().trim();

                if (idTexto.isEmpty() || montoTexto.isEmpty() || formaPago.isEmpty()) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Completa todos los campos.");
                    return;
                }

                try {
                    int id = Integer.parseInt(idTexto);
                    double monto = Double.parseDouble(montoTexto.replace(',', '.'));
                    if (id <= 0 || monto < 0) {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("El ID debe ser positivo y el monto no puede ser negativo.");
                        return;
                    }

                    Modelo.Factura factura = new Modelo.Factura();
                    factura.setId(id);
                    factura.setMontoTotal(monto);
                    factura.setFormaPago(formaPago);
                    gestor.ModificarFactura(factura);

                    mensaje.setStyle("-fx-text-fill: green;");
                    mensaje.setText("Factura modificada correctamente.");
                } catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Ingresá un ID entero y un monto numérico válido.");
                } catch (SQLException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("No se pudo modificar la factura: " + ex.getMessage());
                }
            });

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(8);
            grid.addRow(0, new Label("ID de factura:"), tId);
            grid.addRow(1, new Label("Nuevo monto total:"), tMonto);
            grid.addRow(2, new Label("Nueva forma de pago:"), tFormaPago);
            contenedor.getChildren().addAll(new Label("Modificar factura"), grid, guardar, mensaje);
        });
        eliminarFactura.setOnAction(e -> {
            TextField tId = crearCampo("ID de factura:");
            Label mensaje = new Label();
            Button confirmar = new Button("Dar de baja");

            confirmar.setOnAction(ev -> {
                String idTexto = tId.getText().trim();
                if (idTexto.isEmpty()) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Ingresá el ID de la factura.");
                    return;
                }

                try {
                    int id = Integer.parseInt(idTexto);
                    if (id <= 0) {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("El ID debe ser positivo.");
                        return;
                    }

                    if (gestor.darDeBaja(id)) {
                        mensaje.setStyle("-fx-text-fill: green;");
                        mensaje.setText("Factura dada de baja correctamente.");
                    } else {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("No se encontró una factura activa con ese ID.");
                    }
                } catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("El ID debe ser un número entero válido.");
                } catch (SQLException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("No se pudo dar de baja la factura: " + ex.getMessage());
                }
            });

            GridPane formulario = new GridPane();
            formulario.setHgap(10);
            formulario.setVgap(8);
            formulario.addRow(0, new Label("ID de factura:"), tId);
            contenedor.getChildren().addAll(new Label("Dar de baja factura"), formulario, confirmar, mensaje);
        });

        ///Acciones con usuarios
        modificarUsuario.setOnAction(e -> {
            TextField tId = crearCampo("ID de usuario:");
            TextField tNombre = crearCampo("Nombre de usuario:");
            TextField tContrasena = crearCampo("Contraseña:");
            ComboBox<TIPOdeUSUARIO> tRol = new ComboBox<>();
            tRol.getItems().setAll(TIPOdeUSUARIO.values());
            tRol.setPromptText("Seleccioná un rol");

            Label mensaje = new Label();
            Button guardar = new Button("Guardar cambios");
            guardar.setOnAction(ev -> {
                String idIngresado = tId.getText().trim();
                String nombre = tNombre.getText().trim();
                String contrasena = tContrasena.getText().trim();
                TIPOdeUSUARIO rol = tRol.getValue();

                if (idIngresado.isEmpty() || nombre.isEmpty() || contrasena.isEmpty() || rol == null) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Completá todos los campos y seleccioná un rol.");
                    return;
                }

                try {
                    int id = Integer.parseInt(idIngresado);
                    if (id <= 0) {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("El ID debe ser un número positivo.");
                        return;
                    }

                    Usuario usuario = new Usuario(id, nombre, contrasena, rol);
                    boolean modificado = gestor.modificarUsuario(usuario);
                    if (modificado) {
                        mensaje.setStyle("-fx-text-fill: green;");
                        mensaje.setText("Usuario modificado correctamente.");
                    } else {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("No se encontró el usuario o no se pudo modificar.");
                    }
                } catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("El ID debe ser un número entero válido.");
                }
            });

            GridPane formulario = new GridPane();
            formulario.setHgap(10);
            formulario.setVgap(8);
            formulario.addRow(0, new Label("ID de usuario:"), tId);
            formulario.addRow(1, new Label("Nombre de usuario:"), tNombre);
            formulario.addRow(2, new Label("Contraseña:"), tContrasena);
            formulario.addRow(3, new Label("Rol:"), tRol);
            contenedor.getChildren().addAll(new Label("Modificar usuario"), formulario, guardar, mensaje);
        });
        buscarUsuario.setOnAction(e -> {
            TextField tId = crearCampo("ID de usuario:");
            Label mensaje = new Label();
            Button buscar = new Button("Buscar");

            buscar.setOnAction(ev -> {
                String idIngresado = tId.getText().trim();
                if (idIngresado.isEmpty()) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Ingresá el ID del usuario.");
                    return;
                }

                try {
                    int id = Integer.parseInt(idIngresado);
                    if (id <= 0) {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("El ID debe ser un número positivo.");
                        return;
                    }

                    Usuario usuarioEncontrado = new UsuarioDAO().listar().stream()
                            .filter(usuario -> usuario.getId() == id)
                            .findFirst()
                            .orElse(null);
                    if (usuarioEncontrado != null) {
                        mensaje.setStyle("-fx-text-fill: green;");
                        mensaje.setText("Usuario encontrado:\nID: " + usuarioEncontrado.getId()
                                + "\nNombre: " + usuarioEncontrado.getUsername()
                                + "\nRol: " + usuarioEncontrado.getRol());
                    } else {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("No se encontró un usuario con el ID " + id );
                    }
                } catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("El ID debe ser un número entero válido");
                }catch(SQLException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error mySQL");
                }
            });

            GridPane formulario = new GridPane();
            formulario.setHgap(10);
            formulario.setVgap(8);
            formulario.addRow(0, new Label("ID de usuario:"), tId);
            contenedor.getChildren().addAll(new Label("Buscar usuario"), formulario, buscar, mensaje);
        });
        agregarUsuario.setOnAction(e -> {
            TextField tNombre = crearCampo("Nombre de usuario:");
            TextField tContrasena = crearCampo("Contraseña:");
            ComboBox<TIPOdeUSUARIO> tRol = new ComboBox<>();
            tRol.getItems().setAll(TIPOdeUSUARIO.values());
            tRol.setPromptText("Seleccioná un rol");
            Label mensaje = new Label();
            Button guardar = new Button("Agregar usuario");

            guardar.setOnAction(ev -> {
                String nombre = tNombre.getText().trim();
                String contrasena = tContrasena.getText().trim();
                TIPOdeUSUARIO rol = tRol.getValue();
                if (nombre.isEmpty() || contrasena.isEmpty() || rol == null) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Completá todos los campos.");
                    return;
                }

                Usuario usuario = new Usuario();
                usuario.setUsername(nombre);
                usuario.setPassword(contrasena);
                usuario.setRol(rol);
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    boolean nombreEnUso = dao.listar().stream()
                            .anyMatch(existente -> existente.getUsername().equalsIgnoreCase(nombre));
                    if (nombreEnUso) {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("Ese nombre de usuario ya está en uso.");
                    } else if (dao.insertar(usuario)) {
                        mensaje.setStyle("-fx-text-fill: green;");
                        mensaje.setText("Usuario agregado correctamente.");
                    } else {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("No se pudo agregar el usuario.");
                    }
                } catch (SQLException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error al agregar el usuario: " + ex.getMessage());
                }
            });

            GridPane formulario = new GridPane();
            formulario.setHgap(10);
            formulario.setVgap(8);
            formulario.addRow(0, new Label("Nombre de usuario:"), tNombre);
            formulario.addRow(1, new Label("Contraseña:"), tContrasena);
            formulario.addRow(2, new Label("Rol:"), tRol);
            contenedor.getChildren().addAll(new Label("Agregar usuario"), formulario, guardar, mensaje);
        });
        eliminarUsuario.setOnAction(e -> {
            TextField tId = crearCampo("ID de usuario:");
            Label mensaje = new Label();
            Button eliminar = new Button("Eliminar usuario");

            eliminar.setOnAction(ev -> {
                try {
                    int id = Integer.parseInt(tId.getText().trim());
                    if (id <= 0) {
                        mensaje.setStyle("-fx-text-fill: red;");
                        mensaje.setText("El ID debe ser positivo.");
                        return;
                    }
                    boolean eliminado = new UsuarioDAO().eliminar(id);
                    mensaje.setStyle(eliminado ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                    mensaje.setText(eliminado ? "Usuario eliminado correctamente." : "No se encontró el usuario.");
                } catch (NumberFormatException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Ingresá un ID entero válido.");
                } catch (SQLException ex) {
                    mensaje.setStyle("-fx-text-fill: red;");
                    mensaje.setText("Error al eliminar el usuario: " + ex.getMessage());
                }
            });

            GridPane formulario = new GridPane();
            formulario.setHgap(10);
            formulario.setVgap(8);
            formulario.addRow(0, new Label("ID de usuario:"), tId);
            contenedor.getChildren().addAll(new Label("Eliminar usuario"), formulario, eliminar, mensaje);
        });
        volver.setOnAction(e->{
            BienvenidaMenu inicio = new BienvenidaMenu();
            inicio.start(stage);
        });
        Scene scena = new Scene(scroll, 750, 500);
        stage.setTitle("Zona administradores");
        stage.setScene(scena);
        stage.show();
    }
}
