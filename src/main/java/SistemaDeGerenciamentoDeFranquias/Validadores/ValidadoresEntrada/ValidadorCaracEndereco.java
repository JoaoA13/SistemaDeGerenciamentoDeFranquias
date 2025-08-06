package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

public class ValidadorCaracEndereco  implements ValidadadorEntrada{
    public static String validarTexto(String entrada, String campoNome) throws EntradaException {
        String limpa = entrada.replaceAll("[^\\p{L}\\p{Nd} ]", "").trim();

        if (limpa.length() < 10) {
            throw new EntradaException("O campo \"" + campoNome + "\" deve ter pelo menos " + 10 + " caracteres válidos.");
        }
        return limpa;
    }
}
