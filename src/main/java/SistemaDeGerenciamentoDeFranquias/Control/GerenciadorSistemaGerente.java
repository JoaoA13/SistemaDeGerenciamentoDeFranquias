package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpfBancoDeDadosTrue;

import javax.swing.*;

public class GerenciadorSistemaGerente extends GerenciadorSistema{

    static GerenciadorDeLojas listaLojas = new GerenciadorDeLojas();

    public String login(String cpf, String senha) throws LoginException {
        super.login(cpf, senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        } catch (EntradaException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            buscaCpfExiste(cpf);
            ValidadorLogin.valida(GerenciadorDeLojas.getGerente(cpf),cpf,senha);
            return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    public String lancarCadastro(String nome, String cpf, String senha, String cpfGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nome);
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);

            ValidadorNome.validarNome(nome);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        } catch (EntradaException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        Loja loja = listaLojas.getLoja(cpfGerente);
        loja.addVendedor(nome, cpf, senha);
        return "Vendedor Cadastrado";
    }

    public String excluirVendedor(String cpf, String cpfGerente) throws EntradaException {
        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCpf.validarCpf(cpf);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            ValidadorCpfVendedorBancoDeDadosTrue.valida(cpf);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        Loja loja = listaLojas.getLoja(cpfGerente);
        loja.excluirVendedor(cpf);

        return "Vendedor Excluído com Sucesso";
    }

    public JScrollPane listaDeVendedores(String cpfGerente){
        String[] colunas = {"Nome", "CPF"};
        Loja loja = listaLojas.getLoja(cpfGerente);

        String[][] dados = new String[loja.getArmazenaVendedores().size()][2];
        int i = 0;
        for (Vendedor v : loja.getArmazenaVendedores().values()) {
            dados[i][0] = v.getNome();
            dados[i][1] = v.getCpf();
            i++;
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scroll = new JScrollPane(tabela);

        return scroll;
    }


    static void cadastrarVendedor(String nome, String cpf, String senha){

    }
}
