package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;

public class GerenciadorSistemaVendedor extends GerenciadorSistema{

    public String login(String cpf, String senha) throws LoginException {
        super.login(cpf,senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        }catch (EntradaException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpf);
            ValidadorLogin.valida(getDono(),cpf,senha);
            return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }
}
