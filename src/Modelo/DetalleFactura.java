package Modelo;

public class DetalleFactura {
    private int id;
    private int facturaId;
    private String concepto;
    private double subtotal;

    public DetalleFactura() {}

    public DetalleFactura(int facturaId, String concepto, double subtotal) {
        this.facturaId = facturaId;
        this.concepto = concepto;
        this.subtotal = subtotal;
    }

    public DetalleFactura(int id, int facturaId, String concepto, double subtotal) {
        this.id = id;
        this.facturaId = facturaId;
        this.concepto = concepto;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFacturaId() { return facturaId; }
    public void setFacturaId(int facturaId) { this.facturaId = facturaId; }
    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "factura: "+facturaId +"- " + concepto + ": $" + subtotal + "\n";
    }
}
