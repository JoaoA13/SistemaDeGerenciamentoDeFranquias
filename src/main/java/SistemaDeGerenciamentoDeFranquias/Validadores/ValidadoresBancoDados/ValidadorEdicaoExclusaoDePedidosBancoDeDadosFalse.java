package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorEdicaoExclusaoDePedidosBancoDeDadosFalse implements ValidadorBancoDeDados {
    static public void valida(String codigo, Loja loja) throws BancoDeDadosException {
        if (loja.getPedidoAltera(codigo) != null) {
            throw new BancoDeDadosException("Já existe uma solicitação para modificar esse pedido, aguarde a resposta");
        }
    }
}
