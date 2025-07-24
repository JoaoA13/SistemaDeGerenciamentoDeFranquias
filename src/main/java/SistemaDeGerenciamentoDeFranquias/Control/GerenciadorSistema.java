package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Dono;

public abstract class GerenciadorSistema {

    static private Dono dono = new Dono("João","141279456-05","joao@gmail","12345678");
    //protected GerenciadorDeLojas gerenciadorDeLojas = new GerenciadorDeLojas();

    public GerenciadorSistema(){
    }

    String login(String nome,String senha) throws LoginException {
        return null;
    }

    static public Dono getDono(){
        return dono;
    }
}