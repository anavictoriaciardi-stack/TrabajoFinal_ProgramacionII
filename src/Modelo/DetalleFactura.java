package Modelo;

public class DetalleFactura {
    private String concepto;
    private double subtotal;

    public DetalleFactura(String concepto, double subtotal) {
        this.concepto = concepto;
        this.subtotal = subtotal;
    }

    public String getConcepto() { return concepto; }
    public double getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "concepto='" + concepto + '\'' +
                ", subtotal=" + subtotal +
                '}';
    }
}
