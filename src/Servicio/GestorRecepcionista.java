package Servicio;

import Excepciones.ClienteNoEncontradoException;
import Excepciones.TurnoInexistenteException;
import Interfaces.PagoInvalidoException;
import Modelo.*;
import Persistencia.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


///Definicion de atributos
public class GestorRecepcionista {
    private ClienteDAO dao_cliente;
    private TurnoDAO dao_turno;
    private MascotaDAO dao_mascotas;
    private ConsultaDAO dao_consulta;
    private FacturaDAO dao_factura;
    private Set <Mascota> mascotas;
    private Set <Turno> turnos;
    private Set <Cliente> clientes;
    private Set <Consulta> consultas;

    ///Constructor
    public GestorRecepcionista() {
        this.dao_cliente = new ClienteDAO();
        this.dao_turno = new TurnoDAO();
        this.dao_mascotas = new MascotaDAO();
        this.dao_consulta = new ConsultaDAO();
        this.dao_factura= new FacturaDAO();
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
    ///CobrarConsulta
    public void cobrarTurno(Turno t, double monto, String formaPago) throws SQLException, ClienteNoEncontradoException, PagoInvalidoException {

        Cliente cliente = dao_cliente.buscarPorDni(t.getDni_cliente());
        Factura factura = new Factura(cliente,0,formaPago);
        factura.agregarItem("Consulta veterinaria - Turno N° " + t.getID(), t.getCosto());
        String resultado = factura.procesarPago(monto, formaPago);

        dao_factura.insertar(factura);

        System.out.println(resultado);
        System.out.println("Factura generada correctamente.");
        System.out.println(factura);

    }
    public void cobrarTurnoConMedicamentos(Turno t, double monto, String formaPago, List <Medicamento> medicamentos) throws SQLException, ClienteNoEncontradoException, PagoInvalidoException {

        Cliente cliente = dao_cliente.buscarPorDni(t.getDni_cliente());
        Factura factura = new Factura(cliente,0,formaPago);
        factura.agregarItem("Consulta veterinaria - Turno N° " + t.getID(), t.getCosto());
        for (Medicamento m: medicamentos){
            factura.agregarItem("Medicamento"+m.getNombre(), m.getPrecio());
        }
        String resultado = factura.procesarPago(monto, formaPago);

        dao_factura.insertar(factura);

        System.out.println(resultado);
        System.out.println("Factura generada correctamente.");
        System.out.println(factura);

    }

    /// ---------- SALA DE ESPERA ---------- ///

    //AgregaraSaladeEspera
    //EliminarSaladeEspera
    //MostrarSaladeEspera

}
