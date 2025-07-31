package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaGerente;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaVendedor;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Dono;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

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

    @Test
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
