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

A Aplicação esta disponível utilizando [Spring Secutiry](https://spring.io/projects/spring-security)

Para acesso utilize o json

```
{
    "username":"luizalabs",
    "password":"luizalabs"
}
```

Endpoint POST em [/api/authenticate](http://localhost:8080/api/authenticate) 