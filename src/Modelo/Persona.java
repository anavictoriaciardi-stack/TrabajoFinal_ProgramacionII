package Modelo;

//Clase abstracta para que hereden Cliente y Veterinario

public abstract class Persona {
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String telefono;
    protected String email;

    //Constructores
    public Persona() {
    }

    public Persona(String dni, String nombre, String apellido, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    //Metodos getters y setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //metodo ToString

    @Override
    public String toString() {
        return "DNI: "+dni+ '\n'+
                "Nombre y Apellido: "+nombre+" "+apellido+ '\n'+
                "Telefono: "+telefono+ '\n'+
                "email: "+email+ '\n';
    }
}
