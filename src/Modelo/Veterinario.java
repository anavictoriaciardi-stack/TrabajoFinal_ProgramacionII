package Modelo;

import java.util.Objects;

//Clase Veterinario. Hereda de Persona.
public class Veterinario extends Persona {
    private int matricula;
    private String especialidad;

    //Constructores
    public Veterinario() {
    }

    public Veterinario(String dni, String nombre, String apellido, String telefono, String email, int matricula, String especialidad) {
        super(dni, nombre, apellido, telefono, email);
        this.matricula = matricula;
        this.especialidad = especialidad;
    }

    //Métodos getter y Setter

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    //Metodo toString
    @Override
    public String toString() {
        return "VETERINARIO: " + '\n'+
                super.toString() +
                "Matricula: "+matricula+'\n'+
                "Especialidad: "+especialidad+'\n';
    }
    //Metodo equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Veterinario)) {
            return false;
        }

        Veterinario otro = (Veterinario)obj;

        return Objects.equals(matricula, otro.matricula);
    }
    //Metodo HashCode
    @Override
    public int hashCode() {
        return Objects.hash(getMatricula());
    }
}
