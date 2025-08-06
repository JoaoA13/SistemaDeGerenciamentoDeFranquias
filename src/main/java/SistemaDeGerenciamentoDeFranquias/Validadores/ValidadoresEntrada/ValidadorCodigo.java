package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

public class ValidadorCodigo {

    public static void validarCodigo(String codigo) throws EntradaException {
        codigo = codigo.trim();

        if (!codigo.matches("\\d+")) {
            throw new EntradaException("O código deve conter apenas números.");
        }
        if (codigo.length() != 3) {
            throw new EntradaException("O código deve conter exatamente 3 dígitos.");
        }
    }
}
