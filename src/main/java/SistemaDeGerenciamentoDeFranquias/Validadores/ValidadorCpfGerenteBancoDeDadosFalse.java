package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.GerenciadorSistema;

public class ValidadorCpfGerenteBancoDeDadosFalse implements ValidadorBancoDeDados{

    /// Retorna exceção(false) caso JÁ EXITA no banco de dados
    static public boolean valida(String cpf)  throws BancoDeDadosException {
        if(GerenciadorDeLojas.getGerente(cpf) == null || GerenciadorSistema.getDono().getCpf() == null)
            return true;
        else
            throw new BancoDeDadosException("Esse Cpf já está cadastrado como gerente");
    }
}
