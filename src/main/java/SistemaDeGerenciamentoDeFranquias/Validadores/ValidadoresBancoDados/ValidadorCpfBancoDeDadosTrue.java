package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorCpfBancoDeDadosTrue implements ValidadorBancoDeDados {

    /// Retorna exceção(false) caso NÃO EXISTA no banco de dados, É TRUE SE EXISTE NO BANCO DE DADOS
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        cpf = cpf.replaceAll("[^\\d]", "");
        for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
            if(loja == null)
                continue;
            if (loja.getVendedor(cpf) != null)
                return true;
        }
        if(GerenciadorDeLojas.getGerente(cpf) != null || GerenciadorSistemaDono.getDono(cpf) != null)
            return true;
        else
            throw new BancoDeDadosException("Esse Cpf não está cadastrado");
    }
}