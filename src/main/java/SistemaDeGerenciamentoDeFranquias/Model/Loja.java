package SistemaDeGerenciamentoDeFranquias.Model;

import java.util.HashMap;
import java.util.Map;

public class Loja {

    private String codigo;
    private String endereco;
    private Gerente gerenteDaUnidade;
    static private Map<String, Vendedor> armazenaVendedores = new HashMap<>();

    public Loja(String codigo, String endereco, Gerente gerenteDaUnidade){
        this.endereco = endereco;
        this.gerenteDaUnidade = gerenteDaUnidade;
        this.codigo = codigo;
    }

    /// Getters e Setters
    protected String getCpfGerente() {return gerenteDaUnidade.getCpf();}
    protected void setCpfGerente(String cpf) {gerenteDaUnidade.setCpf(cpf);}

    protected String getEndereco() {return endereco;}
    protected void setEndereco(String endereco) {this.endereco = endereco;}

    protected Gerente getGerenteDaUnidade() {return gerenteDaUnidade;}
    protected void setGerenteDaUnidade(Gerente gerenteDaUnidade) {this.gerenteDaUnidade = gerenteDaUnidade;}

    void addVendedor(String nome,String cpf,String senha, String cpfGerente){
        Vendedor vendedor = new Vendedor(nome, cpf,"@gmail.com", senha);
        armazenaVendedores.put(cpf,vendedor);
    }

    static public Vendedor getVendedor(String cpf){
        return armazenaVendedores.getOrDefault(cpf,null);
    }
}
