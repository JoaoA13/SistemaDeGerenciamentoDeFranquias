package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;

import static SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas.getLoja;

public class ValidadorCpfVendedorBancoDeDadosTrue implements ValidadorBancoDeDados {
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(getLoja(cpf).getVendedor(cpf) == null)
            throw new BancoDeDadosException("Esse Cpf não está cadastrado");
        else
        return true;
    }
}
