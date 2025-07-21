package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;

public abstract class GerenciadorSistema {

    protected Dono dono = new Dono("João","141279456-05","joao@gmail","12345678");

    public GerenciadorSistema(){
    }

    String login(String nome,String senha) throws LoginException {
        return null;
    }
}