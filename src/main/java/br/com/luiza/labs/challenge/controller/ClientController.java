package br.com.luiza.labs.challenge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping(value="/hello")
    public String helloworld(){
        return "Oi";
    }
}
