#  Estrutura do projeto

# Imagem Docker do SearchWords

Esta é uma imagem Docker para a aplicação SearchWords.

## Como Usar

Para usar esta imagem, você pode baixá-la do Docker Hub usando o seguinte comando:

```bash
docker pull icecilia007/searchwords:v1
```
Assim que tiver a imagem, você pode executá-la em um container Docker:

```bash
docker run -it --name searchwords-container icecilia007/searchwords:v1
```
## Configuração
Esta imagem não requer nenhuma configuração específica. No entanto, você pode personalizar o comportamento da aplicação SearchWords usando variáveis de ambiente.

## Portas Expostas
A aplicação SearchWords escuta na porta 8080. Você pode mapear esta porta para o sistema host ao executar o container.

## Persistência de Dados
A aplicação não gera nenhum dado que exija persistência.

## Pré requisito 
Ter o Kubernetes instalado no ambiente.

## Instalação

Atualize o código main para: 
```bash
public static void main(String[] args) throws IOException {
        // Izabela Cecília
        String url = "";
        String frase = "";
        String conteudo = "";
        char opcao = 'n';
        int contagemFrase  = 0;
        Map<String, Integer> repeticoesPalavras;

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
             opcao = br.readLine().charAt(0);
        	 url = br.readLine();
             frase = br.readLine();  
             
             System.out.println("Opção: " + opcao);
             
             System.out.println("URL: "+url);

             System.out.println("Frase: "+frase);
            
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        	return;
        }   
        

        conteudo = findMainContent(url,opcao);
        
        contagemFrase = contarRepeticoes(conteudo, frase);
        System.out.println("\"" + frase + "\" ⇒ repete " + contagemFrase + " vezes");
        
        repeticoesPalavras = contarRepeticoesPalavras(frase,conteudo);
        for(String palavra:repeticoesPalavras.keySet()) {
          System.out.println("\"" + palavra + "\" ⇒ repete " + repeticoesPalavras.get(palavra) + " vezes");
        }
    }
```

Atualize o *Dockerfile* para: 
```bash
# Use a imagem Java 8 como base
FROM openjdk:8-jdk-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo jar do projeto para o diretório de trabalho do contêiner
COPY target/maven-0.0.1-SNAPSHOT.jar /app/maven-0.0.1-SNAPSHOT.jar

# Executa o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "maven-0.0.1-SNAPSHOT.jar"]

# Copia o arquivo input.txt do diretório atual do host para o diretório /app dentro do contêiner
COPY input.txt /app/input.txt
```

1. Abra o terminal na raiz do projeto
- Exucute mvn package
- Build da imagem Docker.
  - docker build -t icecilia007/searchwords:v2 .
  - docker pull icecilia007/searchwords:v2
  - docker push icecilia007/searchwords:v2

## Executando no Kubernetes
1. Aplique o arquivo de manifesto do deployment e serviço para o Kubernetes.
```bash
kubectl apply -f deployment.yaml
```
Pronto a imagem foi rodada, para verificar basta
1. Verificar o pods
```bash
kubectl get pods
kubectl get services
```
Como a imagem tem apenas uma execução o log será algo como:
NAME                                      READY   STATUS    RESTARTS   AGE
searchwords-deployment-v2xxxxx            0/1     Completed     0      xh
2. Verificar o log
```bash
 kubectl logs searchwords-deployment-v2xxxxx
```
No log pode ter algo no gênero
```bash
Opção: s
URL: https://es.wikipedia.org/wiki/Pir%C3%A1mides_de_Egipto
Frase: bloques de piedra
"bloques de piedra" ⇒ repete 2 vezes
"de" ⇒ repete 187 vezes
"piedra" ⇒ repete 4 vezes
"bloques" ⇒ repete 7 vezes
```


