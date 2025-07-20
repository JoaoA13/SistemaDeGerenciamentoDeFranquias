package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    public GerenciadorSistemaDono(){
    }
    @Override
    boolean login(String nome,String senha) {
        super.login(nome,senha);

        try {
            ValidadorLogin.validarLogin(dono,nome,senha);
            return true;
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            return false;
        }
    }


}
