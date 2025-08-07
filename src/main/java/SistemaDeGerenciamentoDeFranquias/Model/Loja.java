package SistemaDeGerenciamentoDeFranquias.Model;

import SistemaDeGerenciamentoDeFranquias.Control.GerenciadorDeLojas;
import SistemaDeGerenciamentoDeFranquias.Exceptions.BancoDeDadosException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Loja<T> {

    private String codigo;
    private String endereco;
    private Gerente gerenteDaUnidade;
    public Map<String, Vendedor> armazenaVendedores = new HashMap<>();
    public Map<String, Produto> armazenaProdutos = new HashMap<>();
    public Map<String, Cliente> armazenaClientes = new HashMap<>();
    public Map<String, Pedido> armazenaPedidosAltera = new HashMap<>();
    public Map<String, T> armazenaAlteracao = new HashMap<>();
    public Map<String, T> armazenaAtual = new HashMap<>();

    public Loja(String codigo, String endereco, Gerente gerenteDaUnidade){
        this.endereco = endereco;
        this.gerenteDaUnidade = gerenteDaUnidade;
        this.codigo = codigo;
//        Vendedor vendedor1 = new Vendedor("Gleiph", "14714714714","socorro@gmail.com", "12345677", codigo);
//        Vendedor vendedor2 = new Vendedor("Carlos", "45645645645","eitanois@gmail.com", "12345677", codigo);
//        Vendedor vendedor3 = new Vendedor("Ana", "78978978978","Tome-lheForro@gmail.com", "12345677", codigo);
//        armazenaVendedores.put("14714714714",vendedor1);
//        armazenaVendedores.put("45645645645",vendedor2);
//        armazenaVendedores.put("78978978978",vendedor3);
    }

    /// Getters e Setters
    public String getCodigo(){return codigo;}
    public void setCodigo(String novoCodigo){this.codigo = novoCodigo;}

    public String getCpfGerente() {
        if(gerenteDaUnidade == null)
            return null;
        if (gerenteDaUnidade.getCpf().isEmpty())
            return null;
        else
        return gerenteDaUnidade.getCpf();
    }
    protected void setCpfGerente(String cpf) {gerenteDaUnidade.setCpf(cpf);}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public Gerente getGerenteDaUnidade() {return gerenteDaUnidade;}
    public void setGerenteDaUnidade(Gerente gerenteDaUnidade) {this.gerenteDaUnidade = gerenteDaUnidade;}

    public Map<String, Vendedor> getArmazenaVendedores() { return armazenaVendedores; }

    public void addVendedor(String nome, String cpf, String email, String senha){
        Vendedor vendedor = new Vendedor(nome, cpf, email, senha, codigo);
        armazenaVendedores.put(cpf,vendedor);
    }
    public void excluirVendedor(String cpf){armazenaVendedores.remove(cpf);}

    public Vendedor getVendedor(String cpf){
        return armazenaVendedores.getOrDefault(cpf,null);
    }

    public Vendedor[] rankVendedores(){
        List<Vendedor> lista = new ArrayList<>(getArmazenaVendedores().values());
        Collections.sort(lista, (v1, v2) -> v2.getValorVenda().compareTo(v1.getValorVenda()));
        return lista.toArray(new Vendedor[0]);
    }
    public Vendedor[] vendedoresVolume(){
        List<Vendedor> lista = new ArrayList<>(getArmazenaVendedores().values());
        lista.sort(Comparator.comparingInt((Vendedor v) -> v.getVolumeVendas()).reversed());;
        return lista.toArray(new Vendedor[0]);
    }


    public BigDecimal calculaFaturamentoBruto(){
       BigDecimal faturamentoBruto = BigDecimal.ZERO;
        for(Vendedor vendedor : armazenaVendedores.values()){
            if (faturamentoBruto!=null)
                faturamentoBruto = faturamentoBruto.add(vendedor.getValorVenda());
            if (faturamentoBruto==null)
                System.out.println(faturamentoBruto + "aqui"+vendedor.getValorVenda());
        };
        return faturamentoBruto;
    }

    public Map<String, Produto> getArmazenaProdutos() { return armazenaProdutos; }

    public void addProduto(String nome, BigDecimal preco, String carac, BigDecimal quant, String codigo){
        Produto produto = new Produto(nome, preco, carac, quant, codigo);
        armazenaProdutos.put(codigo,produto);
    }

    public void addProdutoDireto(Produto produto){ armazenaProdutos.put(produto.getCodigoProd(), produto); GerenciadorDeLojas.salvaLojaGerente();}

    public void excluirProduto(String codigo){armazenaProdutos.remove(codigo);GerenciadorDeLojas.salvaLojaGerente();}

    public void addCliente(Cliente cliente){ armazenaClientes.put(cliente.getCpf(), cliente); GerenciadorDeLojas.salvaLojaGerente();}

    public void excluirCliente(String cpf){armazenaClientes.remove(cpf);GerenciadorDeLojas.salvaLojaGerente();}

    public Cliente getCliente(String cpf) {
        if (armazenaClientes == null) {
            armazenaClientes = new HashMap<>();
        }
        return armazenaClientes.getOrDefault(cpf, null);
    }

    public Map<String, Cliente> getArmazenaClientes() {
        if (armazenaClientes != null)
            return armazenaClientes;
        return null;
    }

    public Cliente[] ordenaClientes(){
        if (getArmazenaClientes()==null)
            return null;
        List<Cliente> lista = new ArrayList<>(getArmazenaClientes().values());
        Collections.sort(lista, (v1, v2) -> v2.getQuantidadeCompras().compareTo(v1.getQuantidadeCompras()));
        return lista.toArray(new Cliente[0]);
    }

    public Produto getProduto(String codigo){
        return armazenaProdutos.getOrDefault(codigo,null);
    }

    public int calculaTotalPedidos(){
        int totPedidos = 0;
        for(Vendedor vendedor : armazenaVendedores.values()){
            totPedidos = totPedidos + vendedor.getPedidosOficial().size();
        };
        return totPedidos;
    }
    public Float calculaTicketMedio(){
        BigDecimal valorTotal = BigDecimal.ZERO;
        float valTotFloat = valorTotal.floatValue();
        if(armazenaClientes != null) {
            valorTotal = calculaFaturamentoBruto();
            valTotFloat = valorTotal.floatValue();

            return valTotFloat / calculaTotalPedidos();
        }else
            return valTotFloat;
    }

    public Pedido getPedidoAltera (String codigo) {
        if (armazenaPedidosAltera == null) {
            armazenaPedidosAltera = new HashMap<>();
        }
        return armazenaPedidosAltera.getOrDefault(codigo, null);
    }

    public T getArmazenaAlteracao (String codigo) {
        if (armazenaAlteracao == null) {
            armazenaAlteracao = new HashMap<>();
        }
        return armazenaAlteracao.getOrDefault(codigo, null);
    }

    public T getArmazenaAtual (String codigo) {
        if (armazenaAtual == null) {
            armazenaAtual = new HashMap<>();
        }
        return armazenaAtual.getOrDefault(codigo, null);
    }

    public void addPedidosAltera(Pedido pedido, T texto, T texto2, int escolha){
        if (armazenaPedidosAltera == null) {
            armazenaPedidosAltera = new HashMap<>();
        }
        if (armazenaAlteracao == null) {
            armazenaAlteracao = new HashMap<>();
        }
        if (armazenaAtual == null) {
            armazenaAtual = new HashMap<>();
        }
        armazenaPedidosAltera.put(pedido.getCodigo(),pedido);
        armazenaAlteracao.put(pedido.getCodigo(), texto);
        armazenaAtual.put(pedido.getCodigo(), texto2);

        GerenciadorDeLojas.salvaLojaGerente();
    }

    public Map<String, Pedido> getArmazenaPedidosAltera() {
        if (armazenaPedidosAltera == null)
            armazenaPedidosAltera = new HashMap<>();
        return armazenaPedidosAltera;
    }

    public Map<String, T> getArmazenaAlteracoes() {
        if (armazenaAlteracao == null)
            armazenaAlteracao = new HashMap<>();
        return armazenaAlteracao;
    }

    public Map<String, T> getArmazenaAtual() {
        if (armazenaAtual == null)
            armazenaAtual = new HashMap<>();
        return armazenaAtual;
    }

    public Pedido getPedido(String cod){
        for (Vendedor vendedor : (Collection<Vendedor>) armazenaVendedores.values()){
            if(vendedor.getPedido(cod) != null)
                return vendedor.getPedido(cod);
        }
        return null;
    }

    public void excluirPedido(Pedido pedido){
        if(pedido!= null) {
            Vendedor vendedor = getVendedor(pedido.getCpfVendedor());
            vendedor.excluirPedido(pedido.getCodigo());
            armazenaPedidosAltera.remove(pedido.getCodigo());
            armazenaAtual.remove(pedido.getCodigo());
            armazenaAlteracao.remove(pedido.getCodigo());
        }
        GerenciadorDeLojas.salvaLojaGerente();
    }

    public void excluirAlteracao(String codigo){
            armazenaPedidosAltera.remove(codigo);
            armazenaAtual.remove(codigo);
            armazenaAlteracao.remove(codigo);

        GerenciadorDeLojas.salvaLojaGerente();
    }

    public void acatarAlteracao(String codigo){
        Pedido pedido = getPedido(codigo);
        if(pedido != null) {
            if (getArmazenaAtual(codigo) instanceof LocalDate)
                pedido.setData((LocalDate) getArmazenaAlteracao(codigo));
            else if (getArmazenaAtual(codigo) instanceof LocalTime)
                pedido.setHora((LocalTime) getArmazenaAlteracao(codigo));
            else if (getArmazenaAtual(codigo) instanceof BigDecimal)
                pedido.setTaxaEntrega((BigDecimal) getArmazenaAlteracao(codigo));
            else if (getArmazenaAtual(codigo) instanceof String){
                if (getArmazenaAlteracao(codigo) == "Exclusão")
                    excluirPedido(pedido);
                else if(((String) getArmazenaAtual(codigo)).length() == 3 && ((String) getArmazenaAtual(codigo)).chars().allMatch(Character::isDigit))
                    pedido.setCodigo((String) getArmazenaAlteracao(codigo), GerenciadorDeLojas.getLoja(this.codigo));
                else if("Dinheiro Físico".equals(getArmazenaAtual(codigo)) ||"Pix".equals(getArmazenaAtual(codigo)) || "Cartão".equals(getArmazenaAtual(codigo)))
                    pedido.setFormaDePagamento((String) getArmazenaAlteracao(codigo));
                else
                    pedido.getCliente().setCpf((String) getArmazenaAlteracao(codigo));
            }

        }
        armazenaPedidosAltera.remove(codigo);
        armazenaAtual.remove(codigo);
        armazenaAlteracao.remove(codigo);

        GerenciadorDeLojas.salvaLojaGerente();
    }

    public void qntProdPedido(String cpfVendedor, String codigoP, BigDecimal quantidade){
        Vendedor vendedor = getVendedor(cpfVendedor);

        vendedor.getPedido(codigoP).qntProdPedido(codigoP,quantidade);

        GerenciadorDeLojas.salvaLojaGerente();
    }

}
