package Servicio;

import Modelo.Consulta;
import Modelo.Mascota;
import Persistencia.ConsultaDAO;
import Persistencia.MascotaDAO;

import java.util.HashSet;
import java.util.Set;

public class GestorVeterinario {
    private Set <Mascota> mascotas;
    private Set <Consulta> consultas;
    private MascotaDAO dao_mascota;
    private ConsultaDAO dao_consulta;

    public GestorVeterinario() {
        this.mascotas = new HashSet<>();
        this.consultas = new HashSet<>();
        this.dao_mascota = new MascotaDAO();
        this.dao_consulta = new ConsultaDAO();
    }

    /// ---------- MASCOTAS ---------- ///
    ///AñadirMascota
    ///EliminarMascota
    //ModificarMAscota
    ///ListarConsultas
    ///MostrarMascotas
    ///BuscarMascota

    ///---------- CONSULTA ---------- ///
    ///AñadirConsulta
    ///EliminarConsulta
    //ModificarConsulta
    ///ListarConsultas
    ///MostrarConsultas
    ///BuscarConsulta

    /// ---------- ATENCION ---------- ///
    ///VerHistorialClinico
    /// AtenderPaciente /*eliminar de sala de espera y String llamandolo.*/


}
