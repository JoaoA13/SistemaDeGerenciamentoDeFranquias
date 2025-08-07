package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Control.*;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.*;
import org.junit.jupiter.api.AfterEach;
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
//        GerenciadorDeLojas.getLojas().clear();
//        GerenciadorDeLojas.getGerentes().clear();
//        GerenciadorDeLojas.getCodigoPraCpf().clear();

        String dataTexto = "31/05/2023";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataTexto, formatter1);

        String horaTexto = "10:30";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hora = LocalTime.parse(horaTexto, formatter2);

        Dono dono = new Dono("João", "12345678900", "joao@gmail.com", "12345678");
        GerenciadorSistemaDono.getDonos().put(dono.getCpf(), dono);
        Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
        GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "111", gerente);
        Loja loja = GerenciadorDeLojas.getLoja("12345678900");
        loja.addVendedor("Heitor", "12345678911","heitor@gmail.com","12345678" );
        Vendedor vendedor = loja.getVendedor("12345678911");
        Pedido pedido = new Pedido("123", "Leozinho", data, hora, "Pix", new BigDecimal("100"), "12345678999");
        vendedor.addPedido(pedido);
        loja.addProduto("Arroz", new BigDecimal(20), "preço a cada 5 kg", new BigDecimal(50), "123");
        loja.addPedidosAltera(pedido,"Pix", "Cartão", 0);

    }

    @AfterEach
    void tearDown() throws EntradaException {
        GerenciadorSistemaDono.excluirLoja("111");
        GerenciadorSistemaDono.excluirDono("12345678900");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// VALIDADOR BANCO DE DADOS ///////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /// ValidadorCodigoLojaBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDaLojaJaExiste() throws EntradaException {
        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoLojaBancoDeDadosFalse.valida("111"));
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
        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoPedidoBancoDeDadosFalse.valida("123"));
    }

    // valida //
    @Test
    void deveAceitarOCodigoDoPedido() {
        assertDoesNotThrow(() -> ValidadorCodigoPedidoBancoDeDadosFalse.valida("999"));
    }


    /// ValidadorCodigoProdutoBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDoProdutoJaExiste() throws EntradaException {
        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoProdutoBancoDeDadosFalse.valida("123", "12345678900"));
    }

    // valida //
    @Test
    void deveAceitarOCodigoDoProduto() {
        assertDoesNotThrow(() -> ValidadorCodigoProdutoBancoDeDadosFalse.valida("999", "12345678900"));
    }


/// ValidadorCodigoProdutoBancoDeDadosTrue ///

// não valida //
@Test
void deveLancarExcecaoQuandoOCodigoDoProdutoNaoExiste() {
    assertThrows(BancoDeDadosException.class, () ->
            ValidadorCodigoProdutoBancoDeDadosTrue.valida("100", "12345678900"));
}

// valida //
@Test
void deveReconhecerOCodigoDoProdutoComoExistente() {
    assertDoesNotThrow(() -> ValidadorCodigoProdutoBancoDeDadosTrue.valida("123", "12345678900"));
    }


    /// ValidadorCpfBancoDeDadosTrue ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {
        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCpfBancoDeDadosTrue.valida("12345678999"));
    }

    // valida //
    @Test
    void deveAceitarCpfNovo() throws EntradaException {
        assertDoesNotThrow(() -> ValidadorCpfBancoDeDadosTrue.valida("12345678900"));
    }


    /// ValidadorEdicaoExclusaoDePedidosBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoJaExisteSolicitacaoDeAlteracao() {
        assertThrows(BancoDeDadosException.class, () ->
                ValidadorEdicaoExclusaoDePedidosBancoDeDadosFalse.valida("123", GerenciadorDeLojas.getLoja("111")));
    }

    // valida //
    @Test
    void deveAceitarPedidoSemSolicitacaoDeAlteracao() {
        assertDoesNotThrow(() -> ValidadorEdicaoExclusaoDePedidosBancoDeDadosFalse.valida("001", GerenciadorDeLojas.getLoja("111")));
    }
}