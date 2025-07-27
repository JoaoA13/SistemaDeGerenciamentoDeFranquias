package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;

public class Vendedor extends Usuario {
    private BigDecimal valorVenda = BigDecimal.valueOf(0);

    protected Vendedor(String nome,String cpf, String email, String senha){
        super(nome,cpf,email,senha);
    }

    public BigDecimal getValorVenda(){
        return valorVenda;
    }
    protected void setValorVenda(BigDecimal venda){valorVenda = valorVenda.add(venda);}
}
