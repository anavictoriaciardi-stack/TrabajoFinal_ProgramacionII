package Modelo;

import java.util.Objects;

public class Usuario {
    private int id;
    private String Username;
    private String Password;
    private TIPOdeUSUARIO rol;

    public Usuario(int id, String username, String password, TIPOdeUSUARIO rol) {
        this.id = id;
        Username = username;
        Password = password;
        this.rol = rol;
    }

    public Usuario(String username, String password) {
        Username = username;
        Password = password;
        this.rol = TIPOdeUSUARIO.VETERINARIO;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public TIPOdeUSUARIO getRol() {
        return rol;
    }

    public void setRol(TIPOdeUSUARIO rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "USUARIO= " + '\n'+
                "Username: " + getUsername() + '\n' +
                "Password: " + getPassword() + '\n' +
                "Rol: " +getRol() + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }

        Usuario otro = (Usuario) obj;

        return Objects.equals(id, otro.id) &&
                Objects.equals(Username, otro.Username) &&
                Objects.equals(Password, otro.Password);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
