package br.com.projetobackmavensearchwords.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.config.CookieSpecs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchWords {

    public static void main(String[] args) throws IOException {
        // Izabela Cecília
        String url = "";
        String frase = "";
        String conteudo = "";
        int contagemFrase  = 0;
        Map<String, Integer> repeticoesPalavras;

        Scanner ent = new Scanner(System.in);

        System.out.println("Digite a URL: ");
        url = ent.nextLine();

        System.out.println("Digite uma frase para pesquisa: ");
        frase = ent.nextLine();


        Document document = Jsoup.connect(url).get();
        conteudo = findMainContent(document);
        
        System.out.println(conteudo);
        contagemFrase = contarRepeticoes(conteudo, frase);
        
        repeticoesPalavras = contarRepeticoesPalavras(frase,conteudo);
        
        for(String palavra:repeticoesPalavras.keySet()) {
          System.out.println("\"" + palavra + "\" ⇒ repete " + repeticoesPalavras.get(palavra) + " vezes");
        }
//        contagemFrase = countSubstringOccurrences(conteudo, frase);
//        System.out.println("\"" + frase + "\" ⇒ repete " + contagemFrase + " vezes");
//
//        String[] words = frase.split("\\s+");
//        
//        Map<String, Integer> contagemPalavras = countWordOccurrences(conteudo,words);
//        for (String palavra : contagemPalavras.keySet()) {
//            System.out.println("\"" + palavra + "\" ⇒ repete " + contagemPalavras.get(palavra) + " vezes");
//        }

        ent.close();
    }
    private static String findMainContent(Document doc) {
        Element mainElement = doc.selectFirst("main");
        if (mainElement != null) {
            return mainElement.text();
        }
        return doc.body().text();
    }
    private static int countSubstringOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(substring, index)) != -1) {
            index += substring.length();
            count++;
        }
        return count;
    }
    private static Map<String, Integer> countWordOccurrences(String text, String[] words) {
        Map<String, Integer> wordOccurrences = new HashMap<>();
        for (String word : words) {
            int count = countSubstringOccurrences(text, word);
            wordOccurrences.put(word, count);
        }
        return wordOccurrences;
    }
    
    private static int contarRepeticoes(String conteudo, String frase) {
        int cont = 0;
        String conteudoTexto = extrairTexto(conteudo); // Obtém apenas o texto do conteúdo, ignorando as tags HTML
        int index = conteudoTexto.toLowerCase().indexOf(frase.toLowerCase());
        while (index != -1) {
            cont++;
            index = conteudoTexto.toLowerCase().indexOf(frase.toLowerCase(), index + 1);
        }
        return cont;
    }

    private static Map<String, Integer> contarRepeticoesPalavras(String frase, String conteudo) {
        Map<String, Integer> repeticoes = new HashMap<>();
        String conteudoTexto = extrairTexto(conteudo); // Obtém apenas o texto do conteúdo, ignorando as tags HTML
        String[] palavrasFrase = frase.split("\\s+"); // Divide a frase em palavras usando espaço como delimitador

        for (String palavra : palavrasFrase) {
            int contador = 0;
            int index = conteudoTexto.indexOf(palavra);
            while (index != -1) {
                contador++;
                index = conteudoTexto.indexOf(palavra, index + 1);
            }
            repeticoes.put(palavra, contador);
        }

        return repeticoes;
    }

    private static String extrairTexto(String conteudo) {
        // Expressão regular para extrair o texto da página ignorando as tags HTML
        Pattern pattern = Pattern.compile("<.*?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.replaceAll("");
    }
}
