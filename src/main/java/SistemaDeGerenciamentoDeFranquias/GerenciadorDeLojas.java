package SistemaDeGerenciamentoDeFranquias;
import java.util.*;


public class GerenciadorDeLojas {

    static private Map<String, Loja > armazenaLojas = new HashMap<>();
    static private Map<String, Gerente > armazenaGerentes = new HashMap<>();

    protected GerenciadorDeLojas(){
        Gerente gerente = new Gerente("Pedroca", "12345678900", "pedrogameplay@gmail.com", "12345688");
        Loja lojinha = new Loja("Rua da resenha",gerente);
        armazenaLojas.put(gerente.getCpf(),lojinha);
        armazenaGerentes.put(gerente.getCpf(),gerente);
    }

    static public Map<String, Loja> getLojas() {return armazenaLojas;}

    static protected void addLoja(String cpfGerente,Loja loja){
        armazenaLojas.put(cpfGerente,loja);
    }

    protected Loja getLoja(String cpfGerente){return armazenaLojas.getOrDefault(cpfGerente,null);}

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
