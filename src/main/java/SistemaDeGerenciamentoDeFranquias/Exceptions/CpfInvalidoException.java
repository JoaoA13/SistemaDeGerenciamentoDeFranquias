package SistemaDeGerenciamentoDeFranquias.Exceptions;

public class CpfInvalidoException extends LoginException{
    public CpfInvalidoException(String message) {
        super(message);
    }
}
