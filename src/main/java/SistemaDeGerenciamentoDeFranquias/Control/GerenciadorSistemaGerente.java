//João Arthur Vieira 202465564B
//Ismael Cezário Rosa 202465503B

package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Pedido;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorBigDecimal;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorPrecoPositivo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class GerenciadorSistemaGerente extends GerenciadorSistema{

    static GerenciadorDeLojas listaLojas = new GerenciadorDeLojas();

    public String lancarCadastro(String nome, String cpf,String email, String senha, String cpfGerente) throws CadastroException {
        try {
            ValidadorCampoVazio.valida(nome);
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(email);
            ValidadorCampoVazio.valida(senha);

            ValidadorNome.valida(nome);
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
        GerenciadorDeLojas.salvaLojaGerente();

        return "Vendedor Cadastrado";
    }

    public String editarVendedor(String nome, String cpf, String email, String senha, String cpfGerente, String cpfAntigo) throws EntradaException{
        if(nome != ""){
            try {
                ValidadorCampoVazio.valida(nome);
                ValidadorNome.valida(nome);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Vendedor vendedor = loja.getVendedor(cpfAntigo);
            vendedor.setNome(nome);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Nome editado";
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

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Vendedor vendedor = loja.getVendedor(cpfAntigo);
            loja.getArmazenaVendedores().remove(cpfAntigo);
            vendedor.setCpf(cpf);
            loja.getArmazenaVendedores().put(cpf, vendedor);

            GerenciadorDeLojas.salvaLojaGerente();
            return "CPf editado";
        }
        if(email != ""){
            try {
                ValidadorCampoVazio.valida(email);
                ValidadorEmail.valida(email);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Vendedor vendedor = loja.getVendedor(cpfAntigo);
            vendedor.setEmail(email);

            GerenciadorDeLojas.salvaLojaGerente();
            return "E-mail editado";
        }
        if(senha != ""){
            try {
                ValidadorCampoVazio.valida(senha);
                ValidadorSenha.valida(senha);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Vendedor vendedor = loja.getVendedor(cpfAntigo);
            vendedor.setSenha(senha);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Senha editada";
        }

        return "";
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

        GerenciadorDeLojas.salvaLojaGerente();

        return "Vendedor Excluído com Sucesso";
    }

    /// FAZER SALVA ARQUIVOS
    public String lancarProduto(String nome, String precoSTR, String carac, String quantSTR, String codigo, String cpfGerente) throws CadastroException{
        BigDecimal preco = new BigDecimal(0);
        BigDecimal quant = new BigDecimal(0);

        try {
            ValidadorCampoVazio.valida(nome);
            ValidadorCampoVazio.valida(precoSTR);
            ValidadorCampoVazio.valida(carac);
            ValidadorCampoVazio.valida(quantSTR);
            ValidadorCampoVazio.valida(codigo);

            preco = ValidadorBigDecimal.valida(precoSTR, "Preço Inválido");
            quant = ValidadorBigDecimal.valida(quantSTR, "Quantidade Inválida");

            ValidadorNome.valida(nome);
            ValidadorPrecoPositivo.valida(String.valueOf(preco));
            carac = ValidadorCaracEndereco.validarTexto(carac, "Características");
            ValidadorPrecoPositivo.valida(String.valueOf(quant));
            ValidadorCodigo.validarCodigo(codigo);
        } catch (EntradaException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        try {
            ValidadorCodigoProdutoBancoDeDadosFalse.valida(codigo, cpfGerente);
        }catch (BancoDeDadosException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new CadastroException(e.getMessage());
        }

        Loja loja = listaLojas.getLoja(cpfGerente);
        loja.addProduto(nome, preco, carac, quant, codigo);

        GerenciadorDeLojas.salvaLojaGerente();
        return "Produto Cadastrado";

    }

    public String editarProduto (String nome, String preco, String carac, String quant, String codigo, String cpfGerente, String codigoAntigo) throws EntradaException{
        if(nome != ""){
            try {
                ValidadorCampoVazio.valida(nome);
                ValidadorNome.valida(nome);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Produto produto = loja.getProduto(codigoAntigo);
            produto.setNomeProd(nome);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Nome editado";
        }
        if(preco != ""){
            BigDecimal precoProd = new BigDecimal(0);

            try {
                ValidadorCampoVazio.valida(preco);
                precoProd = ValidadorBigDecimal.valida(preco, "Preço Inválido");
                ValidadorPrecoPositivo.valida(String.valueOf(precoProd));
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Produto produto = loja.getProduto(codigoAntigo);
            produto.setPreco(precoProd);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Preço editado";
        }
        if(carac != ""){
            try {
                ValidadorCampoVazio.valida(carac);
                ValidadorCaracEndereco.validarTexto(carac, "Características");
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Produto produto = loja.getProduto(codigoAntigo);
            produto.setCarac(carac);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Características editadas";
        }
        if(quant != ""){
            BigDecimal quantProd = new BigDecimal(0);

            try {
                ValidadorCampoVazio.valida(quant);
                quantProd = ValidadorBigDecimal.valida(quant, "Quantidade Inválida");
                ValidadorPrecoPositivo.valida(String.valueOf(quantProd));
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Produto produto = loja.getProduto(codigoAntigo);
            produto.setQuant(quantProd);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Quantidade editada";
        }
        if(codigo != ""){
            try {
                ValidadorCampoVazio.valida(codigo);
                ValidadorCodigo.validarCodigo(codigo);
            } catch (EntradaException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            try {
                ValidadorCodigoProdutoBancoDeDadosFalse.valida(codigo, cpfGerente);
            }catch (BancoDeDadosException e){
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }

            Loja loja = GerenciadorDeLojas.getLoja(cpfGerente);
            Produto produto = loja.getProduto(codigoAntigo);
            loja.excluirProduto(codigoAntigo);
            produto.setCodigoProd(codigo);
            loja.addProdutoDireto(produto);

            GerenciadorDeLojas.salvaLojaGerente();
            return "Código editado";
        }
        return "";
    }

    /// FAZER SALVA ARQUIVOS
    public void excluirProdutos(String codigo, String cpfGerente){
        Loja loja = listaLojas.getLoja(cpfGerente);
        loja.excluirProduto(codigo);

        GerenciadorDeLojas.salvaLojaGerente();
    }

    static void listaDePedidos(String texto, LocalDate data, LocalTime hora, BigDecimal taxaExtra, Pedido pedido, int escolha){
        switch (escolha){
            case 0 :
        }
    }

    public void listaDeVendedores(String cpfGerente){

    }


    static void cadastrarVendedor(String nome, String cpf, String senha){

    }
}
