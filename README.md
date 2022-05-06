#**Projeto Integrador - Bootcamp Mercado Livre**

#**MERCADO LIVRE - FRESCOS**

##Tecnologias utilizadas 
- Java 
- SpringBoot 
- Maven 
- Docker 
- MySQL 
- Junit 
- Jenkins 
- Pipeline 
- Sonarqube 
- Jacoco 
- Postman 
- Redis 

***

# **Documentação da API**
[Swagger Documentation API](http://localhost:8080/swagger-ui.html#/)

***

##Tópicos  
##Descrição: 
- API de produtos frescos realizando toda logística de disponibilidade de compra e venda

##Funcionalidade: 
- Comprar produtos adicionando a um carrinho e também vender

##Deploy da Aplicação: 
- Push do branch master que se faz no repositório Git ou deploy automatizado é o Jenkins.

***

##Como rodar a aplicação: 
- docker-compose --file docker-compose.dev.yml up

***

##Descrição do projeto  
Construção de API para realizar a logística de produtos alimentícios em estado de congelados, refrigerados, fresco para serem  armazenados em seus determinados setores com organização de volumes e sua respectiva venda em carrinho com suas ordens  logística

***

##Funcionalidades
- Representante do armazém de distribuição, inseri um lote de 
- produtos no armazém de distribuição para registrar a existência de estoque
- Comprador ativo adiciona produtos ao carrinho de compras do marketplace para compra-los, se desejar
- Representante pode consultar um produto em stock no armazém para saber a sua localização num setor e os diferentes lotes onde se  encontra
- Representante pode consultar um produto em todos os armazéns para saber o estoque em cada armazém do referido produto
- Representante pode consultar os produtos em estoque que estão prestes a expirar no almoxarifado, afim de aplicar alguma ação comercial  com eles

***

##Layout ou Deploy da Aplicação

- [EndPoint Requisito 1](http://localhost:8080/api/v1/fresh-products/inboundorder/)

- [EndPoint Requisito 1](http://localhost:8080/api/v1/fresh-products/inboundorder/)

***

##EndPoint REQ2: 
- [EndPoint Requisito 2](http://localhost:8080/api/v1/fresh-products/)

- [EndPoint Requisito 2](http://localhost:8080/api/v1/fresh-products/list?querytype=[categoríaproducto])

- [EndPoint Requisito 2](http://localhost:8080/api/v1/fresh-products/orders/)

- [EndPoint Requisito 2](http://localhost:8080/api/v1/fresh-products/orders/querytype=[idOrder])

- [EndPoint Requisito 2](http://localhost:8080/api/v1/fresh-products/orders/queryparam=[idOrder])

***

##EndPoint REQ3: 
- [EndPoint Requisito 3](http://localhost:8080/api/v1/fresh-products/list?querytype=[idProducto])

- [EndPoint Requisito 3](http://localhost:8080/api/v1/fresh-products/list?querytype=[idProducto]querytype=[L])

***

##EndPoint REQ4: 
- [EndPoint Requisito 4](http://localhost:8080/api/v1/fresh-products/warehouse/querytype=idproduct])

***

##EndPoint REQ5: 
- [EndPoint Requisito 5](http://localhost:8080/api/v1/fresh-products/duedate/queryparam=[numberofdays]queryparam=[section])

- [EndPoint Requisito 5](http://localhost:8080/api/v1/fresh-products/duedate/list?queryparam=[numberofdays]queryparam=[category] queryparam=[asc])

***
#REQUISITO 6 - Micaela
Esta feature propõe que o usuário possa cadastrar um novo armazém de distribuição e um novo 
responsável como também consultar a lista dos existentes. Desta forma será possível fazer a 
gestão do negócio e a sua expansão.
##EndPoint REQ6:
- [EndPoint Requisito 6 - GET / Warehouses](http://localhost:8080/api/v1/fresh-products/admin/warehouse)

- [EndPoint Requisito 6 - POST / Warehouse](http://localhost:8080/api/v1/fresh-products/admin/warehouse)

- [EndPoint Requisito 6 - GET / Agents](http://localhost:8080/api/v1/fresh-products/admin/agent)

- [EndPoint Requisito 6 - POST / Agent](http://localhost:8080/api/v1/fresh-products/admin/agent)

***

##Pré-requisitos: 

- Instalação do Java 11 JDK ou versão mais atualizada na maquina


- Utilização de uma IDE para start do serviço


- Instalação do Docker desktop


- Instalação do Postman para manuseio dos endpoint

***

#**Como rodar a aplicação:**

##No terminal, clone o projeto; git clone https ou ssh do projeto: 
- git clone https://github.com/maik-henrique/DH-Projeto-Integrador.git 
#Acesse a pasta do projeto via terminal, para iniciar o projeto: 
- mvn clean install 

##Em seguida execute o servidor Tomcat 
- mvn spring-boot:run  

##Inicie os conteiners referente a aplicacao com: 
- docker-compose docker-compose --file docker-compose.dev.yml up 

Inicialize a aplicacao springboot na IDE e acessse os endpoints  utilizando Postman

***

##Como rodar os testes:
##Para iniciar os testes em linha de comando utilizar: 
`mvn tests`

##Para iniciar os testes no SonarQube: 
`mvn sonar:sonar -Dsonar.projectKey={KEY DO PROJETO CRIADO NO SONAR} - Dsonar.host.url=http://127.0.0.1:9000 -Dsonar.login={CHAVE DE  AUTENTICACAO DO SONAR}`

***

#**Diagrama - # DER:**
[Diagrama Entidade e Relacionamento](https://whimsical.com/4VfH3PMQLRV3u33RUJniwY)

***

##Classes de teste referente ao Requisito 6: 
- Controller/BuyerController.java 
- Service/BuyerService.java 
- integration/BuyerControllerTest.java
- unit/BuyerServiceTest.java dto/response/BuyerDTO.java

***

##Iniciando/Configurando banco de dados
##Para iniciar o servico de banco de dados no docker: 
`docker-compose --file docker-compose.dev.yml up`


***

##Linguagens, dependencias e libs utilizadas: 
- Java 
- SpringBoot 
- Maven 
- Docker 
- MySQL 
- Junit 
- Jenkins 
- Pipeline 
- Sonarqube 
- Jacoco 
- Postman 
- Redis 

***

##Tarefas em aberto: 

- Implementar microservices


- Expandir logica e regra de negocio


- Subir na cloud para implementar observability

***

##Desenvolvedores:

@Bruno Mendes

@David Alexandre Fernandes

@Maik Henrique dos Santos Pereira

@Mariana Galdino Vieira

@Matheus dos Anjos Guerra

@Micaela da Cruz Alves

@Pedro Henrique Dalpa



> 