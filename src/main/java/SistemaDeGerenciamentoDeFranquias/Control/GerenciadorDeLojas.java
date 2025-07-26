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

    static public Map<String, Loja> getLojas(){return armazenaLojas;}


    static public Loja getLoja(String cpfGerente){return armazenaLojas.getOrDefault(cpfGerente,null);}

    protected static void cadastraLoja(String endereco, Gerente gerente){
        String codigo = geraCodigoLoja();
        Loja loja = new Loja(gerente.getCpf(),endereco,gerente);
        addLoja(gerente.getCpf(),loja);
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

    static public void excluirLoja(String cpf){armazenaLojas.remove(cpf);}

    static public void excluirGerente(String cpf){armazenaGerentes.remove(cpf);}

}
