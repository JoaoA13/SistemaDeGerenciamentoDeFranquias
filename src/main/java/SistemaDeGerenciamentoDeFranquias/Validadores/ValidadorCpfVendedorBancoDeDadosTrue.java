package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import java.util.Map;

public class ValidadorCpfVendedorBancoDeDadosTrue implements ValidadorBancoDeDados{
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(Loja.getVendedor(cpf) == null)
            throw new BancoDeDadosException("Esse Cpf não está cadastrado");
        else
            return true;
    }
}
