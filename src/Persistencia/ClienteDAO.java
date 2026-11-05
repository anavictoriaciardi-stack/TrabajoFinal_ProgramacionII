package Persistencia;

import Conexion.ConexionBDD;
import Modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public ClienteDAO() {
    }

    // 1. INSERTAR CLIENTE
    public void insertar(Cliente cliente)throws SQLException {
        String sql = "INSERT INTO cliente (dni, nombre, apellido, telefono, email, domicilio) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getDomicilio());

            ps.executeUpdate();
            System.out.println("Cliente insertado correctamente.");

        }
    }

    // 2. OBTENER TODOS LOS CLIENTES
    public List<Cliente> listarClientes() throws SQLException{
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setDni(rs.getString("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDomicilio(rs.getString("domicilio"));

                lista.add(c);
            }
        }
        return lista;
    }

    // 3. ELIMINAR CLIENTE POR DNI
    public void eliminar(String dni)throws SQLException {
        String sql = "DELETE FROM cliente WHERE dni = ?";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.executeUpdate();
            System.out.println("Cliente eliminado correctamente.");

        }
    }
}
