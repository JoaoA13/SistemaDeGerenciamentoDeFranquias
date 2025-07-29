package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorCodigoProdutoBancoDeDadosTrue implements ValidadorBancoDeDados {
    public static boolean valida(String codigo, String cpfGerente)  throws BancoDeDadosException {
        Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
        if (loja.getProduto(codigo) == null)
            throw new BancoDeDadosException("Esse não está cadastrado");
        else
            return true;
    }
}
