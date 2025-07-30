package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;

public class Vendedor extends Usuario {
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

    public BigDecimal getValorVenda(){
        return valorVenda;
    }
    protected void setValorVenda(BigDecimal venda){valorVenda = valorVenda.add(venda);}

    public String getCodigoLoja(){ return codigoLoja; }
    public void setCodigoLoja(String codigoLoja) {
        this.codigoLoja = codigoLoja;
    }
}
