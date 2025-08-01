package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;

public class Cliente {
    private String nome;
    private BigDecimal quantidadeCompras = BigDecimal.ZERO;///CIRA ELE QUANDO ELE FAZ UM PEDIDO, ENTÃO JÁ COMEÇA COM 1 PEDIDO///
    private BigDecimal valorGasto = BigDecimal.ZERO;
    private String cpf;


    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    /// Getters e Setters gerais
    public String getNome() { return nome;}
    public void setNome(String nome) { this.nome = nome;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public void setQuantidaCompras(BigDecimal quantidadeCompras){
        this.quantidadeCompras = this.quantidadeCompras.add(quantidadeCompras);
    }

    public BigDecimal getQuantidadeCompras() {
        return quantidadeCompras;
    }

    public BigDecimal getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(BigDecimal valor) {
        valorGasto = valorGasto.add(valor);
        System.out.println("Valor gasto: " + valorGasto);
    }
}
