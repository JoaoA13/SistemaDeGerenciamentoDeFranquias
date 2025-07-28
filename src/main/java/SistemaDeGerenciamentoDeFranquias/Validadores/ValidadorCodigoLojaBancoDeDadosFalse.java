package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

public class ValidadorCodigoLojaBancoDeDadosFalse implements ValidadorBancoDeDados{
    static public boolean valida(String codigo)  throws BancoDeDadosException {
        codigo = codigo.replaceAll("[^\\d]", "");
        if(GerenciadorDeLojas.getLoja(codigo) != null)
            throw new BancoDeDadosException("Já existe uma loja com esse código");
        else
            return true;
    }
}
