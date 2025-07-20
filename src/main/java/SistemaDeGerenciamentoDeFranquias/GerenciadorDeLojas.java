package SistemaDeGerenciamentoDeFranquias;
import java.util.*;


public class GerenciadorDeLojas {

    static int quantidadeDeLojas = 0;
    private Map<String, Loja > armazenaLojas = new HashMap<>();

    protected GerenciadorDeLojas(){
    }

    protected Map<String, Loja> getLojas() { return armazenaLojas;}

    protected void addLoja(String codigo,Loja loja){
        armazenaLojas.put(codigo,loja);
        quantidadeDeLojas++;
    }

    protected Loja getLoja(String codigo){return armazenaLojas.get(codigo);}

    void cadastraLoja(String endereco,Gerente gerente){
        Loja loja = new Loja(geraCodigoLoja(),endereco,gerente);
        addLoja(loja.getCodigo(),loja);
    }

    static protected String geraCodigoLoja(){
        String codigo = String.format("%03d", quantidadeDeLojas);
        return codigo;
    }
}
