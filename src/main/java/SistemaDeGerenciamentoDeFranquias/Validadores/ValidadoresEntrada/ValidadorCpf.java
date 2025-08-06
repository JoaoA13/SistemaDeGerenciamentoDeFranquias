package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;

public class ValidadorCpf implements ValidadorLogin {
        static public void validarCpf(String cpf) throws EntradaException {
        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            throw new CpfInvalidoException("Cpf inválido");

    }
}