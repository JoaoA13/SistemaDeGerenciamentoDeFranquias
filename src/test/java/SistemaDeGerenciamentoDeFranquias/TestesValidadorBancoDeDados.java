package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Control.*;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TestesValidadorBancoDeDados {

    private GerenciadorSistemaDono sistemaDono;
    private GerenciadorSistemaGerente sistemaGerente;
    private GerenciadorSistemaVendedor sistemaVendedor;
    private GerenciadorDeLojas gerenciaLojas;

    @BeforeEach
    void setUp() {
        sistemaDono = new GerenciadorSistemaDono();
        GerenciadorSistemaDono.getDonos().clear();

        sistemaGerente = new GerenciadorSistemaGerente();
        sistemaVendedor = new GerenciadorSistemaVendedor();

        gerenciaLojas = new GerenciadorDeLojas();
        GerenciadorDeLojas.getLojas().clear();
        GerenciadorDeLojas.getGerentes().clear();
        GerenciadorDeLojas.getCodigoPraCpf().clear();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// VALIDADOR BANCO DE DADOS ///////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /// ValidadorCodigoLojaBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDaLojaJaExiste() throws EntradaException {
        Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
        GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "123", gerente);

        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoLojaBancoDeDadosFalse.valida("123"));
        GerenciadorSistemaDono.excluirLoja("123");
    }

    // valida //
    @Test
    void deveAceitarOCodigoNovo() {
        assertDoesNotThrow(() -> ValidadorCodigoLojaBancoDeDadosFalse.valida("123"));
    }


    /// ValidadorCodigoPedidoBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDoPedidoJaExiste() throws EntradaException {
        String dataTexto = "31/05/2023";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataTexto, formatter1);

        String horaTexto = "10:30";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hora = LocalTime.parse(horaTexto, formatter2);

        Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
        GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "111", gerente);
        Loja loja = GerenciadorDeLojas.getLoja("12345678900");
        loja.addVendedor("Heitor", "12345678911","heitor@gmail.com","12345678" );
        Vendedor vendedor = loja.getVendedor("12345678911");
        Pedido pedido = new Pedido("123", "Leozinho", data, hora, "Pix", new BigDecimal("100"), "12345678999");
        vendedor.addPedido(pedido);

        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoPedidoBancoDeDadosFalse.valida("123"));

        GerenciadorSistemaDono.excluirLoja("111");
    }

    // valida //
    @Test
    void deveAceitarOCodigoDoPedido() {
        assertDoesNotThrow(() -> ValidadorCodigoPedidoBancoDeDadosFalse.valida("123"));
    }


    /// ValidadorCodigoProdutoBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDoProdutoJaExiste() throws EntradaException {
        String dataTexto = "31/05/2023";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataTexto, formatter1);

        String horaTexto = "10:30";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hora = LocalTime.parse(horaTexto, formatter2);

        Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
        GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "111", gerente);
        Loja loja = GerenciadorDeLojas.getLoja("12345678900");
        loja.addProduto("Arroz", new BigDecimal(20), "preço a cada 5 kg", new BigDecimal(50), "123");

        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoProdutoBancoDeDadosFalse.valida("123", "12345678900"));

        GerenciadorSistemaDono.excluirLoja("111");
    }

    // valida //
    @Test
    void deveAceitarOCodigoDoProduto() {
        Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
        GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "111", gerente);

        assertDoesNotThrow(() -> ValidadorCodigoProdutoBancoDeDadosFalse.valida("999", "12345678900"));
    }

/// ValidadorCodigoProdutoBancoDeDadosTrue ///

// não valida //
@Test
void deveLancarExcecaoQuandoOCodigoDoProdutoNaoExiste() {
    String dataTexto = "31/05/2023";
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate data = LocalDate.parse(dataTexto, formatter1);

    String horaTexto = "10:30";
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime hora = LocalTime.parse(horaTexto, formatter2);

    Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
    GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "111", gerente);
    Loja loja = GerenciadorDeLojas.getLoja("12345678900");
    loja.addProduto("Arroz", new BigDecimal(20), "preço a cada 5 kg", new BigDecimal(50), "123");

    assertThrows(BancoDeDadosException.class, () ->
            ValidadorCodigoProdutoBancoDeDadosTrue.valida("100", "12345678900"));
}

// valida //
@Test
void deveReconhecerOCodigoDoProdutoComoExistente() {

    String dataTexto = "31/05/2023";
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate data = LocalDate.parse(dataTexto, formatter1);

    String horaTexto = "10:30";
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime hora = LocalTime.parse(horaTexto, formatter2);

    Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
    GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "111", gerente);
    Loja loja = GerenciadorDeLojas.getLoja("12345678900");
    loja.addProduto("Arroz", new BigDecimal(20), "preço a cada 5 kg", new BigDecimal(50), "123");

    assertDoesNotThrow(() -> ValidadorCodigoProdutoBancoDeDadosTrue.valida("123", "12345678900"));
    }


    /// ValidadorCpfBancoDeDadosTrue ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {
        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCpfBancoDeDadosTrue.valida("12345678900"));
    }

    // valida //
    @Test
    void deveAceitarCpfNovo() throws EntradaException {
        Dono dono = new Dono("João", "12345678900", "joao@gmail.com", "12345678");
        GerenciadorSistemaDono.getDonos().put(dono.getCpf(), dono);

        assertDoesNotThrow(() -> ValidadorCpfBancoDeDadosTrue.valida("12345678900"));

        GerenciadorSistemaDono.excluirDono("12345678900");
    }
}