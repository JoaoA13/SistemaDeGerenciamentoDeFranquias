package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;

public class GerenciadorSistemaGerente extends GerenciadorSistema{
    String login(String cpf,String senha) throws LoginException {
        super.login(cpf,senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
        }catch (EntradaException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpf.validarCpf(cpf);
            buscaGerente(cpf);
            ValidadorSenha.valida(senha);
            ValidadorLogin.valida(GerenciadorDeLojas.getGerente(cpf),cpf,senha);
            return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    static public void buscaGerente(String cpf) throws LoginException{

        try {
            ValidadorCpfGerenteBancoDeDadosTrue.valida(cpf);
        }catch (BancoDeDadosException e){
            throw new LoginException(e.getMessage());
        }
    }
}
