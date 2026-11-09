package Persistencia;

import Modelo.Veterinario;
import Conexion.ConexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

    //insertar veterinario
    public void insertar(Veterinario vet)throws SQLException {
        String sql = "INSERT INTO veterinario (matricula, nombre, apellido, dni, telefono, email, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vet.getMatricula());
            ps.setString(2, vet.getNombre());
            ps.setString(3, vet.getApellido());
            ps.setString(4, vet.getDni());
            ps.setString(5, vet.getTelefono());
            ps.setString(6, vet.getEmail());
            ps.setString(7, vet.getEspecialidad());
            ps.executeUpdate();
            System.out.println("Veterinario insertado correctamente.");
        }
    }
// listar todos los veterinarios
    public List<Veterinario> listarVeterinarios() throws SQLException{
        List<Veterinario> lista = new ArrayList<>();
        String sql = "SELECT * FROM veterinario";
        try (Connection conn = ConexionBDD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Veterinario v = new Veterinario();
                v.setMatricula(rs.getInt("matricula"));
                v.setNombre(rs.getString("nombre"));
                v.setApellido(rs.getString("apellido"));
                v.setDni(rs.getString("dni"));
                v.setTelefono(rs.getString("telefono"));
                v.setEmail(rs.getString("email"));
                v.setEspecialidad(rs.getString("especialidad"));
                lista.add(v);
            }
        }
        return lista;
    }

    // modificar veterinario

    public boolean modificar(int matricula, String especialidad)throws SQLException{

        String sql= " UPDATE veterinario SET especialidad WHERE id= ?";

        try(Connection con= ConexionBDD.conectar();
        PreparedStatement ps= con.prepareStatement(sql)){

            ps.setString(1,especialidad);
            ps.setInt(2,matricula);
            return ps.executeUpdate() > 0;
        }
    }
    //eliminar veterinario por matricula

    // ELIMINAR VETERINARIO POR MATRÍCULA
    public void eliminar(int matricula) throws SQLException{
        String sql = "DELETE FROM veterinario WHERE matricula = ?";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, matricula);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Veterinario eliminado correctamente.");
            } else {
                System.out.println("No se encontró un veterinario con esa matrícula.");
            }

        }
    }
}
