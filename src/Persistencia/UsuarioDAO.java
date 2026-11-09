package Persistencia;

import Conexion.ConexionBDD;
import Modelo.Cliente;
import Modelo.TIPOdeUSUARIO;
import Modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario obtenerPorUserName(String username) throws SQLException {

        String sql = " SELECT id, nombre_usuario, password, rol WHERE nombre_usuario=?";

        Usuario usuario = null;

        try (Connection con = ConexionBDD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user = rs.getString("nombre_usuario");
                    String pass = rs.getString("password");

                    /// Convierte el String de la BD a Enum
                    String rolStr = rs.getString("rol");
                    TIPOdeUSUARIO rol = TIPOdeUSUARIO.valueOf(rolStr.toUpperCase());

                    usuario = new Usuario(id, user, pass, rol);

                }
            }
        }
        return usuario;
    }
///  por si queremos agregar otro tipo de usuario
    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, password, rol) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            // Guardamos el enum como String usando .name()
            ps.setString(3, usuario.getRol().name());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        }
    }

    public List <Usuario> listar()throws SQLException{

        String sql= " SELECT * FROM usuarios";
        List<Usuario> usuarios= new ArrayList<>();

        try(Connection con= ConexionBDD.conectar();
        PreparedStatement ps= con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("nombre_usuario");
                String password = rs.getString("password");
                String rolStr = rs.getString("rol");
                TIPOdeUSUARIO rol = TIPOdeUSUARIO.valueOf(rolStr.toUpperCase());

                Usuario user= new Usuario(id,username,password,rol);

                usuarios.add(user);
            }
        }
        return usuarios;
    }


    /// modificar usuario

    /// en este no hace falta pasarle otra vez el id del usuario
    /// ya que en pstmt.set int ahi se extrae el id para el where id=?
    ///
    public boolean modificarUsuario(Usuario usuario)throws SQLException{

        String sql= "UPDATE usuarios SET nombre = ?,  password= ? rol =? WHERE ID= ?";
        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol().name());
            pstmt.setInt(4, usuario.getId());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        }
    }

    public boolean eliminar(int idUsuario)throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = ConexionBDD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        }
    }
}
