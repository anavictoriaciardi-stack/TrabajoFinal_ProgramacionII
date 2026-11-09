package Servicio;

import Excepciones.TurnoInexistenteException;
import Modelo.Consulta;
import Modelo.Mascota;
import Persistencia.ConsultaDAO;
import Persistencia.MascotaDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
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
    public void eliminarMascota(int id) {
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
            System.out.println("Error al listar las mascotas" + e.getMessage());
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
    public void mostrarUnaMascota (String dni, String nombre)throws TurnoInexistenteException {
        boolean encontrar = buscarMascotaxdniyNombre(dni, nombre);
        if (encontrar) {
            for (Mascota m : mascotas) {
                if (m.getCliente_dni().equals(dni)) {
                    if (m.getNombre().equals(nombre)){
                        System.out.println(m);
                    }
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
    public boolean buscarMascotaxdniyNombre (String dni, String nombre){
        listarMascotas();
        for (Mascota m: mascotas) {
            if (m.getCliente_dni().equals(dni)) {
                if (m.getNombre().equals(nombre)){
                    return true;
                }
            }
        }
        return false;
    }
    ///---------- CONSULTA ---------- ///
    ///AñadirConsulta
    public void agregarConsulta (Consulta consulta){
        try {
            dao_consulta.insertar(consulta);
            consultas.add(consulta);
            System.out.println(" La consulta se agregó correctamente -");
        } catch (SQLException e){
            System.out.println("Error al agregar la consulta. "+ e.getMessage());
        }
    }
    ///EliminarConsulta
    public void eliminarConsulta(int id) {
        boolean encontro = buscarConsulta(id);
        if (encontro) {
            try {
                dao_consulta.eliminar(id);
                mascotas.removeIf(t -> t.getId() == id);
                System.out.println("Consulta eliminada correctamente");
            } catch (SQLException e) {
                System.out.println("Error al eliminar la consulta. " + e.getMessage());
            }
        }
    }
    ///ModificarConsulta
    public void modificarConsulta (int id, String nuevoTratamiento, String nuevaReceta){
        boolean encontro = buscarMascota(id);
        if (encontro) {
            try {
                dao_consulta.modificarConsulta(id, nuevoTratamiento, nuevaReceta);
                listarConsultas();
            } catch (SQLException e) {
                System.out.println("Error al modificar la consulta. "+e.getMessage());
            }
        }
    }
    ///ListarConsultas
    public void listarConsultas (){
        try {
            consultas.clear();
            consultas.addAll(dao_consulta.listarConsultas());
        } catch (SQLException e) {
            System.out.println("Error al listar las consultas" + e.getMessage());
        }
    }
    ///MostrarConsultas
    public void mostrarConsultas (){
        listarConsultas();
        for (Consulta c: consultas){
            System.out.println(c);
            System.out.println("--------------------------------------------");
        }
    }
    ///MostrarunaConsulta
    public void mostrarUnaConsulta (int id) {
        boolean encontrar = buscarConsulta(id);
        if (encontrar) {
            for (Consulta c : consultas) {
                if (c.getID() == id) {
                    System.out.println(c);
                }
            }
        }
        else {
            System.out.println("No hay consulta con ese id");

        }
    }
    ///MostraConsultasporfecha
    public void mostrarConsultasporFecha (LocalDateTime fecha) {
        boolean encontrar = buscarConsultaxFecha(fecha);
        if (encontrar) {
            for (Consulta c : consultas) {
                if (c.getFecha() == fecha) {
                    System.out.println(c);
                }
            }
        }
        else {
            System.out.println("No hay consulta con esa fecha");

        }
    }
    ///BuscarConsultaxfecha
    public boolean buscarConsultaxFecha (LocalDateTime fecha){
        listarConsultas();
        for (Consulta c: consultas) {
            if (c.getFecha() == fecha) {
                return true;
            }
        }
        return false;
    }
    ///BuscarConsulta
    public boolean buscarConsulta (int id){
        listarConsultas();
        for (Consulta c: consultas) {
            if (c.getID() == id) {
                return true;
            }
        }
        return false;
    }
    ///MostrarHistorialClinico
    public void historialClinico (int IDmascota){
        listarConsultas();
        for (Consulta c : consultas){
            if (c.getMascota_id() == IDmascota){
                System.out.println(c);
            }
        }
    }


}
