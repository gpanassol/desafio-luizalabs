package br.com.luiza.labs.challenge.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client")
@Api(value = "Client", description = "Operations pertaining to client")
public class ClientController {

    @ApiOperation(value = "Hello World!")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloworld() {
        return "Oi";
    }
}
