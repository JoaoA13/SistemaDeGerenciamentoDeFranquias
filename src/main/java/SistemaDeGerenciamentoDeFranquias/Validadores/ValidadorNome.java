package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;

public class ValidadorNome implements ValidadorLogin{
    static public void validarNome(String nome) throws LoginException {
        if (nome.length() < 2 || !nome.matches("^[a-zA-ZÀ-ÿ\\s]{3,}$"))
            throw new CpfInvalidoException("Nome inválido");
        else
            return;
    }
}
