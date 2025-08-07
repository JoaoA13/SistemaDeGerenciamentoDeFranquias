package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.SenhaInvalidaException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;

public class ValidadorSenha implements ValidadadorEntrada {
    static public void valida(String senha) throws EntradaException {
        if (senha.length() < 8)
            throw new SenhaInvalidaException("Senha Inválida");

        if (senha.length() < 8)
            throw new SenhaInvalidaException("Senha Inválida");
    }
}