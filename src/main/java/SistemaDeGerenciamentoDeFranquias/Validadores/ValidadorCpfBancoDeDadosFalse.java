package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

import java.util.Map;


public class ValidadorCpfBancoDeDadosFalse implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso JÁ EXITA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
//        for (Loja loja : GerenciadorDeLojas.getLojas().values()){
//            if(loja.getVendedor(cpf) != null)
//                throw new BancoDeDadosException("Esse Cpf já está cadastrado como gerente");
  //      }

        if(GerenciadorDeLojas.getGerente(cpf) != null || cpf.equals(GerenciadorSistema.getDono().getCpf()) )
            throw new BancoDeDadosException("Esse Cpf já está cadastrado");
        else
            return true;
    }

}
