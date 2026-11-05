package Modelo;

import java.util.Objects;

//Clase Cliente. Hereda de Persona.
public class Cliente extends Persona{
    private String domicilio;

    //Constructores de la clase
    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellido, String telefono, String email, String domicilio) {
        super(dni, nombre, apellido, telefono, email);
        this.domicilio = domicilio;
    }

    //Métodos getter y setter

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    //Metodo toString

    @Override
    public String toString() {
        return "CLIENTE: " + '\n'+
                super.toString() +
                "Domicilio: "+domicilio+'\n';
    }

    //Metodo Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cliente)) {
            return false;
        }

        Cliente otro = (Cliente) obj;

        return Objects.equals(dni, otro.dni);
    }
    //Metodo HashCode
    @Override
    public int hashCode() {
        return Objects.hash(getDni());
    }
}
