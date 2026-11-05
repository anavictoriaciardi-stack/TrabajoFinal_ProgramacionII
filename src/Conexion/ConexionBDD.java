package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {

    private static final String url= "jdbc:mysql://localhost:3306/veterinaria_2026";
    private static final String user="root";
    private static final String password= "root";

    public static Connection conectar()throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }
}
