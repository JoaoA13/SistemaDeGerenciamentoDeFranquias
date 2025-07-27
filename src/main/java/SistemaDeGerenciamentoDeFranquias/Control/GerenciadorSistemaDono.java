package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.*;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    private String senhaGerentePadrão = "12345678";

    public GerenciadorSistemaDono(){
    }
    @Override
    public String login(String cpf, String senha) throws LoginException{
        super.login(cpf,senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        }catch (EntradaException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorLogin.valida(getDono(),cpf,senha);
                    return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    public void cadastroLoja(String endereco, String cpfGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(endereco);
            ValidadorCampoVazio.valida(cpfGerente);

            ValidadorCpf.validarCpf(cpfGerente);

        }catch (EntradaException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpfGerente);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        if(GerenciadorDeLojas.getLoja(cpfGerente) != null)
            throw new CadastroException("Esse gerente já possui uma unidade");


        GerenciadorDeLojas.cadastraLoja(endereco,GerenciadorDeLojas.getGerente(cpfGerente));
    }

    public void cadastroGerente(String nomeGerente, String cpfGerente, String emailGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nomeGerente);
            ValidadorCampoVazio.valida(cpfGerente);
            ValidadorCampoVazio.valida(emailGerente);

            ValidadorNome.validarNome(nomeGerente);
            ValidadorCpf.validarCpf(cpfGerente);
            ValidadorEmail.valida(emailGerente);

        }catch (EntradaException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpfGerente);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        Gerente gerente = new Gerente(nomeGerente,cpfGerente,emailGerente,senhaGerentePadrão);
        GerenciadorDeLojas.cadastraGerente(gerente.getCpf(),gerente);
    }

    static public String excluirLoja(String cpfGerente) throws EntradaException{
        try {
            ValidadorCampoVazio.valida(cpfGerente);
            ValidadorCpf.validarCpf(cpfGerente);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpfGerente);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        GerenciadorDeLojas.excluirLoja(cpfGerente);

        return "Loja excluída com Sucesso";
    }

    static public String excluirGerente(String cpf) throws EntradaException{
        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCpf.validarCpf(cpf);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpf);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        GerenciadorDeLojas.excluirGerente(cpf);

        return "Gerente excluído com Sucesso";
    }
}
