# Duck Farm

Sistema com fluxo de Vendas de patos, sugerido como teste técnico pelo Preço Justo

# 🗃 Baixando o repositório
Para baixar e executar o sistema, faça download com os seguintes comandos:

``` bash
    git clone -b main https://github.com/yLuiz/duckfarm-java.git
    cd ./duckfarm-java
```

# 📡 Back-end

# 💻 Tecnologias
* Java (v17.0)
* Apache Maven (v3.9.9)
* Spring Boot (v3.0.0)
* PostgreSQL (v15.3)
* Flyway

## ⚙ Configuração
Para conseguir rodar o Backe-end, você precisa ter instalado o Java (jdk) na versão 17.0.11+ e o Apache Maven 3.9.9+ em sua máquina.
Após isso, clone o repositório e execute os seguintes passos:

# Banco de dados 🎲

- Caso tenha você utilize o docker, siga os seguintes passos:

- Entre no arquivo docker-compose-db.yml e altere os campos ``` POSTGRES_USER: your_user ``` e ```POSTGRES_PASSWORD: your_password ``` para as suas credências de preferência.
- Após isso, você precisa ter o banco de dados PostgreSQL, execute o seguinte comando:
``` bash
    $ docker-compose -f docker-compose-db.yml up -d
```

- Em seguida, verifique o arquivo application.properties da sua aplicação que se encontra na pasta resources e certifique de alterar os URLs e credencias de conexão do Banco de dados:
``` bash
    spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/your_database?createDatabaseIfNotExists=true}
    spring.datasource.username=${DB_USERNAME:your_user}
    spring.datasource.password=${DB_PASSWORD:your_password}
```

- Se você quiser configurar variáveis de ambiente na sua IDE, fique a vontade, as váriaveis são:
``` bash
    DB_URL=jdbc:postgresql://localhost:5432/your_database
    DB_USERNAME=your_user
    DB_PASSWORD=your_password
```

*Obs: Caso não tenha o docker, certifique de baixar e fazer a instalação do PostgreSQL 13.

- Agora, vale lembrar que para seguir os próximos passos você já deve ter instalado o Apache Maven e o Java (jdk).

- Ao entrar no diretório da aplicação, execute o seguinte comando caso sua IDE não instale automaticamente os pacotes necessários.

``` bash
    $ mvn clean
    $ mvn package
```

## 🚀 Executando (Sem o Docker)
Após a configurção, rode a aplicação pela sua IDE. 
Mas caso queira rodar o build da aplicação, execute o seguinte comando:
``` bash
$ java -jar duckfarm-0.0.1-SNAPSHOT.jar
```

## 🚀 Executando (Com o Docker)
Caso não queira ter problemas com ambiente, rode os seguintes comando docker:

``` bash
$ docker-compose build
$ docker-compose up -d
```

## 🔀 Rotas
Para saber quais as rotas existentes, acesse: `http://localhost:8085/swagger-ui.html`. (IMPORTANTE! sem barra no final)
# Considerações
--
# Observações

* Neste projeto, utilizei no backend um Design Pattern conhecido como IoC (Inversion of Controll) e para sua implementação utilizei a técnica conhecida como Dependency Injection, que representa a letra D do conhecido SOLID.
* Caso haja alguma coisa que eu possa melhorar no sistema, estarei totalmento aberto a criticas. Afinal, assim posso melhorar minhas habilidades no desenvolvimento.
* Qualquer dúvida em relação ao teste, entre em contato via telefone (caso tenha) ou email: luizvictor1231@gmail.com


