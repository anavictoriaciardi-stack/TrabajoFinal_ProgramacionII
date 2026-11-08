package Persistencia;

import Conexion.ConexionBDD;
import Excepciones.ClienteNoEncontradoException;
import Excepciones.FacturaNoEncontradaException;
import Modelo.Cliente;
import Modelo.Factura;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class FacturaDAO {

    private static final String INSERT =
            "INSERT INTO facturas (fecha_hora, cliente_dni, monto_total, forma_pago, activo) VALUES (?, ?, ?, ?, 1)";

    private static final String SELECT_BY_ID =
            "SELECT id, fecha_hora, cliente_dni, monto_total, forma_pago, activo FROM facturas WHERE id = ? AND activo = 1";

    private static final String SELECT_ALL_ACTIVAS =
            "SELECT id, fecha_hora, cliente_dni, monto_total, forma_pago, activo FROM facturas WHERE activo = 1";

    private static final String UPDATE =
            "UPDATE facturas SET monto_total = ?, forma_pago = ? WHERE id = ?";

    private static final String BAJA_LOGICA =
            "UPDATE facturas SET activo = 0 WHERE id = ?";

    public FacturaDAO() {
    }

    /**
     * 1. CREATE: Inserta una nueva factura en la base de datos.
     */
    public int insertar(Factura factura) throws SQLException {
        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(factura.getFechaHora()));
            ps.setString(2, factura.getCliente().getDni());
            ps.setDouble(3, factura.getMontoTotal());
            ps.setString(4, factura.getFormaPago());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    factura.setId(idGenerado);
                    return idGenerado;
                }
            }
        }
        return -1;
    }

    /**
     * 2. READ (Buscar por ID): Retorna una factura si está activa.
     */
    public Factura buscarPorId(int id) throws FacturaNoEncontradaException,
            ClienteNoEncontradoException,SQLException {
        String sql = "SELECT id, fecha_hora, cliente_dni, monto_total, forma_pago, activo FROM facturas WHERE id = ? AND activo = 1";

        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Factura f = new Factura();
                    f.setId(rs.getInt("id"));
                    f.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());

                    String dniCliente = rs.getString("cliente_dni");
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Cliente clienteAsociado = clienteDAO.buscarPorDni(dniCliente);

                    f.setCliente(clienteAsociado);
                    f.setMontoTotal(rs.getDouble("monto_total"));
                    f.setFormaPago(rs.getString("forma_pago"));
                    return f;
                }
            }
        }


        throw new FacturaNoEncontradaException("No se pudo encontrar la factura con el ID N°: " + id);
    }


     //* 2. READ (Listar todas): Devuelve un List con todas las facturas activas.

    public List<Factura> listarActivas() throws SQLException {
        List<Factura> lista = new ArrayList<>();
        try (Connection con = ConexionBDD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_ACTIVAS)) {

            while (rs.next()) {
                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                f.setMontoTotal(rs.getDouble("monto_total"));
                f.setFormaPago(rs.getString("forma_pago"));
                lista.add(f);
            }
        }
        return lista;
    }

    /**
     * 3. UPDATE: Modifica datos generales de la factura.
     */
    public boolean actualizar(Factura factura) throws SQLException {
        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setDouble(1, factura.getMontoTotal());
            ps.setString(2, factura.getFormaPago());
            ps.setInt(3, factura.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     ///4. DELETE (Baja Lógica): Actualiza el campo activo a 0 en lugar de eliminar el registro.
     */
    public boolean darDeBaja(int id) throws SQLException {
        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(BAJA_LOGICA)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}


