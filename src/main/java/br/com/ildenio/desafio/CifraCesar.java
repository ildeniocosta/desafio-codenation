package br.com.ildenio.desafio;

import br.com.ildenio.desafio.br.com.ildenio.desafio.domain.Desafio;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CifraCesar {
    private static final String URL_BASE = "https://api.codenation.dev/v1/challenge/dev-ps";
    private static final String RECURSO_GENERETE_DATA = "/generate-data";
    private static final String TOKEN = "7a99e1efacb81989497286bdeef00c458f42c64b";

    public static void main(String[] args) throws IOException, InterruptedException {
        Desafio desafio = getDesafio();
        Desafio.decriptar(desafio);
        Desafio.resumir(desafio);
        System.out.println("\n\nTEXTO CRIPTOGRAFADO: " + desafio.getCifrado());
        System.out.println("TEXTO DESCRIPTOGRAFADO: " + desafio.getDecifrado());
        System.out.println("RESUMO: " + desafio.getResumoCriptografico());
        Desafio.salvarArquivo(desafio);
    }

    private static Desafio getDesafio() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + RECURSO_GENERETE_DATA + "?token=" + TOKEN))
                .build();
        HttpResponse<String> resposta = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(resposta.body(), Desafio.class);
    }
}
