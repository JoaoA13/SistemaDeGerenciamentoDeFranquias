package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.*;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorBigDecimal;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorPrecoPositivo;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorQuantidadeValida;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorValorNaoNegativo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestesValidadorEntrada {
    @BeforeEach
    void setUp() {}

    //validador campo vazio
    @Test
    void deveLancarExcecaoQuandoCampoForVazio() {
        assertThrows(CampoVazioException.class, () -> {
            ValidadorCampoVazio.valida("   ");
        });
    }

    @Test
    void naoDeveLancarExcecaoQuandoCampoForValido() {
        assertDoesNotThrow(() -> {
            ValidadorCampoVazio.valida("Texto válido");
        });
    }

    //validador endereço
    @Test
    void deveLancarExcecaoSeEnderecoTiverMenosDe10CaracteresValidos() {
        String entrada = "Rua 1";
        String campo = "Endereço";

        assertThrows(EntradaException.class, () -> {
            ValidadorCaracEndereco.validarTexto(entrada, campo);
        });
    }

    @Test
    void naoDeveLancarExcecaoSeEnderecoTiver10OuMaisCaracteresValidos() {
        String entrada = "Rua das Flores, 123";
        String campo = "Endereço";

        assertDoesNotThrow(() -> {
            String resultado = ValidadorCaracEndereco.validarTexto(entrada, campo);
            assertEquals("Rua das Flores 123", resultado);
        });
    }

    //validador codigo
    @Test
    void lancaExceptionCodigoInvalido(){
        assertThrows(EntradaException.class, () -> {ValidadorCodigo.validarCodigo("12A");});
    }

    @Test
    void naoLancaExceptionCodigoInvalido(){
        assertDoesNotThrow(() -> {ValidadorCodigo.validarCodigo("123");});
    }

    //validador cpf
    @Test
    void deveAceitarCpfValido() {
        assertDoesNotThrow(() -> ValidadorCpf.validarCpf("12345678909"));
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        assertThrows(CpfInvalidoException.class, () -> ValidadorCpf.validarCpf("11111111111"));
    }

    //validador nome
    @Test
    void deveAceitarNomeVlaido() {
        assertDoesNotThrow(() -> ValidadorNome.valida("João"));
    }

    @Test
    void deveLancarExcecaoParaNomeInvalido() {
        assertThrows(NomeInvalidoException.class, () -> ValidadorNome.valida("123"));
        assertThrows(NomeInvalidoException.class, () -> ValidadorNome.valida("a"));
    }

    //validador senha
    @Test
    void deveAceitarSenhaValida() {
        assertDoesNotThrow(() -> ValidadorSenha.valida("12345678"));
    }

    @Test
    void deveLancarExcecaoParaSenhaCurta() {
        assertThrows(SenhaInvalidaException.class, () -> ValidadorSenha.valida("123"));
        assertThrows(SenhaInvalidaException.class, () -> ValidadorSenha.valida("    "));
    }

    //validador email
    @Test
    void deveAceitarEmailValido() {
        assertDoesNotThrow(() -> ValidadorEmail.valida("joao@gmail.com"));
    }

    @Test
    void deveLancarExcecaoParaEmailInvalido() {
        assertThrows(EmailInválidoException.class, () -> ValidadorEmail.valida("joao.com"));
        assertThrows(EmailInválidoException.class, () -> ValidadorEmail.valida("joao@gmail"));
        assertThrows(EmailInválidoException.class, () -> ValidadorEmail.valida("joao"));
    }

    //validador hora
    @Test
    public void deveValidarHoraCorreta() {
        assertDoesNotThrow(() -> {
            LocalTime hora = ValidadorHora.validarHora("08:30");
            assertEquals(LocalTime.of(8, 30), hora);});
    }

    @Test
    public void deveLancarExcecaoParaHoraInvalida() {
        assertThrows(EntradaException.class, () -> {ValidadorHora.validarHora("02:00");});
    }

    //validador data
    @Test
    public void deveValidarDataCorreta() {
        assertDoesNotThrow(() -> {
            LocalDate data = ValidadorData.validarData("15/08/2020");
            assertEquals(LocalDate.of(2020, 8, 15), data);
        });
    }

    @Test
    public void deveLancarExcecaoParaDataForaDoIntervalo() {
        assertThrows(EntradaException.class, () -> {
            ValidadorData.validarData("31/12/2029"); // depois de 01/09/2025
        });
    }

}
