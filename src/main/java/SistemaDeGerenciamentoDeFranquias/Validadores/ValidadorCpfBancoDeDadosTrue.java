package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorCpfBancoDeDadosTrue implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso NÃO EXISTA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        for (Loja loja : GerenciadorDeLojas.getLojas().values()){
            if(Loja.getVendedor(cpf) != null)
                return true;
        }
        if(GerenciadorDeLojas.getGerente(cpf) != null || !cpf.equals(GerenciadorSistema.getDono().getCpf()))
            return true;
        else
            throw new BancoDeDadosException("Esse Cpf não está cadastrado");
    }
}