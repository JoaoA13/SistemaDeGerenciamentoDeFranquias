package SistemaDeGerenciamentoDeFranquias;
import java.util.*;


public class GerenciadorDeLojas {

    static int quantidadeDeLojas = 0;
    static private Map<String, Loja > armazenaLojas = new HashMap<>();
    static private Map<String, Gerente > armazenaGerentes = new HashMap<>();

    protected GerenciadorDeLojas(){
    }

    protected Map<String, Loja> getLojas() {return armazenaLojas;}

    static protected void addLoja(String codigo,Loja loja){
        armazenaLojas.put(codigo,loja);
        quantidadeDeLojas++;
    }

    protected Loja getLoja(String codigo){return armazenaLojas.get(codigo);}

    protected static void cadastraLoja(String endereco, Gerente gerente){
        Loja loja = new Loja(geraCodigoLoja(),endereco,gerente);
        addLoja(loja.getCodigo(),loja);
    }

    static protected String geraCodigoLoja(){
        String codigo = String.format("%03d", quantidadeDeLojas);
        return codigo;
    }

    protected static void cadastraGerente(String cpf, Gerente gerente){
        armazenaGerentes.put(cpf,gerente);
    }

    public static Gerente getGerente(String cpf){
        return armazenaGerentes.getOrDefault(cpf,null);
    }
}
