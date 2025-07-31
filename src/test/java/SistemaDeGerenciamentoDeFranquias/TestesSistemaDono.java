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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestesSistemaDono extends Testes{


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

    /// TESTE SISTEMA DONO
    // - TESTE SISTEMA DONO - dados validos
    @Test
    void testCadastroLojaDadosValidos() throws CadastroException {
        Gerente gerente = new Gerente("j","09876543211","eita@gmail.com","senha123");
        GerenciadorDeLojas.cadastraGerente("09876543211",gerente);
        sistemaDono.cadastroLoja("Rua jose", "080", "09876543211");

        Loja loja = GerenciadorDeLojas.getLoja("09876543211");
        assertNotNull(loja);
        assertEquals("Rua jose", loja.getEndereco());

        loja = null;
        loja = GerenciadorDeLojas.getLoja("080");
        assertEquals("Rua jose", loja.getEndereco());
    }

    @Test
    void testCadastroGerenteDadosValidos() throws CadastroException {
        sistemaDono.cadastroGerente("jose", "09876543211", "12345678","jos@gmail.com");

        Gerente gerente = GerenciadorDeLojas.getGerente("09876543211");
        assertNotNull(gerente);
        assertEquals("jose", gerente.getNome());
    }

    @Test
    void testCadastroDonoDadosValidos() throws CadastroException {
        sistemaDono.cadastroDono("Maria", "12345678901", "senha123", "maria@email.com");
        Dono dono = GerenciadorSistemaDono.getDono("12345678901");

        assertNotNull(dono);
        assertEquals("Maria", dono.getNome());
    }

    @Test
    void testExcluirLojaDadosValidos() throws EntradaException {
        Gerente gerente = new Gerente("j","09876543211","eita@gmail.com","senha123");
        GerenciadorDeLojas.cadastraGerente("09876543211",gerente);
        sistemaDono.cadastroLoja("Rua jose", "080", "09876543211");

        sistemaDono.excluirLoja("080");

        Loja loja = GerenciadorDeLojas.getLoja("09876543211");
        assertEquals(null,loja);

        loja = null;
        loja = GerenciadorDeLojas.getLoja("080");
        assertEquals(null,loja);
    }

    @Test
    void testExcluirGerenteDadosValidos() throws EntradaException {
        sistemaDono.cadastroGerente("jose", "09876543211", "12345678","jos@gmail.com");
        sistemaDono.excluirGerente("09876543211");
        Gerente gerente = GerenciadorDeLojas.getGerente("09876543211");
        assertEquals(null,gerente);
    }

    @Test
    void testEditarLojaDadosValidos() throws EntradaException {
        Gerente gerente = new Gerente("j","09876543211","eita@gmail.com","senha123");
        GerenciadorDeLojas.cadastraGerente("09876543211",gerente);
        Gerente gerente1 = new Gerente("g","67867867867","eita@gmail.com","senha123");
        GerenciadorDeLojas.cadastraGerente("67867867867",gerente1);
        sistemaDono.cadastroLoja("Rua jose", "080", "09876543211");

        Loja loja = GerenciadorDeLojas.getLoja("080");
        assertNotNull(loja);
        assertEquals("Rua jose", loja.getEndereco());

        //teste troca de enderço
        sistemaDono.editarLoja("Rua maria","","","080");
        assertEquals("Rua maria", loja.getEndereco());

        //teste troca de gerente
        sistemaDono.editarLoja("","67867867867","","080");
        assertEquals("67867867867", loja.getCpfGerente());

        //teste troca de código
        sistemaDono.editarLoja("","","090","080");
        assertEquals("090", loja.getCodigo());
    }

    @Test
    void testEditarGerenteDadosValidos() throws EntradaException {
        Gerente gerente = new Gerente("j","09876543211","eita@gmail.com","senha123");
        GerenciadorDeLojas.cadastraGerente("09876543211",gerente);

        //teste troca de nome
        sistemaDono.editarGerente("maria","","","","09876543211");
        assertEquals("maria", gerente.getNome());

        //teste troca de email
        sistemaDono.editarGerente("","","nossa@email.br","","09876543211");
        assertEquals("nossa@email.br", gerente.getEmail());

        //teste troca de senha
        sistemaDono.editarGerente("","","","321ahnes","09876543211");
        assertEquals("321ahnes", gerente.getSenha());

        //teste troca de cpf
        sistemaDono.editarGerente("","67867867867","","","09876543211");
        assertEquals("67867867867", gerente.getCpf());
    }

    @Test
    void testEditarDonoDadosValidos() throws EntradaException {
        sistemaDono.cadastroDono("jos","78901234567","senha123","eita@gmail.com");
        Dono dono = sistemaDono.getDono("78901234567");

        //teste troca de nome
        sistemaDono.editarDono("maria","","","","78901234567");
        assertEquals("maria", dono.getNome());

        //teste troca de email
        sistemaDono.editarDono("","","nossa@email.br","","78901234567");
        assertEquals("nossa@email.br", dono.getEmail());

        //teste troca de senha
        sistemaDono.editarDono("","","","321ahnes","78901234567");
        assertEquals("321ahnes", dono.getSenha());

        //teste troca de cpf
        sistemaDono.editarDono("","67867867867","","","78901234567");
        assertEquals("67867867867", dono.getCpf());
    }

    // - TESTE SISTEMA DONO - dados incorretos
    @Test
    void testCadastroDonoComCpfRepetidoLancaExcecao() throws CadastroException {
        sistemaDono.cadastroDono("Carlos", "11122233344", "senha123", "carlos@email.com");

        CadastroException ex = assertThrows(CadastroException.class, () -> {
            sistemaDono.cadastroDono("Outro", "11122233344", "senha123", "outro@email.com");
        });

        assertEquals("Esse Cpf já está cadastrado", ex.getMessage());
    }

    @Test
    void testLoginComSenhaErrada() throws CadastroException {
        sistemaDono.cadastroDono("Ana", "99988877766", "senha123", "ana@email.com");

        assertThrows(LoginException.class, () -> {
            sistemaDono.login("99988877766", "senhaErrada");
        });
    }

    @Test
    void testExclusaoDeDono() throws CadastroException, EntradaException {
        sistemaDono.cadastroDono("Lucas", "10101010100", "senha123", "lucas@email.com");

        assertNotNull(GerenciadorSistemaDono.getDono("10101010100"));

        sistemaDono.excluirDono("10101010100");

        assertNull(GerenciadorSistemaDono.getDono("10101010100"));
    }

    @Test
    void testEditarNomeDono() throws EntradaException {
        sistemaDono.cadastroDono("João", "88877766655", "senha123", "joao@email.com");

        sistemaDono.editarDono("João Silva","","","","88877766655");

        assertEquals("João Silva", GerenciadorSistemaDono.getDono("88877766655").getNome());
    }
}
