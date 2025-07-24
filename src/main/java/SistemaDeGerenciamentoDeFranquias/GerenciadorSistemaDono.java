package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    private String senhaGerentePadrão = "12345678";

    public GerenciadorSistemaDono(){
    }
    @Override
    String login(String cpf,String senha) throws LoginException{
        super.login(cpf,senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
        }catch (EntradaException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
            ValidadorLogin.valida(getDono(),cpf,senha);
                    return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    void cadastroLoja(String endereco,String nomeGerente, String cpfGerente, String emailGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(cpfGerente);
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

        GerenciadorDeLojas.cadastraLoja(endereco,gerente);
        GerenciadorDeLojas.cadastraGerente(cpfGerente,gerente);
    }
}
