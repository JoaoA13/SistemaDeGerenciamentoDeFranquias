package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;

public abstract class GerenciadorSistema {

    protected Dono dono = new Dono("João","141279456-05","joao@gmail","12345678");


    public GerenciadorSistema(){
    }

    boolean login(String nome,String senha){
        return false;
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
