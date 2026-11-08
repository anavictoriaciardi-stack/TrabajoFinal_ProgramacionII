package Modelo;

import Interfaces.Identificable_Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mascota implements Identificable_Interface {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private String cliente_dni;
    private List <Consulta> historialClinico;

    //Constructores
    public Mascota() {
    }

    public Mascota(String nombre, String especie, String raza, int edad, String cliente_dni) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.cliente_dni=cliente_dni;
        this.historialClinico = new ArrayList<>();
    }
    //Este es solo para el metodo DAO listar
    public Mascota(int id, String nombre, String especie, String raza, int edad, String cliente_dni) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.cliente_dni=cliente_dni;
        this.historialClinico = new ArrayList<>();
    }

    //Metodos getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCliente_dni() {
        return cliente_dni;
    }

    public void setCliente_dni(String cliente_dni) {
        this.cliente_dni = cliente_dni;
    }

    //metodo toString

    @Override
    public String toString() {
        return "MASCOTA: "+'\n'+
                "ID: "+ id + '\n'+
                "Nombre: "+ nombre + '\n'+
                "Especie: "+ especie + '\n'+
                "raza: "+ raza + '\n'+
                "edad: "+ edad + '\n';
    }

    //Metodo equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Mascota)) {
            return false;
        }

        Mascota otro = (Mascota) obj;

        return Objects.equals(id, otro.id);
    }
    //Metodo HashCode
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    //Implemetacion Interfaz
    @Override
    public String identificador() {
        return "denficador de la clase: ID Nro: " + getId();
    }
}
