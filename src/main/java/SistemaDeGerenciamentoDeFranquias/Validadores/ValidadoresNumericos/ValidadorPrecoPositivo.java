package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

import java.math.BigDecimal;

public class ValidadorPrecoPositivo implements ValidadorNumerico{
    public static BigDecimal valida(String entrada) throws EntradaException {
        try {
            BigDecimal valor = new BigDecimal(entrada.replace(",", "."));
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new EntradaException("O valor deve ser um número positivo maior que zero.");
            }
            return valor;
        } catch (NumberFormatException e) {
            throw new EntradaException("Valor inválido. Digite apenas números, usando ponto ou vírgula como separador decimal.");
        }
    }
}
