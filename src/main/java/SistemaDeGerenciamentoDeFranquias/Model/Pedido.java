package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Pedido {
    protected Map<String, Produto> produtosPedido = new HashMap<>();
    private String nomeCliente;
    private LocalDate data;
    private LocalTime hora;
    private String formaDePagamento;
    private BigDecimal taxaEntrega;

    public Pedido(BigDecimal quantidade, String nomeCliente, LocalDate data, LocalTime hora, String formaDePagamento, BigDecimal taxaEntrega){
        /*this.nomeCliente;
        this.data;
        private LocalTime hora;
        private String formaDePagamento;
        private BigDecimal taxaEntrega;*/
    }
    public void addProduto(String codigo, Produto produto){
        produtosPedido.put(codigo,produto);
    }
}
