package SistemaDeGerenciamentoDeFranquias.Model;

import java.util.HashMap;
import java.util.Map;

public class ArmazenaAlteracao <T>{
    private Loja loja;
    public ArmazenaAlteracao(Loja loja) {
        this.loja = loja;
    }

    public Map<String, T> armazenaAlteracao = new HashMap<>();

    public Map<String, T> getAlteracoes() {
        if (loja.getArmazenaPedidosAltera() != null)
            return armazenaAlteracao;
        return null;
    }
    public void addAlteracao(String codigo, T alteracao) {
        armazenaAlteracao.put(codigo, alteracao);
    }
    public T getAlteracao(String codigo){
        return armazenaAlteracao.get(codigo);
    }

}
