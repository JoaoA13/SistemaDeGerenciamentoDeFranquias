package SistemaDeGerenciamentoDeFranquias.Control;
import SistemaDeGerenciamentoDeFranquias.Model.Gerente;
import SistemaDeGerenciamentoDeFranquias.Model.Loja;

import java.util.*;


public class GerenciadorDeLojas {

    static int quantidadeDeLojas = 0;
    static private Map<String, Loja> armazenaLojas = new HashMap<>();
    static private Map<String, Gerente> armazenaGerentes = new HashMap<>();
    static private Map<String, String> codigoParaCpf = new HashMap<>();

    public GerenciadorDeLojas(){
        Gerente gerente = new Gerente("Pedroca", "12345678900", "pedrogameplay@gmail.com", "12345688");
        Loja lojinha = new Loja("12345678900","Rua da resenha",gerente);
        armazenaLojas.put("12345678900",lojinha);
        cadastraGerente("12345678900",gerente);
    }
    static public Map<String, Loja> getLojas(){return armazenaLojas;}
    static public Loja getLoja(String Codigo){
        if(armazenaLojas.getOrDefault(Codigo,null) != null )//conferindo se é cpf
            return armazenaLojas.getOrDefault(Codigo,null);
        else
            return armazenaLojas.getOrDefault(codigoParaCpf.get(Codigo),null);//se nao for cpf vai pelo codigo
    }
    protected static void cadastraLoja(String endereco, Gerente gerente){
        String codigo = geraCodigoLoja();
        Loja loja = new Loja(codigo,endereco,gerente);

        armazenaLojas.put(gerente.getCpf(),loja);
        codigoParaCpf.put(codigo, gerente.getCpf());

        quantidadeDeLojas++;
    }
    static public void excluirLoja(String cpf){armazenaLojas.remove(cpf);}

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


    static public Map<String, Gerente> getGerentes(){return armazenaGerentes;}
    public static Gerente getGerente(String cpf){
        cpf = cpf.replaceAll("[^\\d]", "");
        return armazenaGerentes.get(cpf);
    }
    protected static void cadastraGerente(String cpf, Gerente gerente){
        cpf = cpf.replaceAll("[^\\d]", "");
        armazenaGerentes.put(cpf,gerente);
    }
    static public void excluirGerente(String cpf){armazenaGerentes.remove(cpf);}
}
