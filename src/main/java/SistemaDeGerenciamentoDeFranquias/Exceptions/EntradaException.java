package SistemaDeGerenciamentoDeFranquias.Exceptions;

public class EntradaException extends Exception {
    public EntradaException(String message) {
        super(message);
    }

    public EntradaException(EntradaException ex) {
    }
}
