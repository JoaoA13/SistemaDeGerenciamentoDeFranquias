package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EmailInválidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

public interface ValidadorEmail {
    static public void valida(String email) throws EntradaException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailInválidoException("E-mail está vazio.");
        }
        email = email.trim();

        if (!email.contains("@") || !email.contains(".")) {
            throw new EmailInválidoException("E-mail deve ter '@' e '.'");
        }

        int posArroba = email.indexOf("@");
        int posPonto = email.lastIndexOf(".");
        if (posArroba <= 0 || posPonto < posArroba + 2 || posPonto == email.length() - 1) {
            throw new EmailInválidoException("Formato de e-mail inválido.");
        }
    }
}
