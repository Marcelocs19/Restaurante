# Restaurante [![Build Status](https://travis-ci.org/Marcelocs19/Restaurante.svg?branch=master)](https://travis-ci.org/Marcelocs19/Restaurante)

### Enunciado 
Os times da DBServer enfrentam um grande problema, como eles são muito democráticos, todos os dias gastam 30 minutos decidindo onde irão almoçar. Vamos fazer um pequeno sistema que auxilie essa tomada de decisão!

### Requisitos
Para compilar e rodar está aplicação você precisa:
* [Lombok](https://projectlombok.org/download)

* [MySQL](https://dev.mysql.com/downloads/installer/)

* [Java SE 12](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase12-5440181.html)

* [Maven](https://maven.apache.org/download.cgi)

### Clonar o projeto
Você pode clonar este repositório, pelo git usando:
```
https://github.com/Marcelocs19/Restaurante.git
```
### MySQL
```
1. create database restaurante;
```
```
2. create user 'user'@'%' identified by '123456';
```
```
3. grant all on restaurante.* to 'user'@'%';
```
```
4. use restaurante;
```

### Rodar a aplicação localmente
Existem varias jeitos para rodar uma aplicação Spring Boot no seu computador. Um jeito é executar a classe main RestauranteApplication pela sua IDE.

Ou você pode rodar a aplicação pela linha de comando usando:

```
mvn spring-boot:run
```

### Rotas
1. Método que lista todos os restaurantes com os votos:
Get - http://localhost:8080/restaurantes


2. Método para votar em um restaurante:
Post - http://localhost:8080/restaurantes/votar/{id}
```
Postman
Substituir o {id} pelo id do restaurante que você deseja votar.
Adicionar no Body, um json com o nome e email do funcionário.
Exemplo:
http://localhost:8080/restaurantes/votar/11
Exemplo json:
{
	"nome":"João",
	"email":"joao@email.com"
}
```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
