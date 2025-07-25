package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistema;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;

import java.util.Map;

public class ValidadorCpfVendedorBancoDeDadosFalse implements ValidadorBancoDeDados{
    static public boolean valida(String cpf, Map<String, Loja> mapaLoja)  throws BancoDeDadosException {
        if(cpfJaCadastrado(cpf, mapaLoja))
            throw new BancoDeDadosException("Esse Cpf já está cadastrado");
        else
            return true;
    }
    public static boolean cpfJaCadastrado(String cpf, Map<String, Loja> mapaLoja) {
        for (Loja confereMapa : mapaLoja.values()) {
            Vendedor confereVendedores = confereMapa.getVendedor(cpf);
            if (confereVendedores != null) {
                return true;
            }
        }
        return false;
    }
}
