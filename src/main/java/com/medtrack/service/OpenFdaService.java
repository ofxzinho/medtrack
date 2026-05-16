package com.medtrack.service;

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
        String fabricante = extrairValor(json, "manufacturer_name");
        String nomeGenerico = extrairValor(json, "generic_name");
        String indicacao = extrairValor(json, "indications_and_usage");
        String avisos = extrairValor(json, "warnings");

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

    private String extrairValor(String json, String chave) {
        int keyIdx = json.indexOf("\"" + chave + "\"");
        if (keyIdx == -1) {
            return "";
        }
        int arrayStart = json.indexOf("[", keyIdx);
        if (arrayStart == -1) {
            return "";
        }
        int strStart = json.indexOf("\"", arrayStart);
        if (strStart == -1) {
            return "";
        }
        strStart++;
        int strEnd = json.indexOf("\"", strStart);
        if (strEnd == -1) {
            return "";
        }
        return json.substring(strStart, strEnd);
    }
}
