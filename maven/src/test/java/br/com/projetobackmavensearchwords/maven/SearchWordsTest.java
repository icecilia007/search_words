package br.com.projetobackmavensearchwords.maven;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.text.Document;

public class SearchWordsTest {
	
	private static final String MOCK_URL = "https://es.wikipedia.org/wiki/Pir%C3%A1mides_de_Egipto";
	private static final String MOCK_FRASE = "bloques de piedra";
	private static final String MOCK_CONTENT = "los bloques de piedra se fabricaban en egipto, una vez fabricados se transportaban para construir las piramides con bloque de piedras";
	private static String conteudo;
	private static char opt;
	
	@BeforeAll
	static void setUp() throws IOException {
		Scanner ent = new Scanner(System.in);
        System.out.println("test: Gostaria de pesquisar por todo texto visivel? ");
        opt = ent.nextLine().toLowerCase().charAt(0);
        
		conteudo = SearchWords.findMainContent(MOCK_URL,opt);
		
		ent.close();
	}
	
	@Test
    public void testContarRepeticoesFraseMock() throws IOException {
        int expectedRepeticoes = 1;
        int actualRepeticoes = SearchWords.contarRepeticoes(MOCK_CONTENT, MOCK_FRASE);
        assertEquals(expectedRepeticoes, actualRepeticoes);
    }
	@Test
	public void testContarRepeticoesPalavrasMock() throws IOException {
        Map<String, Integer> repeticoesPalavras = SearchWords.contarRepeticoesPalavras(MOCK_FRASE, MOCK_CONTENT);
        assertEquals(1, repeticoesPalavras.get("bloques").intValue());
        assertEquals(2, repeticoesPalavras.get("de").intValue());
        assertEquals(1, repeticoesPalavras.get("piedra").intValue());
	}
	
	
	@Test
    public void testContarRepeticoesFrase() throws IOException {
        int expectedRepeticoes = 2;
        int actualRepeticoes = SearchWords.contarRepeticoes(conteudo, MOCK_FRASE);
        assertEquals(expectedRepeticoes, actualRepeticoes);
    }
	@Test
	public void testContarRepeticoesPalavras() throws IOException {
		int expectedRepBloques = 7;
		int expectedRepDe = 187;
		int expectedRepPiedra = 4;
		
        Map<String, Integer> repeticoesPalavras = SearchWords.contarRepeticoesPalavras(MOCK_FRASE, conteudo);
        
        if(opt == 'n') {
        	expectedRepBloques = 7;
    		expectedRepDe = 169;
    		expectedRepPiedra = 4;	
        }
        assertEquals(expectedRepBloques, repeticoesPalavras.get("bloques").intValue());
        assertEquals(expectedRepDe, repeticoesPalavras.get("de").intValue());
        assertEquals(expectedRepPiedra, repeticoesPalavras.get("piedra").intValue());
	}
}
