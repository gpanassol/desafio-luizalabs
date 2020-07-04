package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/product")
@Api(value = "product", description = "Operations pertaining to Product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @ApiOperation(value = "Find product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a register product", response = Product.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable Integer id) {
        return service.findProductById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    }
}
