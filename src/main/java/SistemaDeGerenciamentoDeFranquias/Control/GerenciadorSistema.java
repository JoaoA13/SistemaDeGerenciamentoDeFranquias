package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Dono;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpfBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpfBancoDeDadosTrue;

public abstract class GerenciadorSistema {

    static private Dono dono = new Dono("João","14127945605","joao@gmail","12345678");
    //protected GerenciadorDeLojas gerenciadorDeLojas = new GerenciadorDeLojas();

    public GerenciadorSistema(){
    }

    String login(String nome,String senha) throws LoginException {
        return null;
    }

    static public Dono getDono(){
        return dono;
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