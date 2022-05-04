# Requisito 6 - Sistema de avaliação de compras para compradoes

## _User story_

**COMO** Comprador **QUERO** poder avaliar minhas compras **PARA** compartilhar minha experiência de compra com outros usuários da plataforma

- Uma descrição mais detalhada da _user story_ pode ser encontrada em 'docs/Requisito 6 - Maik.pdf'
- Uma coleção _postman_ foi fornecida na pasta _postman-collection_, porém vale salientar que a coleção assume que itens pré-existam no banco de dados, como um pedido, produto, etc.

## Descrição 

A ideia é possibilitar que, após um pedido seja finalizado, o comprador tenha a possibilidade de avaliar a sua experiência e compartilhar com a comunidade. De modo que também possa visualizar antes de a realização de uma compra a listagem das avaliações de um determinado produto.

A ideia de implementação passa pela adição da tabela _purchaseOrderEvaluation_ que é componente central das avaliações e que mantém referência para a compra e produto alvos, permitindo assim que os itens de uma mesma compra sejam avaliados de forma individual.


## Diagrama de lógico entidade-relacionamento

![untitled](https://user-images.githubusercontent.com/101212122/166825764-0cfe39a7-56c9-4e1c-a371-cd9f3302ded0.png)
