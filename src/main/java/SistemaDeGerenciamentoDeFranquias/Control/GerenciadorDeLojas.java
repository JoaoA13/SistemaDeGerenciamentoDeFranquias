package SistemaDeGerenciamentoDeFranquias.Control;
import SistemaDeGerenciamentoDeFranquias.Arquivos.salvaArquivos;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;
import SistemaDeGerenciamentoDeFranquias.Model.Usuario;

import java.util.*;


public class GerenciadorDeLojas {

    static int quantidadeDeLojas = 0;
    static private Map<String, Loja> armazenaLojas = new HashMap<>();
    static private Map<String, Gerente> armazenaGerentes = new HashMap<>();
    static private Map<String, String> codigoParaCpf = new HashMap<>();

    //CARREGANDO ARQUIVOS
    public static void carregaArmazenaGerentes() {
        Map<String, Gerente> recuperado = salvaArquivos.carregarGerentes();
        if (recuperado != null) {
            armazenaGerentes = recuperado;
        }
    }
    public static void carregaArmazenaLojas() {
        Map<String, Loja> recuperado = salvaArquivos.carregarLojas();
        if (recuperado != null) {
            armazenaLojas = recuperado;
        }
    }

    public static void carregaCodigoParaCpf() {
        Map<String, String> recuperado = salvaArquivos.carregarCodigos();
        if (recuperado != null) {
            codigoParaCpf = recuperado;
        }
    }

    public GerenciadorDeLojas(){
        carregaCodigoParaCpf();
        carregaArmazenaGerentes();
        carregaArmazenaLojas();
        quantidadeDeLojas = salvaArquivos.carregarQuantidadeDeLojas();
    }
    static public Map<String, Loja> getLojas(){return armazenaLojas;}
    static public Loja getLoja(String Codigo){
        if(armazenaLojas.getOrDefault(Codigo,null) == null )//conferindo se é cpf
            return armazenaLojas.get(codigoParaCpf.get(Codigo));
        else
            return armazenaLojas.getOrDefault(Codigo,null);//se nao for cpf vai pelo codigo
    }
    protected static void cadastraLoja(String endereco, Gerente gerente){
        String codigo = geraCodigoLoja();
        addLoja(endereco,gerente,codigo);
    }
    protected static void addLoja(String endereco, Gerente gerente,String codigo){
        Loja loja = new Loja(codigo,endereco,gerente);
        armazenaLojas.put(codigo,loja);
        codigoParaCpf.put(gerente.getCpf(),codigo);
        salvaArquivos.salvarLojas(armazenaLojas);
        salvaArquivos.salvarCodigos(codigoParaCpf);
        salvaArquivos.salvarGerentes(armazenaGerentes);

        quantidadeDeLojas++;
        salvaArquivos.salvarQuantidadeDeLojas(quantidadeDeLojas);
    }
    static public void excluirLoja(String codigo){
        if(getLoja(codigo) == null)
            return;

        armazenaLojas.remove(codigo);
        salvaArquivos.salvarLojas(armazenaLojas);
        salvaArquivos.salvarCodigos(codigoParaCpf);
        salvaArquivos.salvarGerentes(armazenaGerentes);
    }

    static protected String geraCodigoLoja(){
        String codigo = String.format("%03d", quantidadeDeLojas + 1);
        return codigo;
    }
    static public String getCodigoLoja(String cpfGerente){
        if(getLoja(cpfGerente) == null)
            return "Sem loja";
        else
            return getLoja(cpfGerente).getCodigo();
    }
    static public String getCpfPorCodigo(String codigo){
        String cpfGerente = getLoja(codigo).getCpfGerente();
        if(cpfGerente != null)
            return cpfGerente;
        else
            return "SEM GERENTE";
    }
    static public Map<String,String> getCodigoPraCpf(){return codigoParaCpf;}

    static public Map<String, Gerente> getGerentes(){return armazenaGerentes;}
    public static Gerente getGerente(String cpf){
        cpf = cpf.replaceAll("[^\\d]", "");
        return armazenaGerentes.get(cpf);
    }
    protected static void cadastraGerente(String cpf, Gerente gerente){
        cpf = cpf.replaceAll("[^\\d]", "");
        armazenaGerentes.put(cpf,gerente);
        salvaArquivos.salvarGerentes(armazenaGerentes);
    }
    static public void excluirGerente(String cpf){
        Loja loja = getLoja(cpf);
        if(loja != null){
            getCodigoPraCpf().remove(cpf);
            loja.setGerenteDaUnidade(null);
        }
        armazenaGerentes.remove(cpf);
        salvaArquivos.salvarLojas(armazenaLojas);
        salvaArquivos.salvarCodigos(codigoParaCpf);
        salvaArquivos.salvarGerentes(armazenaGerentes);
    }
    static public void trocarGerente(String codigo,Gerente gerenteNovo){
        if(getLoja(codigo) == null)
            return;
        getLoja(codigo).setGerenteDaUnidade(gerenteNovo);
        salvaArquivos.salvarLojas(armazenaLojas);
        salvaArquivos.salvarCodigos(codigoParaCpf);
        salvaArquivos.salvarGerentes(armazenaGerentes);
    }

    static public Usuario getVendedorGeral(String cpf){
        for (Loja loja : getLojas().values()) {
            if(loja == null)
                continue;
            if (loja.getVendedor(cpf) != null)
                return loja.getVendedor(cpf);
        }
        return null;
    }
}
