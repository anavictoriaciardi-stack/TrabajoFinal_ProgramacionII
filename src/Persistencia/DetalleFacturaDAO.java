package Persistencia;

import Conexion.ConexionBDD;
import Modelo.DetalleFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaDAO {

    public List<DetalleFactura> listarPorFactura(int facturaId) throws SQLException {
        List<DetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT id, factura_id, concepto, subtotal FROM detalle_facturas WHERE factura_id = ?";

        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, facturaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleFactura detalle = new DetalleFactura();
                    detalle.setId(rs.getInt("id"));
                    detalle.setFacturaId(rs.getInt("factura_id"));
                    detalle.setConcepto(rs.getString("concepto"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    lista.add(detalle);
                }
            }
        }
        return lista;
    }
}
