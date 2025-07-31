package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Vendedor extends Usuario {
    private Map<String, Pedido> pedidosOficial = new HashMap<>();

    private BigDecimal valorVenda = BigDecimal.valueOf(0);
    String codigoLoja;

    protected Vendedor(String nome,String cpf, String email, String senha, String codigoLoja){
        super(nome,cpf,email,senha);
        this.codigoLoja = codigoLoja;
    }
    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.VENDEDOR;
    }

    public Map<String, Pedido> getPedidosOficial(){
        if(pedidosOficial == null)
            pedidosOficial = new HashMap<>();
        return pedidosOficial;
    }
    public void setPedidosOficial(Map<String, Pedido> pedidos){
        pedidosOficial = pedidos;
    }
    public Pedido getPedido(String codigo){
        Pedido pedido = pedidosOficial.get(codigo);
        if(pedidosOficial.get(codigo) != null)
            return pedido;
        else
            return null;
    }

    public void addPedido(Pedido pedido){ pedidosOficial.put(pedido.getCodigo(), pedido); }

    public BigDecimal getValorVenda(){
        return valorVenda;
    }
    public void setValorVenda(BigDecimal venda){valorVenda = valorVenda.add(venda);}

    public String getCodigoLoja(){ return codigoLoja; }
    public void setCodigoLoja(String codigoLoja) {
        this.codigoLoja = codigoLoja;
    }
}
