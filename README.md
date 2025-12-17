# 🧬 Projeto VekRest - VekSecurity - Módulo 2.1

VekSecurity: Segurança da aplicação com login de usuários via Spring Boot com Docker e Maven. **Módulo 2.1 / Login**

> ATENÇÃO: VÁ ATÉ OS REPOSITÓRIOS DAS PARTES DO MÓDULO 2 E SIGA AS INSTRUÇÕES DE EXECUÇÃO DO README DE CADA APLICAÇÃO PARA RODAR A APLICAÇÃO COMPLETA!

## 🧩 PARTES DO MÓDULO 2
| Aplicação      | Descrição                                      | Link                                                                                   |
|----------------|------------------------------------------------|----------------------------------------------------------------------------------------|
| VekGateway     | Gateway (este projeto) - Centraliza o acesso às outras aplicações | [Repositório VekGateway](https://github.com/VekRest/vekrest-vekgateway-modulo2)        |
| VekClient      | Aplicação de CRUD de Pessoa                    | [Repositório VekClient Módulo 1](https://github.com/VekRest/vekrest-vekclient-modulo1) |
| VekSecurity    | Aplicação de Login e Segurança                 | Este Repositório                                                                       |

> Este projeto depende das outras duas aplicações (VekGateway e VekClient) para funcionar corretamente.

---

# 1.✨ Imagem Docker (DockerHub)

> A imagem desta aplicação é atualizada a cada nova tag ou pull request na [branch main](https://github.com/VekRest/vekrest-veksecurity-modulo2.1/tree/main)

> Link da imagem no DockerHub: [vek03/vekrest-veksecurity:latest](https://hub.docker.com/r/vek03/vekrest-veksecurity)

---

## 1.1 🧩 Containers necessários para rodar a aplicação:

| Container  | Imagem                               | Link                                                                                                                                            | 
|------------|--------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| MongoDB    | `mongo:latest`                       | https://hub.docker.com/_/mongo                                                                                                                  |
| OpenSearch | `opensearchproject/opensearch:2.4.0` | https://hub.docker.com/layers/opensearchproject/opensearch/2.4.0/images/sha256-c8681472b70d46e7de61fe770d288a972f84b3f122f3c74ca06ea525264b6fd5 |
| Graylog    | `graylog/graylog:5.1.5`              | https://hub.docker.com/layers/graylog/graylog/5.1.5/images/sha256-3b6967572e88731eacfa661e6d7ca41da3e259bc5eb041e58fb10e4deb823dcb              |
| VekGateway | `vek03/vekrest-vekgateway:latest`    | https://hub.docker.com/r/vek03/vekrest-vekgateway                                                                                               |
| VekClient  | `vek03/vekrest-vekclient:latest`     | https://hub.docker.com/r/vek03/vekrest-vekclient                                                                                                |

---

## 1.2 ⚙ Variáveis de ambiente necessárias para rodar o container:

| Variável           | Descrição                        | Exemplo                                                                                |
|--------------------|----------------------------------|----------------------------------------------------------------------------------------|
| `SERVER_PORT`      | Porta onde a aplicação irá rodar | `8081`                                                                                 |
| `SECRET_KEY`       | Chave do JWT                     | `vekrest!Afwedfuihosedwfbgri8uoef`                                                     |
| `MONGODB_URI`      | URI do MongoDB                   | `mongodb://mongodb:27017/vekrest?serverSelectionTimeoutMs=15000&connectTimeoutMS=15000` |
| `GRAYLOG_HOST`     | Endereço do Graylog              | `graylog`                                                                              |
| `GRAYLOG_PORT`     | Porta do Graylog                 | `12201`                                                                                |

---

## 1.3 🐳 Como rodar o container

1️⃣ Para baixar a imagem do Docker Hub:
```bash
docker pull vek03/vekrest-veksecurity:latest
```

2️⃣ Para rodar o container localmente:
```bash
docker run -d \
  --name veksecurity \
  -e SERVER_PORT=8081 \
  -e SECRET_KEY=vekrest!Afwedfuihosedwfbgri8uoef \
  -e MONGODB_URI=mongodb://mongodb:27017/vekrest?serverSelectionTimeoutMs=15000&connectTimeoutMS=15000 \
  -e GRAYLOG_HOST=graylog \
  -e GRAYLOG_PORT=12201 \
  -p 8081:8081 \
  vek03/vekrest-veksecurity:latest
```

3️⃣ Alternativamente, você pode adicionar o serviço no seu docker-compose.yml local, descomentando ou adicionando o seguinte trecho:
```bash
services:
  veksecurity:
    image: vek03/vekrest-veksecurity:latest
    hostname: veksecurity
    container_name: veksecurity
    ports:
      - "8081:8081"
    environment:
      SERVER_PORT: 8081
      SECRET_KEY: "vekrest!A$9zLq#2vNf@eR6tYpWmZcXbGdQh"
      MONGODB_URI: mongodb://mongodb:27017/vekrest?serverSelectionTimeoutMs=15000&connectTimeoutMS=15000
    depends_on:
      mongodb:
        condition: service_healthy
      opensearch:
        condition: service_healthy
      graylog:
        condition: service_started
    healthcheck:
      test: ["CMD-SHELL", "curl -f http://localhost:8081/vekrest/veksecurity/actuator/health || exit 1"]
      interval: 5s
      timeout: 15s
      retries: 10
      start_period: 30s
```

4️⃣ Depois de adicionar o serviço em docker-compose.yml, suba os containers:
```bash
docker-compose up -d
```

---

## 📦 Instalação e Configuração do Ambiente

### 1️⃣ Clone o projeto na sua máquina e baixe as dependências:
```bash
# Clonar
git clone https://github.com/VekRest/vekrest-veksecurity-modulo2.1.git

# Acesse a pasta do projeto
cd vekrest-veksecurity-modulo2.1
````

### 2️⃣ Suba os containers necessários e Rode o projeto na sua IDE de preferência (ou via comando Maven)
```bash
# Suba os containers necessários (MongoDB, Redis, OpenSearch, Graylog)
docker-compose up -d

# Rode o projeto via Maven
```

### 3️⃣ (Opcional) Alternativamente, se quiser rodar via container localmente:
```bash
# Dentro da pasta do projeto:
mvn clean package -DskipTests

# Agora faça deploy no Docker local:
docker build -t vekrest/veksecurity:latest .

# Descomente as últimas linhas do docker-compose.yml (relacionadas ao veksecurity) e rode:
docker-compose up -d
```

> Ou execute o script .bat (executar_tudo.bat) na pasta .commands para automatizar o processo.


> A API Gateway VekGateway fica disponível na porta 8081 do [Localhost](http://localhost:8081) ao rodar localmente via IDE.

### 4️⃣ (Opcional) Caso deseje, pode rodar o SonarQube localmente

```bash
# Após configurar o pom.xml com as informações do Sonar em Properties:
mvn clean install sonar:sonar -Dsonar.token={TOKEN_SONAR}
```

---

## 🧩 Tecnologias Utilizadas

- **Spring Boot** → Framework Back-End
- **Java** → Linguagem de programação
- **Maven** → Build
- **Docker** → Containers e virtualização
- **Docker Hub** → Repositório de imagens Docker
- **OpenSearch e Graylog** → Logs da Aplicação
- **Swagger** → Documentação da API
- **SonarQube** → Qualidade
- **Github Actions** → CI/CD automatizado
- **.bat** → Scripts para automatizar processos no Windows

---

## ✅ Qualidade (SonarQube)

> Este projeto tem qualidade analisada pelo SonarQube Cloud. Verifique nos badges!

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=veksecurity)

---

## 📦 Esteira CI/CD Automatizada com Github Actions

> A esteira CI/CD deste projeto é automatizada via Github Actions. A cada tag criada a esteira é disparada.

### Para executar a Esteira pelo trigger:
```bash
# Exemplo: Cria a tag
git tag <version>

# Envia a tag para o repositório remoto
git push origin <version>
```

[![VekSecurity CI/CD Workflow](https://github.com/VekRest/vekrest-veksecurity-modulo2.1/actions/workflows/main.yml/badge.svg)](https://github.com/VekRest/vekrest-veksecurity-modulo2.1/actions/workflows/main.yml)

---

## Postman Collection

> Link para download da coleção Postman utilizada nos testes da API: [Postman Collection VekRest](https://www.postman.com/aviation-pilot-88658184/workspace/my-workspace/folder/33703402-dad9baf5-9c1b-4010-a4c7-7ace385191fd?action=share&source=copy-link&creator=33703402&ctx=documentation)

> Alternativamente, você pode utilizar o Swagger UI para testar a API:
[Swagger UI VekRest VekSecurity Módulo 2.1](http://localhost:8081/vekrest/veksecurity/swagger-ui.html) (rodando localmente)

---