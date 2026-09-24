# 🔗 Create URL Shortener

Função **AWS Lambda** em Java que recebe uma URL, gera um código curto e salva o mapeamento no **Amazon S3**. Projeto desenvolvido durante o bootcamp da **Rocketseat**.

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-build-blue)
![AWS Lambda](https://img.shields.io/badge/AWS-Lambda-FF9900)
![License](https://img.shields.io/badge/license-GPL--3.0-green)

## 📌 Sobre o projeto

O `CreateUrlLambda` é a parte responsável por **criar** a URL encurtada em uma arquitetura serverless na AWS. A cada requisição ele:

1. Lê o corpo da requisição (`originalUrl` e `expirationTime`);
2. Gera um código curto de 8 caracteres a partir de um UUID;
3. Salva um arquivo JSON no bucket S3, com o nome `<codigo>.json`, contendo a URL original e o tempo de expiração;
4. Retorna o código gerado.

## 🛠️ Tecnologias

- **Java 17**
- **Maven** (fat jar com `maven-shade-plugin`)
- **AWS Lambda** (`aws-lambda-java-core`, `aws-lambda-java-log4j2`)
- **AWS SDK for Java v2** – Amazon S3
- **Jackson** – JSON
- **Lombok**

## 🏗️ Arquitetura

```
Cliente ──▶ API Gateway ──▶ Lambda (Main) ──▶ S3 (url-shortener-storage-lambda)
```

## 📥 Requisição

O handler espera um evento do API Gateway, em que o campo `body` é uma string JSON:

```json
{
  "originalUrl": "https://exemplo.com/uma/url/muito/longa",
  "expirationTime": "3600"
}
```

| Campo            | Tipo   | Descrição                                              |
|------------------|--------|--------------------------------------------------------|
| `originalUrl`    | string | URL que será encurtada                                 |
| `expirationTime` | string | Tempo de expiração em segundos (enviar como **string**) |

## 📤 Resposta

```json
{
  "code": "a1b2c3d4"
}
```

## 🗄️ Dado salvo no S3

Arquivo `a1b2c3d4.json` no bucket `url-shortener-storage-lambda`:

```json
{
  "originalUrl": "https://exemplo.com/uma/url/muito/longa",
  "expirationTime": 3600
}
```

## ✅ Pré-requisitos

- JDK 17+
- Maven 3.8+
- Conta AWS com permissão para criar Lambda e acessar S3
- Um bucket S3 (o nome está definido em `Main.java`; altere para o seu)

## 🚀 Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/Paulojoserc/createUrlShortner.git
cd createUrlShortner
```

### 2. Gerar o pacote

```bash
mvn clean package
```

O jar será gerado em `target/CreateUrlLambda-1.0-SNAPSHOT.jar`.

### 3. Deploy na AWS

1. No console da AWS, crie uma função Lambda com runtime **Java 17**.
2. Faça upload do `.jar` gerado.
3. Configure o **Handler** como:
   ```
   com.rocketseat.createUrlShortner.Main::handleRequest
   ```
4. Dê à role da Lambda a permissão `s3:PutObject` no bucket.
5. Configure o gatilho com o **API Gateway** (método `POST`).

## 🧪 Exemplo de teste

```bash
curl -X POST https://<seu-api-id>.execute-api.<regiao>.amazonaws.com/create \
  -H "Content-Type: application/json" \
  -d '{"originalUrl": "https://exemplo.com", "expirationTime": "3600"}'
```

## 📁 Estrutura do projeto

```
createUrlShortner/
├── src/main/java/com/rocketseat/createUrlShortner/
│   ├── Main.java      # handler da Lambda
│   └── UrlData.java   # modelo com URL original e expiração
├── pom.xml
├── LICENSE
└── README.md
```


## 📄 Licença

Distribuído sob a licença **GPL-3.0**. Veja o arquivo [LICENSE](LICENSE).

## 👤 Autor

**Paulo José** – [@Paulojoserc](https://github.com/Paulojoserc)
