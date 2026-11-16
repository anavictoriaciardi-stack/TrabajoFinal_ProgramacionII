package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {

    private static final String url= "jdbc:mysql://172.16.1.64:3306/veterinaria_2026";
    private static final String user="bdd1";
    private static final String password= "bdd1";

    public static Connection conectar()throws SQLException{
        return DriverManager.getConnection(url,user,password);
    }
}
