package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaVendedor;
import SistemaDeGerenciamentoDeFranquias.Exceptions.*;
import SistemaDeGerenciamentoDeFranquias.Model.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoLojaBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoPedidoBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorSenha;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public abstract class Testes {

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

    /*@Test
    void testLoginComDadosValidos() throws CadastroException, LoginException {
        sistemaDono.cadastroDono("Ana", "99988877766", "senha123", "ana@email.com");
        String resultado = sistemaDono.login("99988877766", "senha123");
        assertEquals("CPF e senha corretos", resultado);

        sistemaDono.cadastroGerente("josias","12345678900","senha123","eita@gmail.com");
        String resultado2 = sistemaGerente.login("12345678900", "senha123");
        assertEquals("CPF e senha corretos", resultado2);

        gerenciaLojas.cadastraLoja("rua dos bobos","070",GerenciadorDeLojas.getGerente("12345678900"));
        sistemaGerente.lancarCadastro("luis","12312312312","l@gmail.com","12345678","12345678900");
        String resultado3 = sistemaVendedor.login("12312312312", "12345678");
        assertEquals("CPF e senha corretos", resultado3);
    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// VALIDADOR BANCO DE DADOS ///////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// ValidadorCpfBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {
        Dono dono = new Dono("João", "12345678900", "joao@gmail.com", "12345678");
        GerenciadorSistemaDono.getDonos().put(dono.getCpf(), dono);

        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCpfBancoDeDadosFalse.valida("12345678900"));
    }

    // valida //
    @Test
    void deveAceitarCpfNovo() {
        assertDoesNotThrow(() -> ValidadorCpfBancoDeDadosFalse.valida("00000000191"));
    }


    /// ValidadorCpfBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDaLojaJaExiste() {
        Gerente gerente = new Gerente("Nalon", "12345678900", "nalon@gmail.com", "12345678");
        GerenciadorDeLojas.cadastraLoja("Avenida Paulista", "123", gerente);

        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoLojaBancoDeDadosFalse.valida("123"));
    }

    // valida //
    @Test
    void deveAceitarOCodigoNovo() {
        assertDoesNotThrow(() -> ValidadorCodigoLojaBancoDeDadosFalse.valida("123"));
    }

    /// ValidadorCodigoPedidoBancoDeDadosFalse ///

    // não valida //
    @Test
    void deveLancarExcecaoQuandoOCodigoDoPedidoJaExiste() {
        /*String dataTexto = "31/05/2023";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataTexto, formatter1);

        String horaTexto = "22:30";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("hh:mm");
        LocalTime hora = LocalTime.parse(horaTexto, formatter2);

        Vendedor vendedor = new Vendedor("Nalon","12345678900", "nalon@gmail.com", "12345678", "111");
        Pedido pedido = new Pedido("123", "Nalon", data, hora, "Pix", new BigDecimal("100"), "12345678900");
        vendedor.addPedido(pedido);*/

        assertThrows(BancoDeDadosException.class, () ->
                ValidadorCodigoPedidoBancoDeDadosFalse.valida("111"));
    }

    // valida //
    @Test
    void deveAceitarOCodigoDoPedido() {
        assertDoesNotThrow(() -> ValidadorCodigoLojaBancoDeDadosFalse.valida("123"));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// VALIDADOR BANCO DE DADOS ///////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void deveAceitarCpfValido() {
        assertDoesNotThrow(() -> ValidadorCpf.validarCpf("12345678909"));
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        assertThrows(CpfInvalidoException.class, () -> ValidadorCpf.validarCpf("11111111111"));
    }

    @Test
    void deveAceitarSenhaValida() {
        assertDoesNotThrow(() -> ValidadorSenha.valida("12345678"));
    }

    @Test
    void deveLancarExcecaoParaSenhaCurta() {
        assertThrows(SenhaInvalidaException.class, () -> ValidadorSenha.valida("123"));
        assertThrows(SenhaInvalidaException.class, () -> ValidadorSenha.valida("    "));
    }

//    @Test
//    void testEditarEmailComValorVazio() throws CadastroException {
//        sistemaDono.cadastroDono("Clara", "55544433322", "senha123", "clara@email.com");
//
//        EntradaException ex = assertThrows(EntradaException.class, () -> {
//            sistemaDono.editarDono("","","","","88877766655");
//        });
//
//        assertEquals("Campo vazio!", ex.getMessage());
//    }
}
