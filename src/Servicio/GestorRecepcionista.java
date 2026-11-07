package Servicio;

import Modelo.Cliente;
import Modelo.Consulta;
import Modelo.Mascota;
import Modelo.Turno;
import Persistencia.ClienteDAO;
import Persistencia.ConsultaDAO;
import Persistencia.MascotaDAO;
import Persistencia.TurnoDAO;

import java.util.ArrayList;
import java.util.List;

public class GestorRecepcionista {
    private ClienteDAO dao_cliente;
    private TurnoDAO dao_turno;
    private MascotaDAO dao_mascotas;
    private ConsultaDAO dao_consulta;
    private List <Mascota> mascotas;
    private List <Turno> turnos;
    private List <Cliente> clientes;
    private List <Consulta> consultas;

    public GestorRecepcionista() {
        this.dao_cliente = new ClienteDAO();
        this.dao_turno = new TurnoDAO();
        this.dao_mascotas = new MascotaDAO();
        this.dao_consulta = new ConsultaDAO();
        this.mascotas = new ArrayList<>();
        this.turnos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.consultas = new ArrayList<>();
    }

    //AñadirTurno
    //EliminarTurno
    //ModificarTurno
    //BuscarTurno
    //ListarTurnos

    //AñadirCliente
    //EliminarCliente
    //ModificarClientes
    //ListarCLientes
    //BuscarCliente
    //MostrarMascotasxCliente

    //buscarConsulta
    //CobrarConsulta 

    //AgregaraSaladeEspera
    //EliminarSaladeEspera
    //MostrarSaladeEspera

}
