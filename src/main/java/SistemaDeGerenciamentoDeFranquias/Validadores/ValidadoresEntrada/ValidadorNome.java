package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.NomeInvalidoException;

public class ValidadorNome {
    static public void validarNome(String nome) throws EntradaException{
        if (nome.length() < 2 || !nome.matches("^[a-zA-ZÀ-ÿ\\s]{3,}$"))
            throw new NomeInvalidoException("Nome inválido");
        else
            return;
    }
}
