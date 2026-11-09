package Persistencia;

import Modelo.Consulta;
import Modelo.Mascota;
import Modelo.Veterinario;
import Conexion.ConexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // 1. INSERTAR CONSULTA
    public void insertar(Consulta consulta)throws SQLException {
        String sql = "INSERT INTO consulta (mascota_id, matricula_veterinario, fecha, diagnostico, tratamiento, receta) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, consulta.getMascota_id());
            ps.setInt(2, consulta.getMatricula_veterinario());
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getFecha()));
            ps.setString(4, consulta.getDiagnostico());
            ps.setString(5, consulta.getTratamiento());
            ps.setString(6, consulta.getReceta());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    consulta.setID(idGenerado); // Se lo asignamos al objeto en memoria
                    System.out.println("Consulta médica registrada correctamente con ID: " + idGenerado);
                }
            }
        }
    }

    // 2. LISTAR CONSULTAS
    public List<Consulta> listarConsultas()throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM consulta";

        try (Connection conn = ConexionBDD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Consulta con = new Consulta();
                con.setID(rs.getInt("id"));
                con.setMascota_id(rs.getInt("mascota_id"));
                con.setMatricula_veterinario(rs.getInt("matricula_veterinario"));
                con.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                con.setDiagnostico(rs.getString("diagnostico"));
                con.setTratamiento(rs.getString("tratamiento"));
                con.setReceta(rs.getString("receta"));


                lista.add(con);
            }
        }
        return lista;
    }

    //3. MODIFICAR CONSULTA POR ID

    public boolean modificarConsulta(int id, String nuevoTratamiento, String nuevaReceta)throws SQLException{

        String sql= "UPDATE consulta SET tratamiento=?, SET receta =? WHERE id= ?";
        try(Connection con= ConexionBDD.conectar();
        PreparedStatement ps= con.prepareStatement(sql)){

            ps.setString(1,nuevoTratamiento);
            ps.setString(2,nuevaReceta);
            ps.setInt(3,id);

            return ps.executeUpdate() > 0;
        }
    }

    // 4. ELIMINAR CONSULTA POR ID
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        }
    }
}
