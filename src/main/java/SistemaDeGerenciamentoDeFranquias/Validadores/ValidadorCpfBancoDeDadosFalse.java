package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

import java.util.Map;


public class ValidadorCpfBancoDeDadosFalse implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso JÁ EXITA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(Loja.getVendedor(cpf) != null)
            throw new BancoDeDadosException("Esse Cpf já está cadastrado");
        else if(GerenciadorDeLojas.getGerente(cpf) != null || cpf.equals(GerenciadorSistema.getDono().getCpf()) )
            throw new BancoDeDadosException("Esse Cpf já está cadastrado");
        else
            return true;
    }

}
