package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Loja;

public class ValidadorCpfBancoDeDadosTrue implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso NÃO EXISTA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        for (Loja loja : GerenciadorDeLojas.getLojas().values()){
//            if(loja.getVendedor(cpf) != null)
//                return ture;
        }
        if(GerenciadorDeLojas.getGerente(cpf) != null || GerenciadorSistema.getDono().getCpf() != null )
            return true;
        else
            throw new BancoDeDadosException("Esse Cpf não está cadastrado como gerente");
    }
}