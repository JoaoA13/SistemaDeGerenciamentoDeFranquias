package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    private GerenciadorDeLojas gerenciadorDeLojas = new GerenciadorDeLojas();
    private String senhaGerentePadrão = "12345678";

    public GerenciadorSistemaDono(){
    }
    @Override
    boolean login(String cpf,String senha) {
        super.login(cpf,senha);

        if(!confereCpf(cpf))
            return false;

        try {
            ValidadorLogin.valida(dono,cpf,senha);
            return true;
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            return false;
        }
    }

    void cadastroLoja(String endereco,String nomeGerente, String cpfGerente, String emailGerente){

        Gerente gerente = new Gerente(nomeGerente,cpfGerente,emailGerente,senhaGerentePadrão);
        gerenciadorDeLojas.cadastraLoja(endereco,gerente);
    }

    boolean confereCpf(String cpf){
        try {
            ValidadorCpf.validarCpf(cpf);
            System.out.println("Cpf válido");
            return true;
        }catch (CpfInvalidoException e){
            System.out.println(e);
            return false;
        }
    }
    boolean confereSenha(String senha){
        try {
            ValidadorCpf.validarCpf(senha);
            System.out.println("Senha válido");
            return true;
        }catch (CpfInvalidoException e){
            System.out.println(e);
            return false;
        }
    }
}
