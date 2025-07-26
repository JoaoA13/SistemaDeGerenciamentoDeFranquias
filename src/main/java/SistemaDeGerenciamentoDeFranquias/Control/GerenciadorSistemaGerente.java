package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpfBancoDeDadosTrue;

public class GerenciadorSistemaGerente extends GerenciadorSistema{

    static GerenciadorDeLojas listaLojas = new GerenciadorDeLojas();

    public String login(String cpf, String senha) throws LoginException {
        super.login(cpf, senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        } catch (EntradaException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            buscaCpfExiste(cpf);
            ValidadorLogin.valida(GerenciadorDeLojas.getGerente(cpf),cpf,senha);
            return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    public String lancarCadastro(String nome, String cpf,String email, String senha, String cpfGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nome);
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(email);
            ValidadorCampoVazio.valida(senha);

            ValidadorNome.validarNome(nome);
            ValidadorCpf.validarCpf(cpf);
            ValidadorEmail.valida(email);
            ValidadorSenha.valida(senha);
        } catch (EntradaException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
            ValidadorCpfBancoDeDadosFalse.valida(cpf);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        Loja loja = listaLojas.getLoja(cpfGerente);
        loja.addVendedor(nome, cpf, email, senha);
        return "Vendedor Cadastrado";
    }

    public String editarVendedor(String nome, String cpf, String email, String senha, String cpfGerente) throws EntradaException{
        if(nome != ""){

        }
        if(cpf != ""){
            try {
                ValidadorCampoVazio.valida(cpf);
                ValidadorCpf.validarCpf(cpf);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            try {
                ValidadorCpfBancoDeDadosFalse.valida(cpf);
            }catch (BancoDeDadosException e){
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }
            Loja loja = listaLojas.getLoja(cpfGerente);
            Vendedor vendedor = loja.getVendedor(cpf);
            vendedor.setCpf(cpf);
            return "CPf editado";
        }
        if(email != ""){

        }
        if(senha != ""){

        }

        return "CPf editado";
    }

    public String excluirVendedor(String cpf, String cpfGerente) throws EntradaException {
        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCpf.validarCpf(cpf);
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        try {
            ValidadorCpfVendedorBancoDeDadosTrue.valida(cpf);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        Loja loja = listaLojas.getLoja(cpfGerente);
        loja.excluirVendedor(cpf);

        return "Vendedor Excluído com Sucesso";
    }

    public void listaDeVendedores(String cpfGerente){

    }


    static void cadastrarVendedor(String nome, String cpf, String senha){

    }
}
