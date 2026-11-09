package Modelo;

public class SaladeEspera {
    private String dni_cliente;
    private String nombreMascota;
    private int numeroTurno;

    public SaladeEspera(String dni_cliente, String nombreMascota, int numeroTurno) {
        this.dni_cliente = dni_cliente;
        this.nombreMascota = nombreMascota;
        this.numeroTurno = numeroTurno;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    @Override
    public String toString() {
        return "Cliente: " +dni_cliente +'\n'+
                "Mascota: "+nombreMascota+ '\n'+
                "Turno: "+numeroTurno+'\n';
    }
}
