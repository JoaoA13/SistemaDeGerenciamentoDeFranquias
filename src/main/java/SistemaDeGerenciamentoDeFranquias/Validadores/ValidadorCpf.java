package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;

public class ValidadorCpf implements ValidadorLogin{
        static public void validarCpf(String cpf) throws LoginException {
        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            throw new CpfInvalidoException("Cpf inválido");
        else
            return;
    }
}