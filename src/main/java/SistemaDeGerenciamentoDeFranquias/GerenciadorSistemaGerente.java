package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;

public class GerenciadorSistemaGerente extends GerenciadorSistema{
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
