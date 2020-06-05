package br.com.ildenio.desafio.br.com.ildenio.desafio.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

public class Desafio {
    transient
    private static final String NOME_ARQUIVO = "answer.json";
    @SerializedName("numero_casas")
    private int numeroCasas;
    private String token;
    private String cifrado;
    private String decifrado;
    @SerializedName("resumo_criptografico")
    private String resumoCriptografico;

    public static void salvarArquivo(Desafio desafio) throws FileNotFoundException {
        Formatter formatter = new Formatter(NOME_ARQUIVO);
        formatter.format(new Gson().toJson(desafio));
        formatter.close();
    }

    public int getNumeroCasas() {
        return numeroCasas;
    }

    public String getCifrado() {
        return cifrado;
    }

    public String getDecifrado() {
        return decifrado;
    }

    public void setDecifrado(String decifrado) {
        this.decifrado = decifrado;
    }

    public String getResumoCriptografico() {
        return resumoCriptografico;
    }

    public void setResumoCriptografico(String resumoCriptografico) {
        this.resumoCriptografico = resumoCriptografico;
    }

    public static void decriptar(Desafio desafio) {
        char[] alfabeto = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder resultado = new StringBuilder();
        String texto = desafio.getCifrado().toLowerCase();
        int tamanhoTexto = texto.length();

        for (int c = 0; c < tamanhoTexto; c++) {
            int indexLetra = isAlfabeto(texto.charAt(c), alfabeto);
            if(indexLetra != -1){
                resultado.append(alfabeto[getletraDescritografada(indexLetra, alfabeto.length, desafio.getNumeroCasas())]);
            }else{
                resultado.append(texto.charAt(c));
            }
        }
        desafio.setDecifrado(resultado.toString());
    }

    private static int getletraDescritografada(int indexLetra, int length, int chave) {
        int resultadoAplicacaoDaChave = indexLetra - (chave);
        if(resultadoAplicacaoDaChave < 0){
            return length + resultadoAplicacaoDaChave;
        }
        return resultadoAplicacaoDaChave;
    }

    private static int isAlfabeto(char caracter, char[] alfabeto) {
        for (int i = 0; i < alfabeto.length; i++){
            if(alfabeto[i] == caracter){
                return i;
            }
        }
        return -1;
    }

    public static void resumir(Desafio desafio){
        String sha1;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(desafio.getDecifrado().getBytes(StandardCharsets.UTF_8));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
            desafio.setResumoCriptografico(sha1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
