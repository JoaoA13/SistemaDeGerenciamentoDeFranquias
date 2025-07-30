package SistemaDeGerenciamentoDeFranquias.Model;

public class Gerente extends Usuario {
    public Gerente(String nome, String cpf, String email, String senha){
        super(nome,cpf,email,senha);
    }
    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.GERENTE;
    }
}
