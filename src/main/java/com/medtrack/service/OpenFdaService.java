package com.medtrack.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenFdaService {

    public String buscarMedicamento(String nome) {
        try {
            String nomeCodificado = nome.replace(" ", "+");
            String urlStr = "https://api.fda.gov/drug/label.json?search=openfda.brand_name:"
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

            return parseResposta(response.toString());

        } catch (Exception e) {
            return "Erro de conexão com a FDA: " + e.getMessage();
        }
    }

    private String parseResposta(String json) {
        String fabricante = extrairValor(json, "manufacturer_name");
        String uso = extrairPrimeiro(json, "indications_and_usage");

        StringBuilder resultado = new StringBuilder();
        resultado.append("\n--- Informações da FDA ---\n");

        if (!fabricante.isEmpty()) {
            resultado.append("Fabricante : ").append(fabricante).append("\n");
        }
        if (!uso.isEmpty()) {
            String usoResumido = uso.length() > 200 ? uso.substring(0, 200) + "..." : uso;
            resultado.append("Indicação  : ").append(usoResumido).append("\n");
        }
        resultado.append("--------------------------");

        return resultado.toString();
    }

    private String extrairValor(String json, String chave) {
        String busca = "\"" + chave + "\":[\"";
        int inicio = json.indexOf(busca);
        if (inicio == -1) {
            return "";
        }
        inicio += busca.length();
        int fim = json.indexOf("\"", inicio);
        return fim == -1 ? "" : json.substring(inicio, fim);
    }

    private String extrairPrimeiro(String json, String chave) {
        return extrairValor(json, chave);
    }
}
