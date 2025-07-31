package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoPedidoBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoProdutoBancoDeDadosTrue;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosTrue;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorPrecoPositivo;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorValorNaoNegativo;
import SistemaDeGerenciamentoDeFranquias.Vision.IGAcoesVendedor;
import SistemaDeGerenciamentoDeFranquias.Vision.InterfaceGrafica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas.getVendedorGeral;

public class GerenciadorSistemaVendedor extends GerenciadorSistema{

    public String login(String cpf, String senha) throws LoginException {
        super.login(cpf,senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
        }catch (EntradaException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpfBancoDeDadosTrue.valida(cpf);
            ValidadorLogin.valida(getVendedorGeral(cpf),cpf,senha);
            return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    public Pedido lancarPedido(String nomeCliente, String dataTexto, String horaTexto, String formaDePagamento, String taxaEntregaTexto, String cpfCliente, Vendedor vendedor, Loja loja) throws EntradaException {
        LocalDate data;
        LocalTime hora;
        BigDecimal taxaEntrega;
        try {
            ValidadorCampoVazio.valida(nomeCliente);
            ValidadorCampoVazio.valida(taxaEntregaTexto);
            ValidadorCampoVazio.valida(cpfCliente);

            ValidadorNome.validarNome(nomeCliente);
            ValidadorCpf.validarCpf(cpfCliente);
            data = ValidadorData.validarData(dataTexto);
            hora = ValidadorHora.validarHora(horaTexto);
            taxaEntrega = ValidadorValorNaoNegativo.validarValorNaoNegativo(taxaEntregaTexto);
        } catch (EntradaException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
        Pedido pedido = new Pedido("001", nomeCliente, data, hora, formaDePagamento, taxaEntrega);

        return pedido;
    }

    public String validarNovosProdutos(String codigo, BigDecimal quantidade, Loja loja) throws EntradaException {
        try {
            ValidadorPrecoPositivo.validarValorPositivo(String.valueOf(quantidade));
            ValidadorQuantidadeValida.validar(quantidade, loja.getProduto(codigo));
        } catch (EntradaException e) {
            System.out.println("Erro: Quantidade inválida " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
        return "Pedido Cadastrado";
    }

    public void adicionaoAoPedido (Pedido pedido, String codigo, BigDecimal quantidade, Loja loja, Cliente cliente){
        Produto produto = loja.getProduto(codigo);
        pedido.addProduto(codigo, produto);
        pedido.addQntProd(codigo, quantidade);
        produto.setQuant(produto.getQuant().subtract(quantidade));
        cliente.setQuantidaCompras(quantidade);
        if(produto.getQuant().compareTo(BigDecimal.ZERO) <= 0)
            loja.excluirProduto(codigo);
    }
}
