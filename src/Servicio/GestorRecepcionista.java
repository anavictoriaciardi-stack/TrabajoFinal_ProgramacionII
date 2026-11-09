package Servicio;

import Excepciones.ClienteNoEncontradoException;
import Excepciones.TurnoInexistenteException;
import Modelo.Cliente;
import Modelo.Consulta;
import Modelo.Mascota;
import Modelo.Turno;
import Persistencia.ClienteDAO;
import Persistencia.ConsultaDAO;
import Persistencia.MascotaDAO;
import Persistencia.TurnoDAO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


///Definicion de atributos
public class GestorRecepcionista {
    private ClienteDAO dao_cliente;
    private TurnoDAO dao_turno;
    private MascotaDAO dao_mascotas;
    private ConsultaDAO dao_consulta;
    private Set <Mascota> mascotas;
    private Set <Turno> turnos;
    private Set <Cliente> clientes;
    private Set <Consulta> consultas;

    //faltaria agregarle facturaDAO, creo
    ///Constructor
    public GestorRecepcionista() {
        this.dao_cliente = new ClienteDAO();
        this.dao_turno = new TurnoDAO();
        this.dao_mascotas = new MascotaDAO();
        this.dao_consulta = new ConsultaDAO();
        this.mascotas = new HashSet<>();
        this.turnos = new HashSet<>();
        this.clientes = new HashSet<>();
        this.consultas = new HashSet<>();
    }
    ///---------- TURNOS ---------- ///
    ///AñadirTurno
    public void agregarTurno (Turno turno){
        try {
            dao_turno.insertar(turno);
            turnos.add(turno);
            System.out.println(" El turno se agregó correctamente -");
        } catch (SQLException e){
            System.out.println("Error al agregar el turno. "+ e.getMessage());
        }
    }
    ///EliminarTurno
    public void eliminarTurno(int id) {
        boolean encontro = buscarTurno(id);
        if (encontro) {
            try {
                dao_turno.eliminar(id);
                turnos.removeIf(t -> t.getID() == id);
                System.out.println("Turno eliminado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al eliminar turno. " + e.getMessage());
            }
        }
    }
    //ModificarTurno
    ///BuscarTurno
    public boolean buscarTurno (int id){
        listarTurnos();
        for (Turno t: turnos) {
            if (t.getID() == id) {
                return true;
            }
        }
        return false;
    }
    ///ListarTurnos
    public void listarTurnos (){
        try {
            turnos.clear();
            turnos.addAll(dao_turno.listarTurnos());
        } catch (SQLException e) {
            System.out.println("Error al listar el mensaje" + e.getMessage());
        }
    }
    ///MostrarTurnos
    public void mostrarTurnos (){
        listarTurnos();
        for (Turno t: turnos){
            System.out.println(t);
            System.out.println("--------------------------------------------");
        }
    }
    ///MostrarUnTurno
    public void mostrarUnTurno (int id)throws TurnoInexistenteException {
        boolean encontrar = buscarTurno(id);
        if (encontrar) {
            for (Turno t : turnos) {
                if (t.getID() == id) {
                    System.out.println(t);
                }
            }
        }
        else {
            throw new TurnoInexistenteException(" no hay un turno disponible con ese ID.");

        }
    }
    /// ---------- CLIENTE ---------- ///
    ///AñadirCliente
    public void agregarCliente (Cliente cliente){
        try {
            dao_cliente.insertar(cliente);
            clientes.add(cliente);
            System.out.println(" El cliente se agregó correctamente -");
        } catch (SQLException e){
            System.out.println("Error al agregar el cliente. "+ e.getMessage());
        }
    }
    ///EliminarCliente
    public void eliminarClientes (String dni){
        boolean encontro = buscarCliente(dni);
        if (encontro) {
            try {
                dao_cliente.eliminar(dni);
                clientes.removeIf( c-> c.getDni().equals(dni));
                System.out.println("Cliente eliminado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al eliminar Cliente. " + e.getMessage());
            }
        }
    }
    // agregarle el metodo de buscar cliente por DNI
    //ModificarClientes
    ///ListarCLientes
    public void listarClientes (){
        try {
            clientes.clear();
            clientes.addAll(dao_cliente.listarClientes());
        } catch (SQLException e) {
            System.out.println("Error al listar los clientes. "+e.getMessage());;
        }
    }

    // aca le falta llamar al dao de cliente q tiene el metodo
    ///BuscarCliente
    public boolean buscarCliente (String dni){
        listarClientes();
        for (Cliente c: clientes){
            if (c.getDni().equals(dni)){
                return true;
            }
        }
        return false;
    }
    ///MostrarClientes
    public void mostrarClientes(){
        listarClientes();
        for (Cliente c: clientes){
            System.out.println(c);
            System.out.println("--------------------------------------------");
        }
    }
    ///MostrarunCliente
    public void mostrarUnCliente (String dni) throws ClienteNoEncontradoException {
        listarClientes();
        for (Cliente c: clientes){
            if (c.getDni().equals(dni)){
                System.out.println(c);
            }
            else {
                throw new ClienteNoEncontradoException("No se encontro cliente con ese DNI para mostrar");
            }
        }
    }
    ///MostrarMascotasxCliente
    public void mostrarMascotasxCliente (String dni) throws ClienteNoEncontradoException{
        try {
            mascotas.clear();
            mascotas.addAll(dao_mascotas.listarMascotas());
            for (Mascota m: mascotas){
                if (m.getCliente_dni().equals(dni)){
                    System.out.println(m);
                }
                else {
                    throw new ClienteNoEncontradoException("No se encontro cliente con ese DNI para mostrar");
                }
            }
        } catch (SQLException e){
            System.out.println("Error al listar mascotas."+e.getMessage());
        }

    }
/// ---------- CONSULTA ---------- ///
    ///buscarConsulta
    public double buscarCostoTurno (int id) {
            listarTurnos();
            for (Turno t: turnos) {
                if (t.getID() == id) {
                    return t.getCosto();
                }
            }
            return -1;
    }
    //CobrarConsulta

    /// ---------- SALA DE ESPERA ---------- ///
    //AgregaraSaladeEspera
    //EliminarSaladeEspera
    //MostrarSaladeEspera

}
