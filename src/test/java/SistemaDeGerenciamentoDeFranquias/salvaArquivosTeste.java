package SistemaDeGerenciamentoDeFranquias;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import SistemaDeGerenciamentoDeFranquias.Model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class salvaArquivosTeste {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new SistemaDeGerenciamentoDeFranquias.Arquivos.Adaptador.LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new SistemaDeGerenciamentoDeFranquias.Arquivos.Adaptador.LocalTimeAdapter())
            .setPrettyPrinting()
            .create();

    private static final String CAMINHO_DONO = "resources/donoTeste.json";
    private static final String CAMINHO_GERENTES = "resources/gerentesTeste.json";
    private static final String CAMINHO_VENDEDORES = "resources/vendedoresTeste.json";
    private static final String CAMINHO_LOJAS = "resources/lojaTeste.json";
    private static final String CAMINHO_CODIGOS = "resources/codigosTeste.json";
    // ===================== SALVAR =====================

    public static void salvarDonos(Map<String, Dono> dono) {
        salvarEmArquivo(dono, CAMINHO_DONO);
    }

    public static void salvarGerentes(Map<String, Gerente> gerentes) {
        salvarEmArquivo(gerentes, CAMINHO_GERENTES);
    }
    public static void salvarCodigos(Map<String, String> codigos) {
        salvarEmArquivo(codigos, CAMINHO_CODIGOS);
    }

    public static void salvarVendedores(List<Vendedor> vendedores) {
        salvarEmArquivo(vendedores, CAMINHO_VENDEDORES);
    }

    public static void salvarLojas(Map<String, Loja> lojas) {
        salvarEmArquivo(lojas, CAMINHO_LOJAS);
    }

    // ===================== CARREGAR =====================

    public static Map<String, Dono> carregarDono() {
        Type tipo = new TypeToken<Map<String, Dono>>() {}.getType();
        return carregarDeArquivo(CAMINHO_DONO, tipo);
    }

    public static Map<String, Gerente> carregarGerentes() {
        Type tipo = new TypeToken<Map<String, Gerente>>() {}.getType();
        return carregarDeArquivo(CAMINHO_GERENTES, tipo);
    }

    public static Map<String, String> carregarCodigos() {
        Type tipo = new TypeToken<Map<String, String>>() {}.getType();
        return carregarDeArquivo(CAMINHO_CODIGOS, tipo);
    }

    public static List<Vendedor> carregarVendedores() {
        Type tipo = new TypeToken<List<Vendedor>>() {}.getType();
        return carregarDeArquivo(CAMINHO_VENDEDORES, tipo);
    }

    public static Map<String, Loja> carregarLojas() {
        Type tipo = new TypeToken<Map<String, Loja>>() {}.getType();
        return carregarDeArquivo(CAMINHO_LOJAS, tipo);
    }

    // ===================== AUXILIARES =====================

    private static <T> void salvarEmArquivo(T objeto, String caminho) {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(objeto, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> T carregarDeArquivo(String caminho, Type tipo) {
        try (FileReader reader = new FileReader(caminho)) {
            return gson.fromJson(reader, tipo);
        } catch (IOException e) {
            System.err.println("Arquivo não encontrado ou erro ao ler: " + caminho);
            return null;
        }
    }


}
