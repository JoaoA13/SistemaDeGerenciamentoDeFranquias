package SistemaDeGerenciamentoDeFranquias;
import java.util.*;


public class GerenciadorDeLojas {

    static private Map<String, Loja > armazenaLojas = new HashMap<>();
    static private Map<String, Gerente > armazenaGerentes = new HashMap<>();

    protected GerenciadorDeLojas(){
    }

    protected Map<String, Loja> getLojas() {return armazenaLojas;}

    static protected void addLoja(String cpfGerente,Loja loja){
        armazenaLojas.put(cpfGerente,loja);
    }

    protected Loja getLoja(String cpfGerente){return armazenaLojas.get(cpfGerente);}

    protected static void cadastraLoja(String endereco, Gerente gerente){
        Loja loja = new Loja(endereco,gerente);
        addLoja(gerente.getCpf(),loja);
    }

    protected static void cadastraGerente(String cpf, Gerente gerente){
        armazenaGerentes.put(cpf,gerente);
    }

    public static Gerente getGerente(String cpf){
        return armazenaGerentes.getOrDefault(cpf,null);
    }
}
