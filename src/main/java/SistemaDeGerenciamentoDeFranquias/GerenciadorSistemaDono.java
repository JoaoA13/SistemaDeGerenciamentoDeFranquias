package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;
import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCampoVazio;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorLogin;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorSenha;

public class GerenciadorSistemaDono extends GerenciadorSistema{
    private GerenciadorDeLojas gerenciadorDeLojas = new GerenciadorDeLojas();
    private String senhaGerentePadrão = "12345678";

    public GerenciadorSistemaDono(){
    }
    @Override
    String login(String cpf,String senha) throws LoginException{
        super.login(cpf,senha);

        try {
            ValidadorCampoVazio.valida(cpf);
            ValidadorCampoVazio.valida(senha);
        }catch (EntradaException e){
            System.out.println("Erro: EntradaException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }

        try {
            ValidadorCpf.validarCpf(cpf);
            ValidadorSenha.valida(senha);
            ValidadorLogin.valida(dono,cpf,senha);
                    return "CPF e senha corretos";
        }catch (LoginException e){
            System.out.println("Erro: LoginException: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    void cadastroLoja(String endereco,String nomeGerente, String cpfGerente, String emailGerente){



        Gerente gerente = new Gerente(nomeGerente,cpfGerente,emailGerente,senhaGerentePadrão);
        gerenciadorDeLojas.cadastraLoja(endereco,gerente);
    }
}
