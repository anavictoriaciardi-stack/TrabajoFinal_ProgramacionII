package Servicio;

import Modelo.Consulta;
import Modelo.Factura;
import Modelo.Mascota;
import Modelo.Veterinario;
import Persistencia.FacturaDAO;
import Persistencia.VeterinarioDAO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GestorAdministrador {
    private VeterinarioDAO dao_veterinario;
    private FacturaDAO dao_facturas;
    private Set <Veterinario> veterinarios;
    private Set <Factura> facturas;

    public GestorAdministrador() {
        this.dao_veterinario = new VeterinarioDAO();
        this.dao_facturas = new FacturaDAO();
        this.veterinarios = new HashSet<>();
        this.facturas = new HashSet<>();
    }

    /// ---------- VETERINARIOS ---------- ///

    ///AñadirVeterinario
    public void agregarVeterinario (Veterinario vete){
        try {
            dao_veterinario.insertar(vete);
            veterinarios.add(vete);
            System.out.println(" El veterinario se agregó correctamente -");
        } catch (SQLException e){
            System.out.println("Error al agregar el veterinario. "+ e.getMessage());
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
    public void modificar (int matricula, String especialidad){
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
    // Dar de baja una factura
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

}
