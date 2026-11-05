package Persistencia;

import Modelo.Turno;
import Modelo.Cliente;
import Modelo.Veterinario;
import Conexion.ConexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    // 1. INSERTAR TURNO
    public void insertar(Turno turno) throws SQLException{
        String sql = "INSERT INTO turno (fecha, hora, costo, cliente_dni, matricula_veterinario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(turno.getFecha()));
            ps.setTime(2, Time.valueOf(turno.getHora()));
            ps.setDouble(3, turno.getCosto());
            ps.setString(4, turno.getDni_cliente());
            ps.setInt(5, turno.getMatricula_veterinario());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    turno.setID(idGenerado); // Se lo asignamos al objeto en memoria
                }
            }

        }
    }

    // 2. LISTAR TURNOS
    public List<Turno> listarTurnos()throws SQLException {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT * FROM turno";

        try (Connection conn = ConexionBDD.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Turno t = new Turno();
                t.setID(rs.getInt("id"));
                t.setFecha(rs.getDate("fecha").toLocalDate());
                t.setHora(rs.getTime("hora").toLocalTime());
                t.setCosto(rs.getDouble("costo"));
                t.setDni_cliente(rs.getString("cliente_dni"));
                t.setMatricula_veterinario(rs.getInt("matricula_veterinario"));


                lista.add(t);
            }
        }
        return lista;
    }

    // 3. ELIMINAR TURNO POR ID
    public void eliminar(int id)throws SQLException {
        String sql = "DELETE FROM turno WHERE id = ?";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        }
    }
}