package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

import java.math.BigDecimal;

public class ValidadorBigDecimal implements ValidadorNumerico{
    public static BigDecimal valida(String entrada, String tipo) throws EntradaException {
        try {
            if (!entrada.matches("\\d+(\\.\\d+)?")) {
                throw new EntradaException(tipo + ". Digite apenas números válidos");
            }
            BigDecimal valor = new BigDecimal(entrada.replace(",", "."));
            return valor;
        } catch (NumberFormatException e) {
            throw new EntradaException("Valor inválido. Digite apenas números, usando ponto ou vírgula como separador decimal.");
        }
    }
}
