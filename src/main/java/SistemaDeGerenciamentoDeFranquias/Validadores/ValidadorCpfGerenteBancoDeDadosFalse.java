package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.GerenciadorDeLojas;

public class ValidadorCpfGerenteBancoDeDadosFalse implements ValidadorBancoDeDados{
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(GerenciadorDeLojas.getGerente(cpf) == null)
            return true;
        else
            throw new BancoDeDadosException("Esse Cpf já está cadastrado como gerente");
    }
}
