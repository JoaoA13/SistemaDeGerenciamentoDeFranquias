package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.CadastroException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Produto;
import SistemaDeGerenciamentoDeFranquias.Model.Vendedor;
import SistemaDeGerenciamentoDeFranquias.Validadores.*;
import SistemaDeGerenciamentoDeFranquias.Vision.IGAcoesVendedor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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

    public String lancarPedido(String codigo, String quantTexto, String nomeCliente, String dataTexto, String horaTexto,String formaDePagamento, String taxaEntregaTexto, Vendedor vendedor, Loja loja) throws EntradaException {
        LocalDate data;
        LocalTime hora;
        BigDecimal quant;
        BigDecimal taxaEntrega;
        try {
            ValidadorCampoVazio.valida(codigo);
            ValidadorCampoVazio.valida(quantTexto);
            ValidadorCampoVazio.valida(nomeCliente);
            ValidadorCampoVazio.valida(taxaEntregaTexto);

            ValidadorCodigo.validarCodigo(codigo);
            ValidadorCodigoProdutoBancoDeDadosTrue.valida(codigo, vendedor.getCodigoLoja());

            quant = ValidadorPrecoPositivo.validarValorPositivo(quantTexto);
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


        while(IGAcoesVendedor.maisProdutos()) {
            IGAcoesVendedor.outrosProdutos(vendedor,loja);
        }
        return "Pedido Cadastrado";
    }

    public void registraNovosProdutos(String codigo, String quantTexto, Vendedor vendedor, Loja loja) throws EntradaException{
        BigDecimal quant;
        try {
            ValidadorCampoVazio.valida(codigo);
            ValidadorCampoVazio.valida(quantTexto);

            ValidadorCodigo.validarCodigo(codigo);
            ValidadorCodigoProdutoBancoDeDadosTrue.valida(codigo, vendedor.getCodigoLoja());

            quant = ValidadorPrecoPositivo.validarValorPositivo(quantTexto);
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
