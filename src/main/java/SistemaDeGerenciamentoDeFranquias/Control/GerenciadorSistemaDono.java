package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.*;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciadorSistemaDono extends GerenciadorSistema {
    private String senhaGerentePadrão = "12345678";

    public GerenciadorSistemaDono() {
    }

    @Override
    public String login(String cpf, String senha) throws LoginException {
        super.login(cpf, senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorLogin.valida(getDono(), cpf, senha);
            return "CPF e senha corretos";
        } catch (LoginException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    public void cadastroLoja(String endereco, String cpfGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(endereco);
            ValidadorCampoVazio.valida(cpfGerente);

            ValidadorCpf.validarCpf(cpfGerente);

        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpfGerente);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        if (GerenciadorDeLojas.getLoja(cpfGerente) != null)
            throw new CadastroException("Esse gerente já possui uma unidade");


        GerenciadorDeLojas.cadastraLoja(endereco, GerenciadorDeLojas.getGerente(cpfGerente));
    }

    public void cadastroGerente(String nomeGerente, String cpfGerente, String emailGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nomeGerente);
            ValidadorCampoVazio.valida(cpfGerente);
            ValidadorCampoVazio.valida(emailGerente);

            ValidadorNome.validarNome(nomeGerente);
            ValidadorCpf.validarCpf(cpfGerente);
            ValidadorEmail.valida(emailGerente);

        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpfGerente);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        Gerente gerente = new Gerente(nomeGerente, cpfGerente, emailGerente, senhaGerentePadrão);
        GerenciadorDeLojas.cadastraGerente(gerente.getCpf(), gerente);
    }

    static public String excluirLoja(String cpfGerente) throws EntradaException {
        try {
            ValidadorCampoVazio.valida(cpfGerente);
            ValidadorCpf.validarCpf(cpfGerente);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpfGerente);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        GerenciadorDeLojas.excluirLoja(cpfGerente);

        return "Loja excluída com Sucesso";
    }

    static public String excluirGerente(String cpf) throws EntradaException {
        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCpf.validarCpf(cpf);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpf);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        GerenciadorDeLojas.excluirGerente(cpf);

        return "Gerente excluído com Sucesso";
    }

    static public String editarGerente(String nome, String cpfNovo, String email, String senha, String cpf) throws EntradaException {
        if (nome != "") {
            try {
                ValidadorCampoVazio.valida(nome);
                ValidadorNome.validarNome(nome);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            GerenciadorDeLojas.getGerente(cpf).setNome(nome);

            return "Nome editado";
        }
        if (cpfNovo != "") {
            try {
                ValidadorCampoVazio.valida(cpfNovo);
                ValidadorCpf.validarCpf(cpfNovo);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            try {
                ValidadorCpfBancoDeDadosFalse.valida(cpfNovo);
            }catch (BancoDeDadosException e){
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpf);
            GerenciadorDeLojas.getLojas().remove(cpf);
            GerenciadorDeLojas.getLojas().put(cpfNovo,loja);

            GerenciadorDeLojas.getGerentes().put(cpfNovo,GerenciadorDeLojas.getGerente(cpf));
            GerenciadorDeLojas.getGerentes().remove(cpf);

            GerenciadorDeLojas.getCodigoPraCpf().remove(GerenciadorDeLojas.getCodigoLoja(cpfNovo));
            GerenciadorDeLojas.getCodigoPraCpf().put(GerenciadorDeLojas.getCodigoLoja(cpfNovo),cpfNovo);

            GerenciadorDeLojas.getGerente(cpfNovo).setCpf(cpfNovo);

            return "CPf editado";
        }
            if (email != "") {
                try {
                    ValidadorCampoVazio.valida(email);
                    ValidadorEmail.valida(email);
                } catch (EntradaException e) {
                    System.out.println("Erro: EntradaException: " + e.getMessage());
                    throw new EntradaException(e.getMessage());
                }

                GerenciadorDeLojas.getGerente(cpf).setEmail(email);

                return "E-mail editado";
            }
            if (senha != "") {
                try {
                    ValidadorCampoVazio.valida(senha);
                    ValidadorSenha.valida(senha);
                } catch (EntradaException e) {
                    System.out.println("Erro: EntradaException: " + e.getMessage());
                    throw new EntradaException(e.getMessage());
                }

                GerenciadorDeLojas.getGerente(cpf).setSenha(senha);

                return "Senha editada";
            }
            return "";
        }

    static public String editarLoja(String endereco, String cpfNovoGerente,String codigo) throws EntradaException {
        if (endereco != "") {

            GerenciadorDeLojas.getLoja(codigo).setEndereco(endereco);

            return "Endereço editado";
        }
        /// troca o gerente da unidade
        if (cpfNovoGerente != "") {
            try {
                ValidadorCampoVazio.valida(cpfNovoGerente);
                ValidadorCpf.validarCpf(cpfNovoGerente);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            try {
                ValidadorCpfBancoDeDadosTrue.valida(cpfNovoGerente);
            }catch (BancoDeDadosException e){
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(codigo);
            GerenciadorDeLojas.getLojas().remove(GerenciadorDeLojas.getCpfPorCodigo(codigo));
            GerenciadorDeLojas.getLojas().put(cpfNovoGerente,loja);

            GerenciadorDeLojas.getCodigoPraCpf().remove(codigo);
            GerenciadorDeLojas.getCodigoPraCpf().put(GerenciadorDeLojas.getCodigoLoja(cpfNovoGerente),cpfNovoGerente);

            GerenciadorDeLojas.trocarGerente(codigo,GerenciadorDeLojas.getGerente(cpfNovoGerente));

            return "Gerente da unidade foi trocado";
        }
        return "";
    }

    static public Vendedor[] rankVendedores(Loja loja){
        List<Vendedor> lista = new ArrayList<>(loja.getArmazenaVendedores().values());
        Collections.sort(lista, (v1, v2) -> v2.getValorVenda().compareTo(v1.getValorVenda()));
        return lista.toArray(new Vendedor[0]);
    }


}
