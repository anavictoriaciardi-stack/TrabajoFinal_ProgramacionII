package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

//Clase turno
public class Turno{
    private int ID;
    private LocalDate fecha;
    private LocalTime hora;
    private double costo;
    private String dni_cliente;
    private int matricula_veterinario;

    //Constructores
    public Turno() {
    }

    public Turno(LocalDate fecha, LocalTime hora, double costo, String dni_cliente, int matricula_veterinario) {
        this.fecha = fecha;
        this.hora = hora;
        this.costo = costo;
        this.dni_cliente = dni_cliente;
        this.matricula_veterinario = matricula_veterinario;
    }
    //Este es solo para el metodo DAO listar
    public Turno(int ID, LocalDate fecha, LocalTime hora, double costo, String dni_cliente, int matricula_veterinario) {
        this.ID = ID;
        this.fecha = fecha;
        this.hora = hora;
        this.costo = costo;
        this.dni_cliente = dni_cliente;
        this.matricula_veterinario = matricula_veterinario;
    }
    //Metedos getter y setter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public int getMatricula_veterinario() {
        return matricula_veterinario;
    }

    public void setMatricula_veterinario(int matricula_veterinario) {
        this.matricula_veterinario = matricula_veterinario;
    }

    //Metodo toString

    @Override
    public String toString() {
        return "DETALLE DEL TURNO:"+'\n'+
                "ID: "+ ID + '\n'+
                "fecha: "+ fecha + '\n'+
                "hora: "+ hora + '\n'+
                "costo: "+ costo+ '\n'+
                "DNI del cliente: "+ dni_cliente + '\n'+
                "Veterinario (Matricula): "+ matricula_veterinario +'\n';
    }

    //metodos equals

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Turno)) {
            return false;
        }

        Turno otro = (Turno)obj;

        return Objects.equals(ID, otro.ID);
    }
    //Metodo HashCode
    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
}
