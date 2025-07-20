package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.SenhaInvalidaException;

public class ValidadorSenha {
    static public void valida(String senha) throws SenhaInvalidaException {
        if (senha.length() < 8)
            throw new SenhaInvalidaException("Senha Inválida");

    }
}