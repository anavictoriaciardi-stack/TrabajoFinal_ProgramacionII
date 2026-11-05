package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private List <Consulta> historialClinico;

    //Constructores
    public Mascota() {
    }

    public Mascota(String nombre, String especie, String raza, int edad, List<Consulta> historialClinico) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.historialClinico = historialClinico;
    }
    //Este es solo para el metodo DAO listar
    public Mascota(int id, String nombre, String especie, String raza, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
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
}
