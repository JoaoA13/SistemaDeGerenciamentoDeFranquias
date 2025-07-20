package SistemaDeGerenciamentoDeFranquias;

public abstract class Usuario {
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    protected Usuario(String nome,String cpf, String email, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;

    }

    /// Getters e Setters gerais
    public String getNome() { return nome;}
    protected void setNome(String nome) { this.nome = nome;}

    public String getCpf() {return cpf;}
    protected void setCpf(String cpf) {this.cpf = cpf;}

    public String getEmail() {return email;}
    protected void setEmail(String email) {this.email = email;}

    public String getSenha() {return senha;}
    protected void setSenha(String senha) {this.senha = senha;}
}
