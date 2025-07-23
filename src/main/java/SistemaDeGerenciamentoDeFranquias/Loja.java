package SistemaDeGerenciamentoDeFranquias;

public class Loja {

    private String endereco;
    private Gerente gerenteDaUnidade;

    protected Loja(String endereco,Gerente gerenteDaUnidade){
        this.endereco = endereco;
        this.gerenteDaUnidade = gerenteDaUnidade;
    }

    /// Getters e Setters
    protected String getCpfGerente() {return gerenteDaUnidade.getCpf();}
    protected void setCpfGerente(String cpf) {gerenteDaUnidade.setCpf(cpf);}

    protected String getEndereco() {return endereco;}
    protected void setEndereco(String endereco) {this.endereco = endereco;}

    protected Gerente getGerenteDaUnidade() {return gerenteDaUnidade;}
    protected void setGerenteDaUnidade(Gerente gerenteDaUnidade) {this.gerenteDaUnidade = gerenteDaUnidade;}
}
