package SistemaDeGerenciamentoDeFranquias;
import java.util.*;


public class GerenciadorDeLojas {

    private Map<String, Loja > armazenaLojas = new HashMap<>();

    protected GerenciadorDeLojas(){
    }

    protected Map<String, Loja> getLojas() { return armazenaLojas;}

    protected void addLoja(String codigo,Loja loja){armazenaLojas.put(codigo,loja);}

    protected Loja getLoja(String codigo){return armazenaLojas.get(codigo);}

}
