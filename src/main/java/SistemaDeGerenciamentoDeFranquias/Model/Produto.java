package SistemaDeGerenciamentoDeFranquias.Model;

import java.math.BigDecimal;

public class Produto {
    String nome;
    BigDecimal preco;
    String carac;
    BigDecimal quant;
    String codigo;

    public Produto(String nome, BigDecimal preco, String carac, BigDecimal quant, String codigo){
        this.nome = nome;
        this.preco = preco;
        this.carac = carac;
        this.quant = quant;
        this.codigo = codigo;
    }

    public String getNomeProd(){ return nome; }
    public void setNomeProd(String nome){ this.nome = nome; }

    public BigDecimal getPreco(){ return preco; }
    public void setPreco(BigDecimal preco){ this.preco = preco; }

    public String getCarac(){ return carac; }
    public void setCarac(String carac){ this.carac = carac; }

    public BigDecimal getQuant(){ return quant; }
    public void setQuant(BigDecimal quant){ this.quant = quant; }

    public String getCodigoProd(){ return codigo; }
    public void setCodigoProd(String codigo){ this.codigo = codigo; }
}
