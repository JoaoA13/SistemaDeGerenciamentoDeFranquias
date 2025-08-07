package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Usuario;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.ValidadorCampoVazio;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.ValidadorSenha;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosTrue;

public abstract class GerenciadorSistema {

    //protected GerenciadorDeLojas gerenciadorDeLojas = new GerenciadorDeLojas();

    public GerenciadorSistema() {
    }

    static public String login(String cpf, String senha) throws LoginException {
        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        Usuario usuario = null;

        try {
            usuario = buscaUsuario(cpf);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorLogin.valida(usuario, cpf, senha);
            return "CPF e senha corretos";
        } catch (LoginException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    static public void buscaCpfExiste(String cpf) throws LoginException {
        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpf);
        } catch (BancoDeDadosException e) {
            throw new LoginException(e.getMessage());
        }
    }

    static public void buscaCpfNAOExiste(String cpf) throws LoginException {
        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
        } catch (BancoDeDadosException e) {
            throw new LoginException(e.getMessage());
        }
    }

    static public Usuario buscaUsuario(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        for (Loja loja : GerenciadorDeLojas.getLojas().values()) {
            if (loja == null)
                continue;
            if (loja.getVendedor(cpf) != null)
                return loja.getVendedor(cpf);
        }
        if (GerenciadorDeLojas.getGerente(cpf) != null)
            return GerenciadorDeLojas.getGerente(cpf);
        else if (GerenciadorSistemaDono.getDono(cpf) != null)
            return GerenciadorSistemaDono.getDono(cpf);
        return null;
    }

}