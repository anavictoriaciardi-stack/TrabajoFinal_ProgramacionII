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
    public String agregarMascota (Mascota mascota){
        String mensaje = "";
        try {
            dao_mascota.insertar(mascota);
            mascotas.add(mascota);
            mensaje += " La mascota se agregó correctamente -";
        } catch (SQLException e){
            mensaje += "Error al agregar la mascota. "+ e.getMessage();
        }
        return mensaje;
    }
    ///EliminarMascota
    public String eliminarMascota(int id) {
        boolean encontro = buscarMascota(id);
        String mensaje = "";
        if (encontro) {
            try {
                dao_mascota.eliminarMascota(id);
                mascotas.removeIf(m -> m.getId() == id);
                mensaje += "Mascota eliminado correctamente";
            } catch (SQLException e) {
                System.out.println("Error al eliminar la mascota. " + e.getMessage());
            }
        }else{
            mensaje += "No se encontro la mascota";
        }
        if(!mascotas.contains(buscarMascota(id))){
            mensaje = "Se elimino correctamente la mascota";
        }else{
            mensaje += "\nNo se pudo eliminar la mascota";
        }
        return mensaje;
    }
    ///ModificarMascota
    public String modificarMascota (int id, String nuevoNombre,String especieNueva, String nuevaRaza, int Edadnueva){
        boolean encontro = buscarMascota(id);
        String mensaje = "";
        if (encontro) {
            try {
                dao_mascota.modificarMascota(id, nuevoNombre, especieNueva, nuevaRaza, Edadnueva);
                listarMascotas();
                mensaje += "Mascota modificada";
            } catch (SQLException e) {
                System.out.println("Error al modificar la mascota. "+e.getMessage());
            }
        }else{
            mensaje += "No se encontro la mascota";
        }
        return mensaje;
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
    public String mostrarMascotas (){
        listarMascotas();
        String mostrar = "";
        for (Mascota m: mascotas){
            mostrar += m.toString();
            mostrar += "\n"+ "--------------------------------------------";
        }
        return mostrar;
    }
    ///MostrarUnaMascota
    public String mostrarUnaMascota (String dni, String nombre)throws TurnoInexistenteException {
        boolean encontrar = buscarMascotaxdniyNombre(dni, nombre);
        String mostrar = "";
        if (encontrar) {
            for (Mascota m : mascotas) {
                if (m.getCliente_dni().equals(dni)) {
                    if (m.getNombre().equals(nombre)){
                        mostrar += m.toString();
                    }
                }
            }
        }
        else {
            throw new TurnoInexistenteException(" no hay uns mascota registrada bajo ese nombre y DNI.");

        }
        return mostrar;
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
    public String agregarConsulta (Consulta consulta){
        String mensaje = "";
        try {
            dao_consulta.insertar(consulta);
            consultas.add(consulta);
            if(consultas.contains(consulta)){
                mensaje += "Se agrego la consulta correctamente";
            }else{
                mensaje += "No se pudo agregar la consulta";
            }
        } catch (SQLException e){
            mensaje += "Error al agregar la consulta. "+ e.getMessage();
        }finally{
            return mensaje;
        }
    }
    ///EliminarConsulta
    public String eliminarConsulta(int id) {
        boolean encontro = buscarConsulta(id);
        String mensaje = "";
        if (encontro) {
            try {
                dao_consulta.eliminar(id);
                consultas.removeIf(t -> t.getID() == id);
                if(consultas.contains(buscarMascota(id))){
                    mensaje += "Consulta no se pudo eliminar";
                }else{
                    mensaje += "Se pudo eliminar la consulta";
                }
            } catch (SQLException e) {
                mensaje += "Error al eliminar la consulta. " + e.getMessage();
            }
        }else{
            mensaje += "No se pudo encontrar la consulta";
        }
        return mensaje;
    }
    ///ModificarConsulta
    public String modificarConsulta (int id, String nuevoTratamiento, String nuevaReceta){
        boolean encontro = buscarMascota(id);
        String mensaje = "";
        if (encontro) {
            try {
                dao_consulta.modificarConsulta(id, nuevoTratamiento, nuevaReceta);
                listarConsultas();
                mensaje += "Consulta Modificada correctamente";
            } catch (SQLException e) {
                System.out.println("Error al modificar la consulta. "+e.getMessage());
            }
        }else{
            mensaje += "No se pudo encontrar la consulta";
        }
        return mensaje;
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
    public String mostrarConsultas (){
        listarConsultas();
        String  mostrar = "";
        for (Consulta c: consultas){
            mostrar += c.toString();
            mostrar += "--------------------------------------------";
        }
        return mostrar;
    }
    ///MostrarunaConsulta
    public String mostrarUnaConsulta (int id) {
        boolean encontrar = buscarConsulta(id);
        String mostrar = "";
        if (encontrar) {
            for (Consulta c : consultas) {
                if (c.getID() == id) {
                    mostrar += c.toString();
                }
            }
        }
        else {
            mostrar += "No hay consulta con ese id";
        }
        return mostrar;
    }
    ///MostraConsultasporfecha
    public String mostrarConsultasporFecha (LocalDateTime fecha) {
        boolean encontrar = buscarConsultaxFecha(fecha);
        String mostrar = "";
        if (encontrar) {
            for (Consulta c : consultas) {
                if (c.getFecha() == fecha) {
                    mostrar += c;
                }
            }
        }
        else {
            mostrar += "No hay consulta con esa fecha";

        }
        return mostrar;
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
    public String historialClinico (int IDmascota){
        listarConsultas();
        String mensaje = "";
        for (Consulta c : consultas){
            if (c.getMascota_id() == IDmascota){
                mensaje += c.toString();
            }
        }
        return mensaje;
    }


}

