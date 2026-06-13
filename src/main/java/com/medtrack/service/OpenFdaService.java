package com.medtrack.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OpenFdaService {

    private static final Map<String, String> TRADUCOES = new HashMap<>();

    static {
        TRADUCOES.put("losartana", "losartan");
        TRADUCOES.put("paracetamol", "acetaminophen");
        TRADUCOES.put("amoxicilina", "amoxicillin");
        TRADUCOES.put("ibuprofeno", "ibuprofen");
        TRADUCOES.put("omeprazol", "omeprazole");
        TRADUCOES.put("metformina", "metformin");
        TRADUCOES.put("atorvastatina", "atorvastatin");
        TRADUCOES.put("sinvastatina", "simvastatin");
        TRADUCOES.put("aspirina", "aspirin");
        TRADUCOES.put("enalapril", "enalapril");
        TRADUCOES.put("captopril", "captopril");
        TRADUCOES.put("metoprolol", "metoprolol");
        TRADUCOES.put("amlodipino", "amlodipine");
        TRADUCOES.put("clonazepam", "clonazepam");
        TRADUCOES.put("fluoxetina", "fluoxetine");
        TRADUCOES.put("sertralina", "sertraline");
        TRADUCOES.put("azitromicina", "azithromycin");
        TRADUCOES.put("ciprofloxacino", "ciprofloxacin");
        TRADUCOES.put("dexametasona", "dexamethasone");
        TRADUCOES.put("prednisona", "prednisone");
    }

    public String buscarMedicamento(String nome) {
        try {
            String nomeTraduzido = TRADUCOES.getOrDefault(
                    nome.toLowerCase().trim(), nome.toLowerCase().trim()
            );
            String nomeCodificado = nomeTraduzido.replace(" ", "+");
            String urlStr = "https://api.fda.gov/drug/label.json?search=openfda.generic_name:"
                    + nomeCodificado + "&limit=1";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status != 200) {
                return "Medicamento não encontrado na base da FDA.";
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return parseResposta(response.toString(), nome);

        } catch (Exception e) {
            return "Erro de conexão com a FDA: " + e.getMessage();
        }
    }

    private String parseResposta(String json, String nomeOriginal) {
        String fabricante = "";
        String nomeGenerico = "";
        String indicacao = "";
        String avisos = "";

        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            if (jsonObject.has("results")) {
                JsonArray resultsArray = jsonObject.getAsJsonArray("results");

                if (!resultsArray.isEmpty()) {
                    JsonObject firstResult = resultsArray.get(0).getAsJsonObject();

                    fabricante = extrairValorComGson(firstResult, "manufacturer_name");
                    nomeGenerico = extrairValorComGson(firstResult, "generic_name");
                    indicacao = extrairValorComGson(firstResult, "indications_and_usage");
                    avisos = extrairValorComGson(firstResult, "warnings");
                }
            }
        } catch (Exception e) {
            // Se falhar o parse, mantém vazio
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Informações da FDA: ")
                .append(nomeOriginal.toUpperCase()).append(" ---\n");

        if (!fabricante.isEmpty()) {
            sb.append("Fabricante    : ").append(fabricante).append("\n");
        }
        if (!nomeGenerico.isEmpty()) {
            sb.append("Nome Genérico : ").append(nomeGenerico).append("\n");
        }
        if (!indicacao.isEmpty()) {
            String ind = indicacao.length() > 250
                    ? indicacao.substring(0, 250) + "..." : indicacao;
            sb.append("Indicação     : ").append(ind).append("\n");
        }
        if (!avisos.isEmpty()) {
            String av = avisos.length() > 150
                    ? avisos.substring(0, 150) + "..." : avisos;
            sb.append("Avisos        : ").append(av).append("\n");
        }
        if (fabricante.isEmpty() && nomeGenerico.isEmpty()
                && indicacao.isEmpty() && avisos.isEmpty()) {
            sb.append("Informações detalhadas não disponíveis.\n");
        }

        sb.append("--------------------------------------------------");
        return sb.toString();
    }

    private String extrairValorComGson(JsonObject result, String chave) {
        JsonElement element = result.get(chave);

        if ((element == null || element.isJsonNull()) && result.has("openfda")) {
            JsonObject openfda = result.getAsJsonObject("openfda");
            if (openfda != null) {
                element = openfda.get(chave);
            }
        }

        if (element != null && element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            if (!array.isEmpty()) {
                return array.get(0).getAsString();
            }
        }

        return "";
    }
}
