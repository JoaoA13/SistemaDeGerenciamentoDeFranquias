package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Arquivos.salvaArquivos;
import SistemaDeGerenciamentoDeFranquias.Exceptions.*;
import SistemaDeGerenciamentoDeFranquias.Model.Dono;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoLojaBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosTrue;

import java.util.*;

public class GerenciadorSistemaDono extends GerenciadorSistema {
    static private Map<String, Dono> armazenaDonos = new HashMap<>();

    public static void carregaArmazenaDonos() {
        Map<String, Dono> recuperado = salvaArquivos.carregarDono();
        if (recuperado != null) {
            armazenaDonos = recuperado;
        }
    }

    public GerenciadorSistemaDono() {
//        Dono dono1 = new Dono("João","14127945605","joao@gmail","12345678");
//        armazenaDonos.put(dono1.getCpf(), dono1);
//        salvaArquivos.salvarDonos(armazenaDonos);
        carregaArmazenaDonos();
    }

    static public Dono getDono(String cpf){
        Dono dono = armazenaDonos.get(cpf);
        return dono;
    }
    static public Map<String, Dono> getDonos(){
        Map<String, Dono> Donos = armazenaDonos;
        return Donos;
    }
    public void cadastroLoja(String endereco,String codigo, String cpfGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(endereco);
            ValidadorCampoVazio.valida(cpfGerente);
            ValidadorCodigo.validarCodigo(codigo);
            ValidadorCpf.validarCpf(cpfGerente);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCodigoLojaBancoDeDadosFalse.valida(codigo);
            ValidadorCpfBancoDeDadosTrue.valida(cpfGerente);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        if (GerenciadorDeLojas.getLoja(cpfGerente) != null)
            throw new CadastroException("Esse gerente já possui uma unidade");


        GerenciadorDeLojas.cadastraLoja(endereco,codigo, GerenciadorDeLojas.getGerente(cpfGerente));
    }

    public void cadastroGerente(String nomeGerente, String cpfGerente,String senha, String emailGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nomeGerente);
            ValidadorCampoVazio.valida(cpfGerente);
            ValidadorCampoVazio.valida(senha);
            ValidadorCampoVazio.valida(emailGerente);

            ValidadorNome.validarNome(nomeGerente);
            ValidadorCpf.validarCpf(cpfGerente);
            ValidadorSenha.valida(senha);
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

        Gerente gerente = new Gerente(nomeGerente, cpfGerente, emailGerente, senha);
        GerenciadorDeLojas.cadastraGerente(cpfGerente, gerente);
    }

    public void cadastroDono(String nome, String cpf,String senha, String email) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nome);
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCampoVazio.valida(email);

            ValidadorNome.validarNome(nome);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
            ValidadorEmail.valida(email);

        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        Dono dono = new Dono(nome, cpf, email, senha);
        armazenaDonos.put(cpf, dono);
        System.out.println(armazenaDonos.get(cpf));

        salvaArquivos.salvarDonos(armazenaDonos);
    }

    static public String excluirLoja(String codigo) throws EntradaException {
        try {
            ValidadorCampoVazio.valida(codigo);
            //ValidadorCpf.validarCpf(cpfGerente);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            //ValidadorCpfBancoDeDadosTrue.valida(cpfGerente);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        GerenciadorDeLojas.excluirLoja(codigo);

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

    static public String excluirDono(String cpf) throws EntradaException {
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

        armazenaDonos.remove(cpf);

        salvaArquivos.salvarDonos(armazenaDonos);
        return "Dono excluído com Sucesso";
    }

    static public String editarLoja(String endereco, String cpfNovoGerente,String novoCodigo,String codigo) throws EntradaException {
        if (endereco != "") {
            Loja loja = GerenciadorDeLojas.getLoja(codigo);
            if(loja != null)
                loja.setEndereco(endereco);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Endereço editado";
        }
        if (novoCodigo != "") {
            try {
                ValidadorCodigo.validarCodigo(novoCodigo);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new LoginException(e.getMessage());
            }

            try {
                ValidadorCodigoLojaBancoDeDadosFalse.valida(novoCodigo);
            } catch (BancoDeDadosException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new CadastroException(e.getMessage());
            }

            GerenciadorDeLojas.trocarCodigo(novoCodigo,codigo);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Codigo da loja editado";
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

            GerenciadorDeLojas.getCodigoPraCpf().remove(GerenciadorDeLojas.getLoja(codigo).getCpfGerente());
            GerenciadorDeLojas.getCodigoPraCpf().put(cpfNovoGerente,codigo);

            GerenciadorDeLojas.trocarGerente(codigo,GerenciadorDeLojas.getGerente(cpfNovoGerente));

            GerenciadorDeLojas.salvaLojaGerente();
            return "Gerente da unidade foi trocado";
        }
        return "";
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

            GerenciadorDeLojas.salvaLojaGerente();
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

            GerenciadorDeLojas.salvaLojaGerente();
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

                GerenciadorDeLojas.salvaLojaGerente();
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

                GerenciadorDeLojas.salvaLojaGerente();
                return "Senha editada";
            }
            return "";
        }

    static public String editarDono(String nome, String cpfNovo, String email, String senha, String cpf) throws EntradaException {
        if (nome != "") {
            try {
                ValidadorCampoVazio.valida(nome);
                ValidadorNome.validarNome(nome);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            getDono(cpf).setNome(nome);

            salvaArquivos.salvarDonos(armazenaDonos);
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

            Dono dono = getDono(cpf);
            armazenaDonos.remove(cpf);

            dono.setCpf(cpfNovo);
            armazenaDonos.put(cpfNovo,dono);

            salvaArquivos.salvarDonos(armazenaDonos);
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

            getDono(cpf).setEmail(email);

            salvaArquivos.salvarDonos(armazenaDonos);
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

            getDono(cpf).setSenha(senha);

            salvaArquivos.salvarDonos(armazenaDonos);
            return "Senha editada";
        }
        return "";
    }
}
