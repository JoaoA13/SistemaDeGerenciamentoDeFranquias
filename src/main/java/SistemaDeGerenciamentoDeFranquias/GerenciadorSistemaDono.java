package SistemaDeGerenciamentoDeFranquias;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    public GerenciadorSistemaDono(){
    }
    @Override
    void login(String nome,String senha) {
        super.login(nome,senha);

        if (dono.getNome().equals(nome))
            System.out.println("nome certo");
        else
            System.out.println("nome errado");

        if (dono.getSenha().equals(senha))
            System.out.println("senha certo");
        else
            System.out.println("senha errado");

    }
}
