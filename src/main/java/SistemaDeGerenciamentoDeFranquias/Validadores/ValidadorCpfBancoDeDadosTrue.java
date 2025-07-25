package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorCpfBancoDeDadosTrue implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso NÃO EXISTA no banco de dados, É TRUE SE EXISTE NO BANCO DE DADOS
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(GerenciadorDeLojas.getGerente(cpf) != null || cpf.equals(GerenciadorSistema.getDono().getCpf()))
            return true;
        else
            throw new BancoDeDadosException("Esse Cpf não está cadastrado");
    }
}