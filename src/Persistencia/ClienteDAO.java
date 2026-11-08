package Persistencia;

import Conexion.ConexionBDD;
import Excepciones.ClienteNoEncontradoException;
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
    // buscar cliente pod DNI

    public Cliente buscarPorDni(String dni) throws ClienteNoEncontradoException, SQLException {
        String sql = "SELECT dni, nombre, apellido, telefono, email, domicilio FROM cliente WHERE dni = ?";

        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Seteamos el parámetro del DNI de manera segura contra SQL Injection
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Mapeamos los datos de la base de datos al objeto Cliente
                    Cliente cliente = new Cliente();
                    cliente.setDni(rs.getString("dni"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setDomicilio(rs.getString("domicilio"));

                    return cliente;
                }
            }
        }

        // Si el ResultSet no arrojó resultados, lanzamos la excepción personalizada
        throw new ClienteNoEncontradoException("No se encontró ningún cliente registrado con el DNI: " + dni);
    }


    // 3. ELIMINAR CLIENTE POR DNI
    public boolean eliminar(String dni)throws SQLException {
        String sql = "DELETE FROM cliente WHERE dni = ?";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            return  ps.executeUpdate()> 0;


        }
    }

    // modificar contacto del cliente

    public boolean modificar(int id,String telefono)throws SQLException{
        String sql= " UPDATE clientes SET telefono= ? WHERE id= ?";

        try(Connection con= ConexionBDD.conectar();
       PreparedStatement ps= con.prepareStatement(sql) ){

            ps.setString(1,telefono);
            ps.setInt(2,id);

          return  ps.executeUpdate()> 0;

        }


    }
}
