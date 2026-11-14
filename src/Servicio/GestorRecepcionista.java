package Servicio;

import Excepciones.ClienteNoEncontradoException;
import Excepciones.TurnoInexistenteException;
import Interfaces.PagoInvalidoException;
import Modelo.*;
import Persistencia.*;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.*;


///Definicion de atributos
public class GestorRecepcionista {
    private ClienteDAO dao_cliente;
    private TurnoDAO dao_turno;
    private MascotaDAO dao_mascotas;
    private FacturaDAO dao_factura;
    private Set <Mascota> mascotas;
    private Set <Turno> turnos;
    private Set <Cliente> clientes;
    private Map<Integer, SaladeEspera> mapa;

    ///Constructor
    public GestorRecepcionista() {
        this.dao_cliente = new ClienteDAO();
        this.dao_turno = new TurnoDAO();
        this.dao_mascotas = new MascotaDAO();
        this.dao_factura= new FacturaDAO();
        this.mascotas = new HashSet<>();
        this.turnos = new HashSet<>();
        this.clientes = new HashSet<>();
        this.mapa= new HashMap<>();
    }
    ///---------- TURNOS ---------- ///
    ///AñadirTurno
    public String agregarTurno (Turno turno){
        String mensaje = "";
        try {
            dao_turno.insertar(turno);
            turnos.add(turno);
            mensaje = " El turno se agregó correctamente -";
        } catch (SQLException e){
            mensaje = "Error al agregar el turno. "+ e.getMessage();
        }
        return mensaje;
    }
    ///EliminarTurno
    public String eliminarTurno(int id) {
        boolean encontro = buscarTurno(id);
        String mensaje = "";
        if (encontro) {
            try {
                dao_turno.eliminar(id);
                turnos.removeIf(t -> t.getID() == id);
                mensaje = "Turno eliminado correctamente";
            } catch (SQLException e) {
                mensaje = "Error al eliminar turno. " + e.getMessage();
            }finally{
                return mensaje;
            }
        }else{
            mensaje = "Turno no encontrado";
        }
        return mensaje;
    }
    ///ModificarTurno
    public String modificarTurno (int id, Date fecha, Time hora){
        String mensaje = "";
        boolean encontro = buscarTurno(id);
        if (encontro) {
            try {
                dao_turno.modificarTurno(id,fecha,hora);
                listarTurnos();
                mensaje =  "Turno Modificado correctamente";
            } catch (SQLException e) {
                mensaje = "Error al modificar el turno. "+e.getMessage();
            }
        }else{
            mensaje = "Turno no encontrado";
        }
        return mensaje;
    }
    ///BuscarTurnoxID
    public boolean buscarTurno (int id){
        listarTurnos();
        for (Turno t: turnos) {
            if (t.getID() == id) {
                return true;
            }
        }
        return false;
    }
    ///buscarTurnoxdni
    public boolean buscarTurnoxdni (String dni){
        listarTurnos();
        for (Turno t: turnos) {
            if (t.getDni_cliente() == dni) {
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
    public String mostrarTurnos (){
        listarTurnos();
        String mensaje = "";
        for (Turno t: turnos){
            mensaje += t.toString();
            mensaje += "--------------------------------------------";
        }
        return mensaje;
    }
    ///MostrarUnTurno
    public String mostrarUnTurno (String dni)throws TurnoInexistenteException {
        boolean encontrar = buscarTurnoxdni(dni);
        String mensaje = "";
        if (encontrar) {
            for (Turno t : turnos) {
                if (t.getDni_cliente() == dni) {
                    mensaje = t.toString();
                }
            }
        }
        else {
            throw new TurnoInexistenteException(" no hay un turno disponible con ese ID.");

        }
        return mensaje;
    }
    /// ---------- CLIENTE ---------- ///
    ///AñadirCliente
    public String agregarCliente (Cliente cliente){
        String mensaje = "";
        try {
            dao_cliente.insertar(cliente);
            clientes.add(cliente);

            if(clientes.contains(cliente)){
                mensaje += " El cliente se agregó correctamente -";
            }else{
                mensaje += " El cliente no se agrego correctamente -";
            }
        } catch (SQLException e){
            mensaje += "Error al agregar el cliente. "+ e.getMessage();
        }
        return mensaje;
    }
    ///EliminarCliente
    public boolean eliminarClientes (String dni){
        boolean encontro = buscarCliente(dni);

        if (encontro) {
            try {
                dao_cliente.eliminar(dni);
                clientes.removeIf( c-> c.getDni().equals(dni));

            } catch (SQLException e) {
                System.out.println("Error al eliminar Cliente. " + e.getMessage());
            }
        }else{
            return false;
        }
        if(clientes.contains(buscarCliente(dni))){
            return true;
        }else{
            return false;
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
    public String mostrarClientes(){
        listarClientes();
        String mensaje = "";
        for (Cliente c: clientes){
            mensaje += c.toString();
            mensaje += "--------------------------------------------";
        }
        return mensaje;
    }
    ///MostrarunCliente
    public String mostrarUnCliente (String dni) throws ClienteNoEncontradoException {
        listarClientes();
        String mensaje = "";
        for (Cliente c: clientes){
            if (c.getDni().equals(dni)){
                mensaje = c.toString();
            }
            else {
                throw new ClienteNoEncontradoException("No se encontro cliente con ese DNI para mostrar");
            }
        }
        return mensaje;
    }
    ///MostrarMascotasxCliente
    public String mostrarMascotasxCliente (String dni) throws ClienteNoEncontradoException{
        String mensaje = "";
        try {
            mascotas.clear();
            mascotas.addAll(dao_mascotas.listarMascotas());
            for (Mascota m: mascotas){
                if (m.getCliente_dni().equals(dni)){
                    mensaje = m.toString();
                }
                else {
                    throw new ClienteNoEncontradoException("No se encontro cliente con ese DNI para mostrar");
                }
            }
        } catch (SQLException e){
            mensaje = "Error al listar mascotas."+e.getMessage();
        }
        return mensaje;
    }
    /// ---------- CONSULTA ---------- ///
    ///CobrarConsultaSola
    public String cobrarTurno(Turno t, double monto, String formaPago) throws SQLException, ClienteNoEncontradoException, PagoInvalidoException {
        String mostrarFactura = "";
        Cliente cliente = dao_cliente.buscarPorDni(t.getDni_cliente());
        Factura factura = new Factura(cliente,0,formaPago);
        factura.agregarItem("Consulta veterinaria - Turno N° " + t.getID(), t.getCosto());
        String resultado = factura.procesarPago(monto, formaPago);

        dao_factura.insertar(factura);

        mostrarFactura += resultado;
        mostrarFactura = "Factura generada correctamente.";
        mostrarFactura += eliminarTurno(t.getID());
        mostrarFactura += factura.toString();
        return mostrarFactura;
    }
    ///Cobrar consulta con Medicamentos
    public String cobrarTurnoConMedicamentos(Turno t, double monto, String formaPago, List <Medicamento> medicamentos) throws SQLException, ClienteNoEncontradoException, PagoInvalidoException {
        String mostrar = "";
        Cliente cliente = dao_cliente.buscarPorDni(t.getDni_cliente());
        Factura factura = new Factura(cliente,0,formaPago);
        factura.agregarItem("Consulta veterinaria - Turno N° " + t.getID(), t.getCosto());
        for (Medicamento m: medicamentos){
            factura.agregarItem("Medicamento"+m.getNombre(), m.getPrecio());
        }
        String resultado = factura.procesarPago(monto, formaPago);

        dao_factura.insertar(factura);

        mostrar += resultado;
        mostrar = "Factura generada correctamente.";
        mostrar += eliminarTurno(t.getID());
        mostrar += factura.toString();

        return mostrar;
    }
    ///CobrarMedicamentos
    public String cobrarMedicamentos(String dni, String formaPago, List <Medicamento> medicamentos) throws SQLException, ClienteNoEncontradoException, PagoInvalidoException {
        String mostrar = "";
        Cliente cliente = dao_cliente.buscarPorDni(dni);
        Factura factura = new Factura(cliente,0,formaPago);
        for (Medicamento m: medicamentos){
            factura.agregarItem("Medicamento"+m.getNombre(), m.getPrecio());
        }
        String resultado = factura.procesarPago(factura.getMontoTotal(), formaPago);

        dao_factura.insertar(factura);

        mostrar += resultado;
        mostrar += "Factura generada correctamente.";
        mostrar += factura.toString();
        return mostrar;
    }
    /// ---------- VETERINARIO ------------ ///
    public String listaryMostrarVeterinarios () throws SQLException{
        List <Veterinario> veterinarios = new ArrayList<>();
        VeterinarioDAO dao_veterinario = new VeterinarioDAO();
        veterinarios.addAll(dao_veterinario.listarVeterinarios());
        String mostrar = "";
        for (Veterinario v: veterinarios){
            mostrar += "Nombre: " + v.getNombre() + " "+ v.getApellido();
            mostrar += "Matricula: "+ v.getMatricula();
            mostrar += "----------------------------------------------------------";
        }
        return mostrar;
    }
    /// ---------- SALA DE ESPERA ---------- ///

    ///AgregaraSaladeEspera
    public String AgregarSaladeEspera(int id, SaladeEspera sala) {
        String mostrar = "";
        if (!mapa.containsKey(id)) {
            mapa.put(id, sala);
            mostrar += "Paciente esperando";
        } else {
            mostrar += "Ya existe un paciente esperando con ese ID";
        }
        return mostrar;
    }
    ///EliminarSaladeEspera
    public String EliminarSaladeEspera(int id) {
        String mensaje = "";
        if (mapa.containsKey(id)) {
            mapa.remove(id);
            mensaje += "El paciente se fue";
        } else {
            mensaje += "No existe una sala de espera con ese ID";
        }
        return mensaje;
    }
    ///MostrarSaladeEspera
    public String mostrarSalasDeEspera() {
        String mostrar = "";
        for (Map.Entry<Integer, SaladeEspera> entrada : mapa.entrySet()) {
            mostrar += "ID: " + entrada.getKey();
            mostrar += entrada.getValue().toString();
        }
        return mostrar;
    }
    ///MostrarPorVeterinarioxVeterinario
    public String mostrarSalaxVeterinario(int matricula) {
        String mostrar = "";
        for (Map.Entry<Integer, SaladeEspera> entrada : mapa.entrySet()) {
            if (entrada.getValue().getMatriculaVet() == matricula) {
                mostrar +="ID: " + entrada.getKey();
                mostrar += entrada.getValue().toString();
            }
        }
        return mostrar;
    }
    ///HacerPasar
    public String hacerPasar(int matricula) {
        String mensaje = "";
        for (Map.Entry<Integer, SaladeEspera> entrada : mapa.entrySet()) {
            if (entrada.getValue().getMatriculaVet() == matricula) {
                mensaje+="Paciente que pasa a consulta:";
                mensaje += entrada.getValue().toString();

                EliminarSaladeEspera(entrada.getKey());
                return mensaje;
            }
        }

        mensaje += "No hay pacientes esperando para ese veterinario.";
        return mensaje;
    }
}
