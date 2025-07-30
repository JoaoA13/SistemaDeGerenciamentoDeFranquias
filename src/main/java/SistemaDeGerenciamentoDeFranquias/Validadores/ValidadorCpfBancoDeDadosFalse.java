package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

import java.util.Map;


public class ValidadorCpfBancoDeDadosFalse implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso JÁ EXITA no banco de dados
    public static boolean valida(String cpf)  throws BancoDeDadosException {
        for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
            if (loja.getVendedor(cpf) != null)
                throw new BancoDeDadosException("Esse Cpf já está cadastrado");
        }
        if(GerenciadorDeLojas.getGerente(cpf) != null || GerenciadorSistemaDono.getDono(cpf) != null )
            throw new BancoDeDadosException("Esse Cpf já está cadastrado");
        else
            return true;
    }

}
