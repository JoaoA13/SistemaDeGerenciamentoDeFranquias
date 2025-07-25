package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.SenhaInvalidaException;

public class ValidadorSenha implements ValidadorLogin{
    static public void valida(String senha) throws EntradaException {
        if (senha.length() < 8)
            throw new SenhaInvalidaException("Senha Inválida");

        if (senha.length() < 8)
            throw new SenhaInvalidaException("Senha Inválida");
    }
}