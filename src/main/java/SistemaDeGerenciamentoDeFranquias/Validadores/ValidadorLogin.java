package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.LoginException;
import SistemaDeGerenciamentoDeFranquias.Model.Usuario;

public interface ValidadorLogin {
    static public boolean valida(Usuario usuario, String cpfDigitado, String senhaDigitada)  throws LoginException {
        String cpfUsuario = usuario.getCpf().replaceAll("[^\\d]", "");
        cpfDigitado = cpfDigitado.replaceAll("[^\\d]", "");
        if (cpfUsuario.equals(cpfDigitado) && usuario.getSenha().equals(senhaDigitada))
            return true;
        else{
            throw new LoginException("CPF ou senha incorretos, digite novamente!");
        }
    }
}
