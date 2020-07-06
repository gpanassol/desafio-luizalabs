package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.entity.ProductClient;
import br.com.luiza.labs.challenge.service.impl.ProductClientImpl;
import br.com.luiza.labs.challenge.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@Api(value = "Product", description = "Operations pertaining to product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductClientImpl productClientService;

    @ApiOperation(value = "Find a product registration by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a product registration", response = Product.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable Integer id) {
        return service.findProductById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    }

    @ApiOperation(value = "Find a list pagined of product registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list pagined of product registration. Page default 0 (zero) ", response = Product.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(defaultValue = "0") Integer page) {
        List<Product> products = service.findPaginated(page);
        if (products.size() > 0) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Find a list products registration by client id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list products registration", response = Product.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity findAllProductByIdClient(@PathVariable Integer id) {
        List<ProductClient> products = productClientService.findAllProductByIdClient(id);
        if (products.size() > 0) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }

}
