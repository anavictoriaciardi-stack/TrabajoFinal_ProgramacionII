package Servicio;

import Excepciones.TurnoInexistenteException;
import Modelo.Consulta;
import Modelo.Mascota;
import Modelo.Turno;
import Persistencia.ConsultaDAO;
import Persistencia.MascotaDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
    public void agregarMascota (Mascota mascota){
        try {
            dao_mascota.insertar(mascota);
            mascotas.add(mascota);
            System.out.println(" La mascota se agregó correctamente -");
        } catch (SQLException e){
            System.out.println("Error al agregar la mascota. "+ e.getMessage());
        }
    }
    ///EliminarMascota
    public void eliminarMAscota(int id) {
        boolean encontro = buscarMascota(id);
        if (encontro) {
            try {
                dao_mascota.eliminarMascota(id);
                mascotas.removeIf(t -> t.getId() == id);
                System.out.println("Turno eliminado correctamente");
            } catch (SQLException e) {
                System.out.println("Error al eliminar turno. " + e.getMessage());
            }
        }
    }
    ///ModificarMascota
    public void modificarMascota (int id, String nuevoNombre,String especieNueva, String nuevaRaza, int Edadnueva){
        boolean encontro = buscarMascota(id);
        if (encontro) {
            try {
                dao_mascota.modificarMascota(id, nuevoNombre, especieNueva, nuevaRaza, Edadnueva);
                listarMascotas();
            } catch (SQLException e) {
                System.out.println("Error al modificar el turno. "+e.getMessage());
            }
        }
    }
    ///ListarMascotas
    public void listarMascotas (){
        try {
            mascotas.clear();
            mascotas.addAll(dao_mascota.listarMascotas());
        } catch (SQLException e) {
            System.out.println("Error al listar el mensaje" + e.getMessage());
        }
    }
    ///MostrarMascotas
    public void mostrarMascotas (){
        listarMascotas();
        for (Mascota m: mascotas){
            System.out.println(m);
            System.out.println("--------------------------------------------");
        }
    }
    ///MostrarUnaMascota
    public void mostrarUnaMascota (int id)throws TurnoInexistenteException {
        boolean encontrar = buscarMascota(id);
        if (encontrar) {
            for (Mascota m : mascotas) {
                if (m.getId() == id) {
                    System.out.println(m);
                }
            }
        }
        else {
            throw new TurnoInexistenteException(" no hay un turno disponible con ese ID.");

        }
    }
    ///BuscarMascotaxId
    public boolean buscarMascota (int id){
        listarMascotas();
        for (Mascota m: mascotas) {
            if (m.getId() == id) {
                return true;
            }
        }
        return false;
    }
    ///BuscarMascotaxDni
    public boolean buscarMascotaxdni (String dni){
        listarMascotas();
        for (Mascota m: mascotas) {
            if (m.getCliente_dni() == dni) {
                return true;
            }
        }
        return false;
    }

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
