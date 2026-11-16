package Modelo;

import Interfaces.PagoInvalidoException;
import Interfaces.iPagable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Factura implements iPagable {

    private int id;
    private LocalDateTime fechaHora;
    private String cliente_dni;
    private List<DetalleFactura> items; // List para los ítems cobrados (ej. consulta, medicamentos)
    private double montoTotal;
    private String formaPago;
    private boolean activo;

    public Factura(int id, LocalDateTime fechaHora, String cliente_dni,  double montoTotal, String formaPago, boolean activo) {
        this.id = id;
        this.fechaHora = LocalDateTime.now();
        this.cliente_dni = cliente_dni;
        this.items = items;
        this.montoTotal = montoTotal;
        this.formaPago = formaPago;
        this.activo = true;
    }
    public Factura(String cliente,  double montoTotal, String formaPago) {
        this.fechaHora = LocalDateTime.now();
        this.cliente_dni = cliente_dni;
        this.items = items;
        this.montoTotal = montoTotal;
        this.formaPago = formaPago;
        this.activo = true;
    }


    public Factura() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCliente_dni() {
        return cliente_dni;
    }

    public void setCliente_dni(String cliente_dni) {
        this.cliente_dni = cliente_dni;
    }

    public List<DetalleFactura> getItems() {
        return items;
    }

    public void setItems(List<DetalleFactura> items) {
        this.items = items;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public void agregarItem(String concepto, double subtotal) {
        items.add(new DetalleFactura(concepto, subtotal));
        this.montoTotal += subtotal;
    }

    @Override
    public String procesarPago(double monto,String Formapago)throws PagoInvalidoException {
        if (monto < montoTotal) {
            throw new PagoInvalidoException("El monto entregado es menor al total de la factura.");
        }
        return "pago procesado con exito mediante: "+ formaPago;
    }

    @Override
    public double calcularcosto() {

        return this.montoTotal;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", fechaHora=" + fechaHora +
                ", cliente=" + cliente_dni +
                ", items =" + items +
                ", montoTotal=" + montoTotal +
                ", formaPago='" + formaPago + '\'' +
                ", activo=" + activo + "\n"+
                '}';
    }
}
