package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Usuario;

public interface ValidadorLogin {
    static public boolean validarLogin(Usuario usuario, String cpfDigitado, String senhaDigitada)  throws LoginException {
        if (usuario.getCpf().equals(cpfDigitado) && usuario.getSenha().equals(senhaDigitada))
            return true;
        else{
            throw new LoginException("CPF ou senha incorretos, digite novamente!");
        }
    }
}
