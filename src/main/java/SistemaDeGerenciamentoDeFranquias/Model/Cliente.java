package SistemaDeGerenciamentoDeFranquias.Model;

public class Cliente {
    private String nome;
    private String cpf;
    private int quantidadePedidos = 1;///CIRA ELE QUANDO ELE FAZ UM PEDIDO, ENTÃO JÁ COMEÇA COM 1 PEDIDO

    protected Cliente(String nome,String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    /// Getters e Setters gerais
    public String getNome() { return nome;}
    public void setNome(String nome) { this.nome = nome;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public void setQuantidadePedidos(int quantidadePedidos){
        this.quantidadePedidos += quantidadePedidos;
    }
}
