package br.com.projetobackmavensearchwords.maven;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SearchWords {

    public static void main(String[] args) throws IOException {
        // Izabela Cecília
        String url = "";
        String frase = "";
        String conteudo = "";
        char opcao = 'n';
        int contagemFrase  = 0;
        Map<String, Integer> repeticoesPalavras;

        Scanner ent = new Scanner(System.in);
        
        System.out.println("Gostaria de pesquisar por todo texto visivel? ");
        opcao = ent.nextLine().toLowerCase().charAt(0);
        
        System.out.println("Digite a URL: ");
        url = ent.nextLine();

        System.out.println("Digite uma frase para pesquisa: ");
        frase = ent.nextLine();

        conteudo = findMainContent(url,opcao);
        
        contagemFrase = contarRepeticoes(conteudo, frase);
        System.out.println("\"" + frase + "\" ⇒ repete " + contagemFrase + " vezes");
        
        repeticoesPalavras = contarRepeticoesPalavras(frase,conteudo);
        for(String palavra:repeticoesPalavras.keySet()) {
          System.out.println("\"" + palavra + "\" ⇒ repete " + repeticoesPalavras.get(palavra) + " vezes");
        }
        ent.close();
    }
     static String findMainContent(String url, char opcao) throws IOException {
        Document document = Jsoup.connect(url).get();
        if(opcao == 'n') {
        	Element mainElement = document.selectFirst("main");
            if (mainElement != null) {
                return mainElement.text();
            }
            return document.body().text();
        }
        return document.text();
    }
     static int contarRepeticoes(String conteudo, String frase) {
    	    int cont = 0;
    	    int index = conteudo.indexOf(frase);
    	    while (index != -1) {
    	        cont++;
    	        index = conteudo.indexOf(frase, index + 1);
    	    }
    	    return cont;
    	}
     
     static Map<String, Integer> contarRepeticoesPalavras(String frase, String conteudo) {
    	    Map<String, Integer> repeticoes = new HashMap<>();
    	    String[] palavrasFrase = frase.split("\\W+");
    	    String[] palavrasConteudo = conteudo.split("\\W+");

    	    for (String palavraFrase : palavrasFrase) {
    	        int contador = 0;
    	        for (String palavraConteudo : palavrasConteudo) {
    	            if (palavraConteudo.equals(palavraFrase)) {
    	                contador++;
    	            }
    	        }
    	        repeticoes.put(palavraFrase, contador);
    	    }

    	    return repeticoes;
    	}
}
