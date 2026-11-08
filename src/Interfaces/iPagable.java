package Interfaces;

public interface iPagable {
    public String procesarPago(double monto, String Formapago) throws PagoInvalidoException;

    public double calcularcosto();
}
