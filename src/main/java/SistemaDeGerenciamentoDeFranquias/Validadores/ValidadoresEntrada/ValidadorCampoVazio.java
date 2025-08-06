package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CampoVazioException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

public interface ValidadorCampoVazio {
    static public void valida(String textoDoCampo) throws EntradaException {
        if (textoDoCampo == null || textoDoCampo.trim().isEmpty() )
            throw new CampoVazioException("O campo de texto está vazio");
    }
}
