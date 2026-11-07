package Modelo;

import Interfaces.Identificable_Interface;

import java.time.LocalDateTime;
import java.util.Objects;

public class Consulta implements Identificable_Interface {
    private int ID;
    private int mascota_id;
    private int matricula_veterinario;
    private LocalDateTime fecha;
    private String diagnostico;
    private String tratamiento;
    private String receta;

    //Constructores
    public Consulta() {
    }

    public Consulta(int mascota_id, int matricula_veterinario, LocalDateTime fecha, String diagnostico, String tratamiento, String receta) {
        this.mascota_id = mascota_id;
        this.matricula_veterinario = matricula_veterinario;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.receta = receta;
    }

    //Este es solo para el metodo DAO

    public Consulta(int ID, int mascota_id, int matricula_veterinario, LocalDateTime fecha, String diagnostico, String tratamiento, String receta) {
        this.ID = ID;
        this.mascota_id = mascota_id;
        this.matricula_veterinario = matricula_veterinario;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.receta = receta;
    }

    //metodos getter y setter

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMascota_id() {
        return mascota_id;
    }

    public void setMascota_id(int mascota_id) {
        this.mascota_id = mascota_id;
    }

    public int getMatricula_veterinario() {
        return matricula_veterinario;
    }

    public void setMatricula_veterinario(int matricula_veterinario) {
        this.matricula_veterinario = matricula_veterinario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    //metodo toString

    @Override
    public String toString() {
        return "CONSULTA:" + '\n'+
                "ID consulta: "+ ID + '\n'+
                "ID mascota: "+ mascota_id + '\n'+
                "Matricula veterinario: "+ matricula_veterinario + '\n'+
                "Fecha: "+ fecha + '\n'+
                "Diagnostico: "+ diagnostico + '\n'+
                "Tratamiento: "+ tratamiento + '\n'+
                "Receta: "+ receta + '\n';
    }

    //Metodo equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Consulta)) {
            return false;
        }

        Consulta otro = (Consulta) obj;

        return Objects.equals(ID, otro.ID);
    }
    //Metodo HashCode
    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
    //Implemetacion Interfaz
    @Override
    public String identificador() {
        return "denficador de la clase: ID Nro: " + getID();
    }
}
