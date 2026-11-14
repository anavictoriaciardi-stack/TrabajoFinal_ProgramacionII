package Servicio;

import Modelo.*;
import Persistencia.FacturaDAO;
import Persistencia.UsuarioDAO;
import Persistencia.VeterinarioDAO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GestorAdministrador {
    private VeterinarioDAO dao_veterinario;
    private FacturaDAO dao_facturas;
    private UsuarioDAO dao_usuarios;
    private Set <Veterinario> veterinarios;
    private Set <Factura> facturas;
    private Set <Usuario> usuarios;

    public GestorAdministrador() {
        this.dao_veterinario = new VeterinarioDAO();
        this.dao_facturas = new FacturaDAO();
        this.dao_usuarios= new UsuarioDAO();
        this.veterinarios = new HashSet<>();
        this.facturas = new HashSet<>();
        this.usuarios= new HashSet<>();

    }

    /// ---------- VETERINARIOS ---------- ///

    ///AñadirVeterinario
    public String agregarVeterinario (Veterinario vete) throws NullPointerException{
        String mensaje = "";
        try{

            if (vete != null){
                try {
                    dao_veterinario.insertar(vete);
                    veterinarios.add(vete);
                    mensaje = "Se agrego el veterinario correctamente.";
                } catch (SQLException e) {
                    System.err.println("Error al agregar el veterinario. " + e.getMessage());
                }
            }else{
                mensaje = "El veterinario no existe -";
                throw new NullPointerException("El veterinario no existe -");
            }
        }catch(NullPointerException e){
            mensaje += e.getMessage();
            e.printStackTrace();
        }finally{
            return mensaje;
        }
    }
    ///EliminarVeterinario
    public void eliminarVeterinario(int matricula) {
        boolean encontro = buscarVeterinario(matricula);
        if (encontro) {
            try {
                dao_veterinario.eliminar(matricula);
                veterinarios.removeIf(v -> v.getMatricula() == matricula);
                System.out.println("Turno eliminado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al eliminar turno. " + e.getMessage());
            }
        }
    }
    ///ModificarVetrinariveterinario
    public void modificarVeterinario(int matricula, String especialidad){
        boolean encontro = buscarVeterinario(matricula);
        if (encontro) {
            try {
                dao_veterinario.modificar(matricula,especialidad);
                listarVeterinarios();
            } catch (SQLException e) {
                System.out.println("Error al modificar el Vetrinario. "+e.getMessage());
            }
        }
    }
    ///ListarVetrinarios
    public void listarVeterinarios(){
        try {
            veterinarios.clear();
            veterinarios.addAll(dao_veterinario.listarVeterinarios());
        } catch (SQLException e) {
            System.out.println("Error al listar los veterinarios" + e.getMessage());
        }
    }
    ///mostrarVeterinarios
    public void mostrarVeterinarios(){
        listarVeterinarios();
        for (Veterinario v: veterinarios){
            System.out.println(v);
            System.out.println("--------------------------------------------");
        }
    }
    ///mostrarunvetrinario
    public void mostrarUnVeterinario (int matricula) {
        boolean encontrar = buscarVeterinario(matricula);
        if (encontrar) {
            for (Veterinario v : veterinarios) {
                if (v.getMatricula() == matricula) {
                    System.out.println(v);
                }
            }
        }
        else {
            System.out.println("No hay Veterinario con esa Matricula");

        }
    }
    ///BuscarVetrinario
    public boolean buscarVeterinario (int matricula){
        listarVeterinarios();
        for (Veterinario v: veterinarios) {
            if (v.getMatricula() == matricula) {
                return true;
            }
        }
        return false;
    }

    /// ---------- CONTABILIDAD ---------- ///
    ///Verfacturas
    public void VerFacturas () throws SQLException{
            facturas.addAll(dao_facturas.listarActivas());
            for (Factura f: facturas){
                System.out.println(f);
            }
        }
    ///ModificarFactura
    public void ModificarFactura(Factura factura) throws SQLException {
        dao_facturas.actualizar(factura);
        facturas.addAll(dao_facturas.listarActivas());
    }
    /// Dar de baja una factura
    public void darDeBaja(int id) throws SQLException {
        Factura factura = null;
        for (Factura f : facturas) {
            if (f.getId() == id) {
                factura = f;
                break;
            }
        }
        if (factura != null) {
            dao_facturas.darDeBaja(id);
            facturas.remove(factura);
        }
    }

    /// ---------- CREACION DE USUARIOS ---------- ///
    ///Crear Usuario
    public boolean CrearUsuario (Usuario usuario){
            try {
                listarUsuarios();
                for (Usuario u: usuarios){
                    if (u.getUsername().equals(usuario.getUsername())){
                        System.out.println("Usuario ya existe.");
                        return false;
                    }
                    else {
                        if (u.getPassword().equals(usuario.getPassword())){
                            System.out.println("Contraseña Invalida");
                            return false;
                        }
                        else{
                            dao_usuarios.insertar(usuario);
                            usuarios.add(usuario);
                            System.out.println(" El Usuario de veterinario se agregó correctamente -");
                        }
                    }
                }

            } catch (SQLException e){
                System.out.println("Error al agregar el usuario. "+ e.getMessage());
            }
        return true;
        }
    ///Listar Usuario
    public void listarUsuarios(){
        try {
            usuarios.clear();
            usuarios.addAll(dao_usuarios.listar());
        } catch (SQLException e) {
            System.out.println("Error al listar los Usuarios" + e.getMessage());
        }
    }
    ///Eliminar Usuario
    public void eliminarUsuario (int id){
        boolean encontro = buscarVeterinario(id);
        if (encontro) {
            try {
                dao_usuarios.eliminar(id);
                usuarios.removeIf(u -> u.getId() == id);
                System.out.println("Usuario eliminado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al eliminar el usuario. " + e.getMessage());
            }
        }
    }
    ///Modificar Usuario
    public void modificarUsuario (Usuario usuario){
        boolean encontro = buscarUsuario(usuario.getId());
        if (encontro) {
            try {
                dao_usuarios.modificarUsuario(usuario);
                listarUsuarios();
            } catch (SQLException e) {
                System.out.println("Error al modificar el Usuario. "+e.getMessage());
            }
        }
    }
    ///Buscar Usuario
    public boolean buscarUsuario (int id){
        listarUsuarios();
        for (Usuario c: usuarios) {
            if (c.getId() == id) {
                return true;
            }
        }
        return false;
    }
    }
