package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.GerenciadorDeLojas;

public class ValidadorCpfGerenteBancoDeDadosTrue implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso NÃO EXITA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(GerenciadorDeLojas.getGerente(cpf) == null)
            throw new BancoDeDadosException("Esse Cpf não está cadastrado como gerente");
        else
            return true;
    }
}