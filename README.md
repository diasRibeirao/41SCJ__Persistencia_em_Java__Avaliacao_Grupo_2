# Persistência em Java (JDBC e JPA)  - 41SCJ

Projeto elaborado para a avaliacao final de Persistência em Java (JDBC e JPA) ministrada pelo Prof. MSc. Rafael Matsuyama

# Grupo 2 
**Emerson - 341060  
Marco Antonio - 341785**  
**Caio - 341245**  
**Alan Coimbra - 341622**


# Opção Escolhida
a. Spring Data JPA + Cache Redis (os dois em um único projeto).
# Stack Tecnológica

Para esse projeto escolhemos utilizar java 11 por ser a ultima versao LTS e já possuir os ultimos recursos da linguagem. 
Para o spring foi escolhida a versao 2.5.4 devido a familiaridade dos integrantes do grupo com os recursos providos. 
Foi utilizado h2 durante o desevolvimento, evitando assim o trabalho de instalar e subir um banco em tempo de desenvolvimento e para o deploy final foi utilizado MySQL. 
Nesse projeto foi utilizado o redis para armazenamento do cache. 
O design pattern escolhido para a transferencia de dados foi o DTO para tentar minimizar ao maximo o trafego de rede e a comunicacao do cliente com o banco de dados.
Utilizamos tambem os recursos padrao da JPA e suas implementacoes providas pelo spring. 
Utilizamos em trechos do código o Lombok para diminuir a quantidade de codigo boiler plate na aplicacao. 
Decidimos pela criacao de API REST devido a facilidade de testes usando o swagger e a rapida implementacao em qualquer sistema de front. 
Foi utilizado JUNIT para automatizacao dos testes 