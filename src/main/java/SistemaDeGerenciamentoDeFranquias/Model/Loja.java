package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;
import java.util.*;

public class Loja {

    private String codigo;
    private String endereco;
    private Gerente gerenteDaUnidade;
    public Map<String, Vendedor> armazenaVendedores = new HashMap<>();

    public Loja(String codigo, String endereco, Gerente gerenteDaUnidade){
        this.endereco = endereco;
        this.gerenteDaUnidade = gerenteDaUnidade;
        this.codigo = codigo;
        Vendedor vendedor1 = new Vendedor("Jorge", "14714714714","socorro@gmail.com", "12345677");
        Vendedor vendedor2 = new Vendedor("Carlos", "45645645645","eitanois@gmail.com", "12345677");
        Vendedor vendedor3 = new Vendedor("Ana", "78978978978","Tome-lheForro@gmail.com", "12345677");
        armazenaVendedores.put("14714714714",vendedor1);
        armazenaVendedores.put("45645645645",vendedor2);
        armazenaVendedores.put("78978978978",vendedor3);
        vendedor1.setValorVenda(BigDecimal.ONE);
        vendedor2.setValorVenda(BigDecimal.TEN);
        vendedor3.setValorVenda(BigDecimal.TWO);
    }

    /// Getters e Setters
    public String getCodigo(){return codigo;}

    public String getCpfGerente() {return gerenteDaUnidade.getCpf();}
    protected void setCpfGerente(String cpf) {gerenteDaUnidade.setCpf(cpf);}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    protected Gerente getGerenteDaUnidade() {return gerenteDaUnidade;}
    public void setGerenteDaUnidade(Gerente gerenteDaUnidade) {this.gerenteDaUnidade = gerenteDaUnidade;}

    public Map<String, Vendedor> getArmazenaVendedores() { return armazenaVendedores; }

    public void addVendedor(String nome, String cpf, String email, String senha){
        Vendedor vendedor = new Vendedor(nome, cpf, email, senha);
        armazenaVendedores.put(cpf,vendedor);
    }
    public void excluirVendedor(String cpf){armazenaVendedores.remove(cpf);}

    public Vendedor getVendedor(String cpf){
        return armazenaVendedores.getOrDefault(cpf,null);
    }

    public Vendedor[] rankVendedores(){
        List<Vendedor> lista = new ArrayList<>(getArmazenaVendedores().values());
        Collections.sort(lista, (v1, v2) -> v2.getValorVenda().compareTo(v1.getValorVenda()));
        return lista.toArray(new Vendedor[0]);
    }
}
