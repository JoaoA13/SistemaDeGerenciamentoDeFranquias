package SistemaDeGerenciamentoDeFranquias.Control;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

import java.util.*;


public class GerenciadorDeLojas {

    static int quantidadeDeLojas = 0;
    static private Map<String, Loja> armazenaLojas = new HashMap<>();
    static private Map<String, Gerente> armazenaGerentes = new HashMap<>();

    public GerenciadorDeLojas(){
        Gerente gerente = new Gerente("Pedroca", "12345678900", "pedrogameplay@gmail.com", "12345688");
        Loja lojinha = new Loja("12345678900","Rua da resenha",gerente);
        armazenaLojas.put("12345678900",lojinha);
        armazenaGerentes.put(gerente.getCpf(),gerente);
    }

    static protected void addLoja(String codigo,Loja loja){
        armazenaLojas.put(codigo,loja);
        quantidadeDeLojas++;
    }

    static public Loja getLoja(String codigo){return armazenaLojas.getOrDefault(codigo,null);}

    protected static void cadastraLoja(String endereco, Gerente gerente){
        String codigo = geraCodigoLoja();
        Loja loja = new Loja(codigo,endereco,gerente);
        addLoja(codigo,loja);
    }
    static protected String geraCodigoLoja(){
        String codigo = String.format("%03d", quantidadeDeLojas + 1);
        return codigo;
    }

    protected static void cadastraGerente(String cpf, Gerente gerente){
        armazenaGerentes.put(cpf,gerente);
    }

    public static Gerente getGerente(String cpf){
        return armazenaGerentes.getOrDefault(cpf,null);
    }
}
