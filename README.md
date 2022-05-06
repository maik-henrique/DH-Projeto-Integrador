#**Projeto Integrador - Bootcamp Mercado Livre **

#**MERCADO LIVRE - FRESCOS **

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


***

##Tópicos  
##Descrição: 
- API de produtos frescos realizando toda logística de disponibilidade de compra e venda

##Funcionalidade: 
- Adicionar e remover produtos dos favoritos
- Filtrar produtos por nome
- Filtrar produtos por marca
- Filtrar produtos faixa de preço e ordenar por preço crescente ou decrescente


#**Como rodar a aplicação:**

##No terminal, clone o projeto; git clone https ou ssh do projeto: 
- git clone https://github.com/maik-henrique/DH-Projeto-Integrador.git 
#Acesse a pasta do projeto via terminal, para iniciar o projeto: 
- mvn clean install 

##Em seguida execute o servidor Tomcat 
- mvn spring-boot:run  

##Inicie os conteiners referente a aplicacao com: 
- docker-compose docker-compose --file docker-compose.dev.yml up 
***

##Descrição do projeto  
Construção de API para realizar a logística de produtos alimentícios em estado de congelados, refrigerados, fresco para serem  armazenados em seus determinados setores com organização de volumes e sua respectiva venda em carrinho com suas ordens  logística


##EndPoint REQ6: 
- [EndPoint Requisito 6](http://localhost:8080 /api/v1/fresh-products/list/price?price=ASC&minValue=15&maxValue=15)

- [EndPoint Requisito 6](http://localhost:8080 /api/v1/fresh-products/list/name?querytype= [nome do produto])

- [EndPoint Requisito 6](http://localhost:8080 /api/v1/fresh-products/list/brand?querytype= [marca do produto])

- [EndPoint Requisito 6](http://localhost:8080 /api/v1/fresh-products /buyer/favorite) 
- ### body:
 ``` 
 {
   "productId": id valido de produto,
   "buyerId": id valido de buyer
 }  
 ```
- [EndPoint Requisito 6](http://localhost:8080 /api/v1/fresh-products /buyer/favorite/{buyerId}?querytype= [productId])

