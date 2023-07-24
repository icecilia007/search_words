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
