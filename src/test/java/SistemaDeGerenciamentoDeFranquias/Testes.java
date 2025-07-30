package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Dono;
import org.junit.jupiter.api.*;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Testes {

    /// TESTE SISTEMA DONO
    private GerenciadorSistemaDono sistema;

    @BeforeEach
    void setUp() {
        sistema = new GerenciadorSistemaDono();
        GerenciadorSistemaDono.getDonos().clear(); // limpando antes de cada teste
    }

    @Test
    void testCadastroDonoComDadosValidos() throws CadastroException {
        sistema.cadastroDono("Maria", "12345678901", "senha123", "maria@email.com");
        Dono dono = GerenciadorSistemaDono.getDono("12345678901");

        assertNotNull(dono);
        assertEquals("Maria", dono.getNome());
    }

    @Test
    void testCadastroDonoComCpfRepetidoLancaExcecao() throws CadastroException {
        sistema.cadastroDono("Carlos", "11122233344", "senha123", "carlos@email.com");

        CadastroException ex = assertThrows(CadastroException.class, () -> {
            sistema.cadastroDono("Outro", "11122233344", "senha123", "outro@email.com");
        });

        assertEquals("Esse Cpf já está cadastrado", ex.getMessage());
    }

    @Test
    void testLoginComDadosValidos() throws CadastroException, LoginException {
        sistema.cadastroDono("Ana", "99988877766", "senha123", "ana@email.com");

        String resultado = sistema.login("99988877766", "senha123");
        assertEquals("CPF e senha corretos", resultado);
    }

    @Test
    void testLoginComSenhaErrada() throws CadastroException {
        sistema.cadastroDono("Ana", "99988877766", "senha123", "ana@email.com");

        assertThrows(LoginException.class, () -> {
            sistema.login("99988877766", "senhaErrada");
        });
    }

    @Test
    void testExclusaoDeDono() throws CadastroException, EntradaException {
        sistema.cadastroDono("Lucas", "10101010100", "senha123", "lucas@email.com");

        assertNotNull(GerenciadorSistemaDono.getDono("10101010100"));

        sistema.excluirDono("10101010100");

        assertNull(GerenciadorSistemaDono.getDono("10101010100"));
    }

    @Test
    void testEditarNomeDono() throws EntradaException {
        sistema.cadastroDono("João", "88877766655", "senha123", "joao@email.com");

        sistema.editarDono("João Silva","","","","88877766655");

        assertEquals("João Silva", GerenciadorSistemaDono.getDono("88877766655").getNome());
    }

//    @Test
//    void testEditarEmailComValorVazio() throws CadastroException {
//        sistema.cadastroDono("Clara", "55544433322", "senha123", "clara@email.com");
//
//        EntradaException ex = assertThrows(EntradaException.class, () -> {
//            sistema.editarDono("","","","","88877766655");
//        });
//
//        assertEquals("Campo vazio!", ex.getMessage());
//    }
}
