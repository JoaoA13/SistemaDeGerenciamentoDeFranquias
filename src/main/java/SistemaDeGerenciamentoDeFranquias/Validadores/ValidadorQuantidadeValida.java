package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;

import java.math.BigDecimal;

public class ValidadorQuantidadeValida {
    public static void validar(BigDecimal quantidade, Produto produto) throws EntradaException {
        if (produto.getQuant().compareTo(quantidade) < 0 || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntradaException("Quantidade inválida para o produto " + produto.getCodigoProd());
        }
    }
}
