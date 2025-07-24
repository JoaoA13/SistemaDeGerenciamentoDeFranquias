package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;

public interface ValidadorBancoDeDados {
    static public boolean valida()  throws BancoDeDadosException {
        return false;
    }
}
