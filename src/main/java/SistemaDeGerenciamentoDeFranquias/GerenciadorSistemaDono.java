package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    public GerenciadorSistemaDono(){
    }
    @Override
    boolean login(String cpf,String senha) {
        super.login(cpf,senha);

        try {
            ValidadorLogin.validarLogin(dono,cpf,senha);
            return true;
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            return false;
        }
    }


}
