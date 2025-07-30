package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Dono;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpfBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpfBancoDeDadosTrue;

import java.util.HashMap;
import java.util.Map;

public abstract class GerenciadorSistema {

    //protected GerenciadorDeLojas gerenciadorDeLojas = new GerenciadorDeLojas();

    public GerenciadorSistema(){
    }

    String login(String nome,String senha) throws LoginException {
        return null;
    }

    static public void buscaCpfExiste(String cpf) throws LoginException{
        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpf);
        }catch (BancoDeDadosException e){
            throw new LoginException(e.getMessage());
        }
    }

    static public void buscaCpfNAOExiste(String cpf) throws LoginException{
        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
        }catch (BancoDeDadosException e){
            throw new LoginException(e.getMessage());
        }
    }

}