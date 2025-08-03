package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import java.util.Collection;

public class ValidadorCodigoPedidoBancoDeDadosFalse implements ValidadorBancoDeDados {
    static public boolean valida(String codigo)  throws BancoDeDadosException {
        for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
            for (Vendedor vendedor : (Collection<Vendedor>) loja.getArmazenaVendedores().values()){
                if(vendedor.getPedido(codigo) != null)
                    throw new BancoDeDadosException("Esse codigo de pedido já foi utilizado!");
            }

        }
        return true;
    }
}
