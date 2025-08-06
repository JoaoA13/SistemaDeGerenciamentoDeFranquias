package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Pedido {
    private Map<String, Produto> produtos = new HashMap<>();
    private Map<String, BigDecimal> quantProdutos = new HashMap<>();
    private String codigo;
    private String nomeCliente;
    private LocalDate data;
    private LocalTime hora;
    private String formaDePagamento;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private Cliente cliente;
    private String cpfVendedor;

    public Pedido(String codigo, String nomeCliente,
                  LocalDate data, LocalTime hora, String formaDePagamento,
                  BigDecimal taxaEntrega,String cpfVendedor) {
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.hora = hora;
        this.formaDePagamento = formaDePagamento;
        this.taxaEntrega = taxaEntrega;
        valorTotal = taxaEntrega;
        this.cpfVendedor = cpfVendedor;
    }

    public Map<String, Produto> getProdutos() { return produtos; }

    public void setProdutos(Map<String, Produto> produtos) { this.produtos = produtos; }

    public void addProduto(String codigo,Produto produto) { produtos.put(codigo,produto); }

    public Map<String, BigDecimal> getQuantProdutos() { return quantProdutos; }

    public BigDecimal getQuantProduto(String codigo) {
        BigDecimal quantProduto = quantProdutos.get(codigo);
        return quantProduto;
    }

    public void setQuantProdutos(Map<String, BigDecimal> quantProdutos) {
        this.quantProdutos = quantProdutos;
    }

    public void addQntProd(String codigo,BigDecimal quantidade) {
        quantProdutos.put(codigo,quantidade);

        BigDecimal preco = (produtos.get(codigo)).getPreco();
        valorTotal = valorTotal.add(preco.multiply(quantidade));
    }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo, Loja loja) {
        Vendedor vendedor = loja.getVendedor(cpfVendedor);
        Pedido pedido = vendedor.getPedido(this.codigo);
        vendedor.excluirPedido(this.codigo);
        if(loja.getPedidoAltera(pedido.getCodigo()) != null){
            System.out.println("entrooooooooooooooooooouuuuuuu");
            String codigoAntigo = this.codigo;
            this.codigo = codigo;
            loja.addPedidosAltera(pedido, loja.getArmazenaAlteracao(codigoAntigo),loja.getArmazenaAtual(codigoAntigo),0);
            this.codigo = codigoAntigo;
            loja.excluirPedido(pedido);
        }
        this.codigo = codigo;
        vendedor.addPedido(pedido);
        /// lembrete: mudar o código nos outros mapas
    }

    public String getNomeCliente() { return nomeCliente; }

    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHora() { return hora; }

    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getFormaDePagamento() { return formaDePagamento; }

    public void setFormaDePagamento(String formaDePagamento) { this.formaDePagamento = formaDePagamento; }

    public BigDecimal getTaxaEntrega() { return taxaEntrega; }

    public void setTaxaEntrega(BigDecimal taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public BigDecimal getValorTotal(){ return valorTotal; }

    public void setCliente(Cliente cliente){ this.cliente = cliente;}

    public Cliente getCliente(){ return cliente;}

    public String getCpfVendedor() {
        return cpfVendedor;
    }

    public void setCpfVendedor(String cpfVendedor) {
        this.cpfVendedor = cpfVendedor;
    }
}
