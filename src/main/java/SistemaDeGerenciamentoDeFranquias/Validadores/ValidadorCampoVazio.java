package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CampoVazioException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;

public interface ValidadorCampoVazio {
    static public void valida(String textoDoCampo) throws EntradaException {
        if (textoDoCampo == null || textoDoCampo.trim().isEmpty() )
            throw new CampoVazioException("O campo de texto está vazio");
    }
}
