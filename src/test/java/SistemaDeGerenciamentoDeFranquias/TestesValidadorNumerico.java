package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.SenhaInvalidaException;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.ValidadorSenha;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorBigDecimal;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorPrecoPositivo;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorQuantidadeValida;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorValorNaoNegativo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestesValidadorNumerico {
    @BeforeEach
    void setUp() {}

    @Test
    void naoDeveLancarExcecaoParaNumeroInvalido() throws EntradaException {
        BigDecimal valor = new BigDecimal(1);
        assertEquals(valor, ValidadorBigDecimal.valida("1","m"));
    }

    @Test
    void deveLancarExcecaoParaNumeroInvalido() {
        assertThrows(EntradaException.class, () -> ValidadorBigDecimal.valida("abc","m"));
    }

    @Test
    void validaPrecoPositivo() throws EntradaException {
        BigDecimal valor = new BigDecimal(1);
        assertEquals(valor, ValidadorPrecoPositivo.valida("1"));
    }

    @Test
    void deveLancarExcecaoParaPrecoNegativo() {
        assertThrows(EntradaException.class, () -> ValidadorPrecoPositivo.valida("-1"));
    }

    @Test
    void validaQuantidadeInvalida() throws EntradaException {
        Produto produto = new Produto("produto",BigDecimal.ONE,"uma caracteristica do produto",BigDecimal.TWO,"000");
        assertThrows(EntradaException.class, () -> ValidadorQuantidadeValida.validar(BigDecimal.TEN,produto));
    }

    @Test
    void retornaValorValido() throws EntradaException {
        BigDecimal valor = new BigDecimal(1);
        assertEquals(valor,ValidadorValorNaoNegativo.valida("1"));
    }

    @Test
    void lancaExceptionParaValorNegativo() throws EntradaException {
        assertThrows(EntradaException.class, () -> ValidadorValorNaoNegativo.valida("-1"));
    }
}
