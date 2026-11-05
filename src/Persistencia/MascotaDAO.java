package Persistencia;

import Modelo.Mascota;
import Modelo.Cliente;
import Conexion.ConexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MascotaDAO {

    public void insertar(Mascota mascota) throws SQLException {
        String sql = "INSERT INTO mascota (nombre, especie, raza, edad, cliente_dni) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mascota.getNombre());
            ps.setString(2, mascota.getEspecie());
            ps.setString(3, mascota.getRaza());
            ps.setInt(4, mascota.getEdad());
            ps.setString(5, mascota.getCliente_dni());
            ps.executeUpdate();
            System.out.println("Mascota insertada correctamente.");
        }
    }

    public List<Mascota> listarMascotas() throws SQLException{
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascota";
        try (Connection conn = ConexionBDD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Mascota m = new Mascota();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecie(rs.getString("especie"));
                m.setRaza(rs.getString("raza"));
                m.setEdad(rs.getInt("edad"));
                m.setCliente_dni(rs.getString("cliente_dni"));
                lista.add(m);
            }
        }
        return lista;
    }
}
