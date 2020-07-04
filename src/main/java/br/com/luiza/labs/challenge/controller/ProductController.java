package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@Api(value = "Product", description = "Operations pertaining to product")
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

    @ApiOperation(value = "Find all product pagined")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list products pagined", response = Product.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(defaultValue = "1") Integer page){
        List<Product> products = service.findPaginated(page);
        if(products.size() > 0){
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }
}
