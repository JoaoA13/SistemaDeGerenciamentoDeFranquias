package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorSistemaDono;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Usuario;

public interface IdentificaTipoUsuario {
    static public Usuario valida(String cpf)  throws BancoDeDadosException {
        cpf = cpf.replaceAll("[^\\d]", "");
        for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
            if(loja == null)
                continue;
            if (loja.getVendedor(cpf) != null)
                return loja.getVendedor(cpf);
        }
        if(GerenciadorDeLojas.getGerente(cpf) != null )
            return GerenciadorDeLojas.getGerente(cpf);
        else if(GerenciadorSistemaDono.getDono(cpf) != null)
            return GerenciadorSistemaDono.getDono(cpf);
        else
            throw new BancoDeDadosException("Esse Cpf não está cadastrado");
    }
}
