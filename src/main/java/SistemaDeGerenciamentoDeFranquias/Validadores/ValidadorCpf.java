package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;

public interface ValidadorCpf {
    static public void validarCpf(String cpf) throws CpfInvalidoException {
        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            throw new CpfInvalidoException("Cpf inválido");
        else
            return;
    }
}
