# 🧬 Projeto VekRest - VekSecurity - Módulo 2.1

Login VekSecurity: Segurnça da aplicação com criação e login de usuários via Spring Boot com Docker e Maven. **Módulo 2.1 / Login**

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

> Link da imagem no DockerHub: [vek03/vekrest-veksecurity:latest](https://hub.docker.com/repository/docker/vek03/vekrest-veksecurity)

---

## 1.1 🧩 Containers necessários para rodar a aplicação:

| Container  | Imagem                               | Link                                                                                                                                            | 
|------------|--------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| MongoDB    | `mongo:latest`                       | https://hub.docker.com/_/mongo                                                                                                                  |
| OpenSearch | `opensearchproject/opensearch:2.4.0` | https://hub.docker.com/layers/opensearchproject/opensearch/2.4.0/images/sha256-c8681472b70d46e7de61fe770d288a972f84b3f122f3c74ca06ea525264b6fd5 |
| Graylog    | `graylog/graylog:5.1.5`              | https://hub.docker.com/layers/graylog/graylog/5.1.5/images/sha256-3b6967572e88731eacfa661e6d7ca41da3e259bc5eb041e58fb10e4deb823dcb              |
| VekGateway | `vek03/vekrest-vekgateway:latest`    | https://hub.docker.com/repository/docker/vek03/vekrest-vekgateway                                                                               |
| VekClient  | `vek03/vekrest-vekclient:latest`     | https://hub.docker.com/repository/docker/vek03/vekrest-vekclient                                                                                |

---

## 1.2 ⚙ Variáveis de ambiente necessárias para rodar o container:

| Variável       | Descrição                        | Exemplo                                                                                 |
|----------------|----------------------------------|-----------------------------------------------------------------------------------------|
| `SERVER_PORT`  | Porta onde a aplicação irá rodar | `8081`                                                                                  |
| `SECRET_KEY`   | Chave do JWT                     | `vekrest!Afwedfuihosedwfbgri8uoef`                                                      |
| `MONGODB_URI`  | URI do MongoDB                   | `mongodb://mongodb:27017/vekrest?serverSelectionTimeoutMs=15000&connectTimeoutMS=15000` |

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
```

4️⃣ Depois de adicionar o serviço em docker-compose.yml, suba os containers:
```bash
docker-compose up -d
```

---

## 📘 Estrutura do Projeto

```

📂 vekrest-veksecurity-modulo2.1/
├── 📁 .commands                            ← Pasta de comandos .bat para automatizar na execução/build
├── 📁 .github                              ← Pasta de configuração da esteira CI/CD do Github Actions
├── 📁 .run                                 ← Pasta de configurações da IDE para facilitar execução local
├── 📁 domain                               ← Módulo de domínio, construído unicamente com o Java, sem dependências do Spring
    ├── 📁 [...]/java                       ← Pasta princípal do projeto (Domínio)
            ├── 📁 entity/                  ← Entidades próprias do domínio
            ├── 📁 exception/               ← Exceções customizadas
            ├── 📁 repository/              ← Interface da Lógica de persistência de dados
    ├── 📄 pom.xml                          ← Arquivo de Build do Maven
├── 📁 spring                               ← Módulo do spring (aplicação), construído com dependências do Spring
    ├── 📁 [...]/java                       ← Pasta princípal do projeto (App)
            ├── 📁 configuration/           ← Arquivos de Injeção de Dependência (@Bean)
            ├── 📁 controller/              ← Controllers Rest HTTP
            ├── 📁 repository/              ← Implementação da Lógica de persistência de dados
            ├── 📁 security/                ← Configurações de Segurança (JWT, Filters, etc)
            📄 VeksecurityApplication.java  ← Classe principal do Spring Boot
    ├── 📁 [...]/resources                  ← Variáveis de ambiente
    ├── 📄 pom.xml                          ← Arquivo de Build do Maven
├── 📄 docker-compose.yml                   ← Configuração dos containers utilizados
├── 📄 Dockerfile                           ← Configuração para build e deploy no Docker
├── 📄 LICENCE.txt                          ← Arquivo de Licença GPL-3.0
├── 📄 pom.xml                              ← Arquivo de Build do Maven
├── 📄 README.md                            ← Este arquivo de documentação

````

---

## ⚙️ Objetivo

Módulo 2
Crie uma API REST de Login com controle de acesso por usuário e senha.
Requisitos:

Ao enviar um usuário e senha válidos, o sistema deve retornar, através do endpoint /login, um token de autenticação (Bearer Token).

Crie um API Gateway e garanta que sua aplicação de Login só possa ser acessada através de uma rota no Gateway.

O container da aplicação de Login não deve expor sua porta diretamente (configure o Docker adequadamente).

Inclua o Dockerfile necessário para a construção da aplicação.

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

## 📌 Status do Projeto
> 🚀 Release [v1.0.0](https://github.com/VekRest/vekrest-veksecurity-modulo2.1/tree/v1.0.0) - Primeira versão

[//]: # (- 🚧 Em desenvolvimento – Release v2.0-iot-alpha)

---

## 📜 Licença
> Este projeto é distribuído sob a licença GPL-3.0. Consulte o arquivo [LICENCE](LICENSE.txt)
para mais detalhes.

---

## ✨ Deploy (DockerHub)

> A imagem desta aplicação é atualizada a cada atualização na [branch main](https://github.com/VekRest/vekrest-veksecurity-modulo2.1/tree/main)

> Link da imagem no DockerHub: [vek03/vekrest-veksecurity:latest](https://hub.docker.com/repository/docker/vek03/vekrest-veksecurity)

---

## ✅ Qualidade (SonarQube)

> Este projeto tem qualidade analisada pelo SonarQube Cloud. Verifique nos badges!

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=veksecurity&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=alert_status&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=bugs&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=vulnerabilities&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=code_smells&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=coverage&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=duplicated_lines_density&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=ncloc&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=reliability_rating&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=security_rating&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=sqale_index&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=sqale_rating&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=veksecurity&metric=vulnerabilities&token=077fa4f33ba1777c431bdb47d785e6d9e3f64fa8)](https://sonarcloud.io/summary/new_code?id=veksecurity)

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

# Agora abra o projeto na sua IDE (IntelliJ, Eclipse, VSCode, etc) e rode a aplicação Spring Boot
# Ou, se preferir, rode via terminal com properties-local:
mvn spring-boot:run -pl spring -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=local"
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

## 📦 Esteira CI/CD Automatizada com Github Actions

> A esteira CI/CD deste projeto é automatizada via Github Actions. A cada tag criada ou execução manual na branch main, a esteira é disparada.

###  Steps da esteira:

1️⃣ Verificação de **Vulnerabilidades** com o **Trivy** (Security)

2️⃣ Análise do **Sonar Cloud** (Quality)

3️⃣ Deploy da imagem do container no **DockerHub e Github Packages** (Deploy)

4️⃣ Deploy do Maven Artifact no **Github Packages** (Deploy)

5️⃣ Deploy da Release no **Github** (Release)

### Para executar a Esteira pelo trigger:
```bash
# Exemplo: Cria a tag
git tag <version>

# Envia a tag para o repositório remoto
git push origin <version>
```

[![VekGateway CI/CD Workflow](https://github.com/VekRest/vekrest-veksecurity-modulo2/actions/workflows/main.yml/badge.svg)](https://github.com/VekRest/vekrest-veksecurity-modulo2/actions/workflows/main.yml)

---

## 💡 Observações Importantes

* Este projeto cumpre com o **Módulo 2 da Atividade**
* Para este módulo, existem duas aplicações: **esta aplicação** e a aplicação de [Gateway](https://github.com/VekRest/vekrest-vekgateway-modulo2)

---

## Postman Collection

> Link para download da coleção Postman utilizada nos testes da API: [Postman Collection VekRest](https://web.postman.co/workspace/My-Workspace~e702bcc2-18e9-41e7-86d7-21df963c99df/folder/33703402-f59218e7-8804-436c-8866-2693c75b9eb6?action=share&source=copy-link&creator=33703402&ctx=documentation)

> Alternativamente, você pode utilizar o Swagger UI para testar a API:
[Swagger UI VekRest VekSecurity Módulo 2.1](http://localhost:8081/vekrest/veksecurity/swagger-ui.html) (rodando localmente)

---

## ✍️ Autor

<div align="center">

| [<img src="https://avatars.githubusercontent.com/u/98980071" width=115><br><sub>Victor Cardoso</sub>](https://github.com/vek03)
| :---: |

</div>

---
