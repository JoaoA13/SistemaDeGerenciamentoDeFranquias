package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoPedidoBancoDeDadosFalse;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCodigoProdutoBancoDeDadosTrue;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.ValidadorCpfBancoDeDadosTrue;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorPrecoPositivo;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorValorNaoNegativo;
import SistemaDeGerenciamentoDeFranquias.Vision.IGAcoesVendedor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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

    public String lancarPedido(String codigo, String quantTexto, String nomeCliente, String dataTexto, String horaTexto,String formaDePagamento, String taxaEntregaTexto, Vendedor vendedor, Loja loja,String codigoPedido) throws EntradaException {
        LocalDate data;
        LocalTime hora;
        BigDecimal quant;
        BigDecimal taxaEntrega;
        try {
            ValidadorCampoVazio.valida(codigo);
            ValidadorCampoVazio.valida(quantTexto);
            ValidadorCampoVazio.valida(nomeCliente);
            ValidadorCampoVazio.valida(taxaEntregaTexto);
            ValidadorCampoVazio.valida(codigoPedido);

            ValidadorCodigo.validarCodigo(codigo);
            ValidadorCodigoProdutoBancoDeDadosTrue.valida(codigo, vendedor.getCodigoLoja());
            ValidadorCodigo.validarCodigo(codigoPedido);
            ValidadorCodigoPedidoBancoDeDadosFalse.valida(codigoPedido);

            quant = ValidadorPrecoPositivo.valida(quantTexto);
            ValidadorNome.validarNome(nomeCliente);
            data = ValidadorData.validarData(dataTexto);
            hora = ValidadorHora.validarHora(horaTexto);
            taxaEntrega = ValidadorValorNaoNegativo.validarValorNaoNegativo(taxaEntregaTexto);
        } catch (EntradaException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
        catch (BancoDeDadosException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        boolean continuar = true;

        while (continuar) {
            continuar = IGAcoesVendedor.outrosProdutos(vendedor, loja);
            if (continuar) {
                continuar = IGAcoesVendedor.maisProdutos();
            }
        }

        return "Pedido Cadastrado";
    }

    public static void iniciarCadastro(Vendedor vendedor, Loja loja) {
        IGAcoesVendedor.outrosProdutos(vendedor, loja);

        boolean continuar = IGAcoesVendedor.maisProdutos();

        if (continuar) {
            iniciarCadastro(vendedor, loja);
        }
    }

    public void registraNovosProdutos(String codigo, String quantTexto, Vendedor vendedor, Loja loja) throws EntradaException{
        BigDecimal quant;
        try {
            ValidadorCampoVazio.valida(codigo);
            ValidadorCampoVazio.valida(quantTexto);

            ValidadorCodigo.validarCodigo(codigo);
            ValidadorCodigoProdutoBancoDeDadosTrue.valida(codigo, vendedor.getCodigoLoja());

            quant = ValidadorPrecoPositivo.valida(quantTexto);
        } catch (EntradaException e) {
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
        catch (BancoDeDadosException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
    }
}
