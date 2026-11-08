package Servicio;

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

public class GestorRecepcionista {
    private ClienteDAO dao_cliente;
    private TurnoDAO dao_turno;
    private MascotaDAO dao_mascotas;
    private ConsultaDAO dao_consulta;
    private Set <Mascota> mascotas;
    private Set <Turno> turnos;
    private Set <Cliente> clientes;
    private Set <Consulta> consultas;

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

    ///AñadirTurno
    public void AgregarTurno (Turno turno){
        try {
            dao_turno.insertar(turno);
            turnos.add(turno);
            System.out.println(" El turno se agregó correctamente -");
        } catch (SQLException e){
            System.out.println("Error al agregar el turno. "+ e.getMessage());
        }
    }
    ///EliminarTurno
    public void EliminarTurno(int id) {
        boolean encontro = BuscarTurno(id);
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
    public boolean BuscarTurno (int id){
        ListarTurnos();
        Boolean encontro = false;
        for (Turno t: turnos) {
            if (t.getID() == id) {
                encontro = true;
            }
        }
        return encontro;
    }
    ///ListarTurnos solo para listar
    public void ListarTurnos (){
        try {
            turnos.clear();
            turnos.addAll(dao_turno.listarTurnos());
        } catch (SQLException e) {
            System.out.println("Error al listar el mensaje" + e.getMessage());
        }
    }
    ///MostrarTurnos
    public void MostrarTurnos (){
        ListarTurnos();
        for (Turno t: turnos){
            System.out.println(t);
        }
    }
    ///MostrarUnTurno
    public void MostrarUnTurno (int id) {
        boolean encontrar = BuscarTurno(id);
        if (encontrar) {
            for (Turno t : turnos) {
                if (t.getID() == id) {
                    System.out.println(t);
                }
            }
        }
        else {
            //crear excepcion que diga que no se encontro/no existe
            System.out.println(" ");
        }
    }

    ///AñadirCliente
    ///EliminarCliente
    ///ModificarClientes
    ///ListarCLientes
    ///BuscarCliente
    ///MostrarMascotasxCliente

    ///buscarConsulta
    //CobrarConsulta

    //AgregaraSaladeEspera
    //EliminarSaladeEspera
    //MostrarSaladeEspera

}
