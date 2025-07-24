package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Loja;

public class ValidadorCpfBancoDeDadosFalse implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso JÁ EXITA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        for (Loja loja : GerenciadorDeLojas.getLojas().values()){
//            if(loja.getVendedor(cpf) != null)
//                throw new BancoDeDadosException("Esse Cpf já está cadastrado como gerente");
        }

        if(GerenciadorDeLojas.getGerente(cpf) != null || GerenciadorSistema.getDono().getCpf() != null )
            throw new BancoDeDadosException("Esse Cpf já está cadastrado como gerente");
        else
            return true;
    }
}
