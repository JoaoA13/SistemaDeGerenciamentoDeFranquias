package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorSenha;

public class GerenciadorSistemaVendedor extends GerenciadorSistema{

    String login(String cpf,String senha) throws LoginException {
        super.login(cpf,senha);

        try {
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
            ValidadorLogin.valida(dono,cpf,senha);
            return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }
}
