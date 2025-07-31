package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;

public class Cliente {
    private String nome;
    private BigDecimal quantidadeCompras = BigDecimal.valueOf(0);///CIRA ELE QUANDO ELE FAZ UM PEDIDO, ENTÃO JÁ COMEÇA COM 1 PEDIDO///
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
}
