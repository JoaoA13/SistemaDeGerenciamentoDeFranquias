package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorCodigoProdutoBancoDeDadosFalse implements ValidadorBancoDeDados {
    /// Retorna exceção(false) caso JÁ EXITA no banco de dados
    public static boolean valida(String codigo, String cpfGerente)  throws BancoDeDadosException {
        Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            if (loja.getProduto(codigo) != null)
                throw new BancoDeDadosException("Esse Produto já está cadastrado");
        else
            return true;
    }
}
