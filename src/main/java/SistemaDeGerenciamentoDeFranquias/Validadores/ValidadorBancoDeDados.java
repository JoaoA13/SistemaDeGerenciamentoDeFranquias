package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Usuario;

public interface ValidadorBancoDeDados {
    static public boolean valida()  throws BancoDeDadosException {
        return false;
    }
}
