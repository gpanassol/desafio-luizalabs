# Desafio LuizaLabs - Magalu

Projeto desenvolvido para o desafio LuizaLabs. Este desafio técnico tem como premissa a documentação [disponível aqui](https://gist.github.com/Bgouveia/9e043a3eba439489a35e70d1b5ea08ec).

## Monitoração

A monitoração esta disponível utilizando [Spring Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)

Os endpoints disponível para monitação são:
- [/api/actuator/health](http://localhost:8080/api/actuator/health)
- [/api/actuator/info](http://localhost:8080/api/actuator/info)

## Documentação

A documentação esta disponível utilizando [Swagger API](https://swagger.io/)

Para acesso:
- [/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

## Acesso

O controle de acesso esta disponível utilizando [Spring Secutiry](https://spring.io/projects/spring-security)

Para acesso utilize o json

```
{
    "username":"luizalabs",
    "password":"luizalabs"
}
```

Endpoint POST em [/api/authenticate](http://localhost:8080/api/authenticate) 

## Checkstyle

O controle de qualidade na estilização e organização do código está disponível utilizando [Checkstyle](https://checkstyle.org/index.html). A validação é feita durante build.

Para build do projeto

```
./gradlew clean build 
```

*Usado como base o checkstyle disponibilizado pela google [aqui](https://google.github.io/styleguide/javaguide.html).

## SpotBug

O controle de erros no código java (usado para diminir o máximo) esta disponível utilizando [SpotBugs](https://spotbugs.github.io/). A validação é feita durante build.

Para checkar:
```
./gradlew check 
```

Para build do projeto:
```
./gradlew clean build 
```
