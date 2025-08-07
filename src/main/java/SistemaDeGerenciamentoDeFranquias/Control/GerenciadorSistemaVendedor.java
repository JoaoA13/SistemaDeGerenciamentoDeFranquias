package SistemaDeGerenciamentoDeFranquias.Control;

import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Model.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresBancoDados.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada.*;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorPrecoPositivo;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorQuantidadeValida;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresNumericos.ValidadorValorNaoNegativo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class GerenciadorSistemaVendedor extends GerenciadorSistema {
    public Pedido lancarPedido(String nomeCliente, String dataTexto, String horaTexto, String formaDePagamento, String taxaEntregaTexto, String cpfCliente, String codigo, Vendedor vendedor, Loja loja) throws EntradaException {
        LocalDate data;
        LocalTime hora;
        BigDecimal taxaEntrega;
        try {
            ValidadorCampoVazio.valida(nomeCliente);
            ValidadorCampoVazio.valida(taxaEntregaTexto);
            ValidadorCampoVazio.valida(cpfCliente);
            ValidadorCampoVazio.valida(codigo);

            ValidadorNome.valida(nomeCliente);
            ValidadorCpf.validarCpf(cpfCliente);
            ValidadorCodigo.validarCodigo(codigo);
            data = ValidadorData.validarData(dataTexto);
            hora = ValidadorHora.validarHora(horaTexto);
            taxaEntrega = ValidadorValorNaoNegativo.valida(taxaEntregaTexto);

            ValidadorCodigoPedidoBancoDeDadosFalse.valida(codigo);
        } catch (EntradaException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: Entrada Exception: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
        Pedido pedido = new Pedido(codigo, nomeCliente, data, hora, formaDePagamento, taxaEntrega, vendedor.getCpf());

        GerenciadorDeLojas.salvaLojaGerente();
        return pedido;
    }

    public String validarNovosProdutos(String codigo, BigDecimal quantidade, Loja loja, Vendedor vendedor) throws EntradaException {
        try {
            ValidadorPrecoPositivo.valida(String.valueOf(quantidade));
            ValidadorQuantidadeValida.validar(quantidade, loja.getProduto(codigo));
        } catch (EntradaException e) {
            System.out.println("Erro: Quantidade inválida " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }
        vendedor.addVolumeVendas(quantidade.intValue());
        GerenciadorDeLojas.salvaLojaGerente();
        return "Pedido Cadastrado";
    }

    public void adicionaoAoPedido(Pedido pedido, String codigo, BigDecimal quantidade, Loja loja, Cliente cliente) {
        Produto produto = loja.getProduto(codigo);
        pedido.addProduto(codigo, produto);
        pedido.addQntProd(codigo, quantidade);
        produto.setQuant(produto.getQuant().subtract(quantidade));
        cliente.setQuantidaCompras(BigDecimal.ONE);
        if (produto.getQuant().compareTo(BigDecimal.ZERO) <= 0)
            loja.excluirProduto(codigo);
    }

    public String solicitarEdicao(String texto, int escolha, Pedido pedido, Usuario vendedor) throws EntradaException {
        System.out.println("solicita edição");
        LocalDate data = null;
        LocalTime hora = null;
        BigDecimal taxaEntrega = null;
        try {
            if (escolha != 2 && escolha != 3)
                ValidadorCampoVazio.valida(texto);
            switch (escolha) {
                case 0: {
                    ValidadorCodigo.validarCodigo(texto);
                    ValidadorCodigoPedidoBancoDeDadosFalse.valida(texto);
                    break;
                }
                case 1: {
                    ValidadorCpf.validarCpf(texto);
                    break;
                }
                case 2: {
                    data = ValidadorData.validarData(texto);
                    break;
                }
                case 3: {
                    hora = ValidadorHora.validarHora(texto);
                    break;
                }
                case 5: {
                    taxaEntrega = ValidadorValorNaoNegativo.valida(texto);
                    break;
                }
            }
        } catch (EntradaException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        } catch (BancoDeDadosException e) {
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new EntradaException(e.getMessage());
        }

        if (vendedor instanceof Vendedor) {
            Loja loja = GerenciadorDeLojas.getLoja(((Vendedor) vendedor).getCodigoLoja());
            if (loja == null) {
                return "Erro loja é nula";
            } else {
                switch (escolha) {
                    case 0: {
                        loja.addPedidosAltera(pedido, texto, pedido.getCodigo(), escolha);
                        break;
                    }
                    case 1: {
                        loja.addPedidosAltera(pedido, texto, pedido.getCliente().getCpf(), escolha);
                        break;
                    }
                    case 2: {
                        loja.addPedidosAltera(pedido, data, pedido.getData(), escolha);
                        break;
                    }
                    case 3: {
                        loja.addPedidosAltera(pedido, hora, pedido.getHora(), escolha);
                        break;
                    }
                    case 4: {
                        loja.addPedidosAltera(pedido, texto, pedido.getFormaDePagamento(), escolha);
                        break;
                    }
                    case 5: {
                        loja.addPedidosAltera(pedido, taxaEntrega, pedido.getTaxaEntrega(), escolha);
                        break;
                    }
                }
                return "Edição solicitada";
            }
        }
        switch (escolha) {
            case 0: {
                pedido.setCodigo(texto, GerenciadorDeLojas.getLoja(vendedor.getCpf()));
                break;
            }
            case 1: {
                pedido.getCliente().setCpf(texto);
                break;
            }
            case 2: {
                pedido.setData(data);
                break;
            }
            case 3: {
                pedido.setHora(hora);
                break;
            }
            case 4: {
                pedido.setFormaDePagamento(texto);
                break;
            }
            case 5: {
                pedido.setTaxaEntrega(taxaEntrega);
                break;
            }
        }

        GerenciadorDeLojas.salvaLojaGerente();

        return "Edição Realizada";
    }

    public String solicitarExclusao(Usuario usuario,Loja loja, Pedido pedido) throws EntradaException {
        if(usuario instanceof Vendedor){
            try {
                ValidadorEdicaoExclusaoDePedidosBancoDeDadosFalse.valida(pedido.getCodigo(), GerenciadorDeLojas.getLoja(((Vendedor) usuario).getCodigoLoja()));
            } catch (BancoDeDadosException e) {
                System.out.println("Erro: EntradaException: " + e.getMessage());
                throw new EntradaException(e.getMessage());
            }
            loja.addPedidosAltera(pedido, "Exclusão", pedido.getCodigo(), 0);
            return "Exclusão Solicitada";
        }
        loja.excluirPedido(pedido);
        GerenciadorDeLojas.salvaLojaGerente();

        return "Exclusão Concluída";
    }
}