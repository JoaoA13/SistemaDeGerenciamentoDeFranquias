package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.NomeInvalidoException;

public class ValidadorNome implements ValidadadorEntrada{
    static public void valida(String nome) throws EntradaException{
        if (nome.length() < 2 || !nome.matches("^[a-zA-ZÀ-ÿ\\s]{3,}$"))
            throw new NomeInvalidoException("Nome inválido");
        else
            return;
    }
}
