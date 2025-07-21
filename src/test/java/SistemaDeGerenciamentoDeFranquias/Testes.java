package SistemaDeGerenciamentoDeFranquias;

import SistemaDeGerenciamentoDeFranquias.Exceptions.CpfInvalidoException;
import SistemaDeGerenciamentoDeFranquias.Validadores.ValidadorCpf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class Testes {

    @Test
    public void testaValidadorCpf(){
        GerenciadorSistemaDono  g = new GerenciadorSistemaDono();
        assertNotNull(g.confereCpf("14127945605"), "Cpf válido");
        assertNotNull(g.confereCpf("141.279.456-05"), "Cpf válido");
        assertNotNull(g.confereCpf("141.279.    456-05"), "Cpf válido");
        assertNotNull(g.confereCpf("141279"), "Cpf inválido");
        assertNotNull(g.confereCpf("141279456056"), "Cpf inválido");
        assertNotNull(g.confereCpf("14127945asd"), "Cpf inválido");
    }
}
