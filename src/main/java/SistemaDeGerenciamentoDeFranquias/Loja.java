package SistemaDeGerenciamentoDeFranquias;

public class Loja {

    private String codigo;
    private String endereco;
    private Gerente gerenteDaUnidade;

    protected Loja(String codigo, String endereco,Gerente gerenteDaUnidade){
        this.codigo = codigo;
        this.endereco = endereco;
        this.gerenteDaUnidade = gerenteDaUnidade;
    }

    /// Getters e Setters
    protected String getCodigo() {return codigo;}
    protected void setCodigo(String codigo) {this.codigo = codigo;}

    protected String getEndereco() {return endereco;}
    protected void setEndereco(String endereco) {this.endereco = endereco;}

    protected Gerente getGerenteDaUnidade() {return gerenteDaUnidade;}
    protected void setGerenteDaUnidade(Gerente gerenteDaUnidade) {this.gerenteDaUnidade = gerenteDaUnidade;}
}
