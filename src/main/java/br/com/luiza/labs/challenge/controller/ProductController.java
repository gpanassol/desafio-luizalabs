package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@Api(value = "Product", description = "Operations pertaining to product")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

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
            @ApiResponse(code = 200, message = "Return a list pagined of product registration. Page default 0 (zero) ", response = Product.class, responseContainer = "List"),
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

    @ApiOperation(value = "Create a product registration")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Created", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request. Failed to save product."),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody @Validated Product product) {
        return service.save(product);
    }

    @ApiOperation(value = "Update a product registration by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Updated", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request. Updated product not found", response = Product.class),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@PathVariable Integer id,
                         @RequestBody @Validated Product product) {

        service.findProductById(id)
                .map(productReturn -> {
                    product.setId(productReturn.getId());
                    productReturn = service.save(product);
                    return productReturn;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Product not exist"));

        return service.updade(product);
    }

    @ApiOperation(value = "Delete a product registration by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Deleted"),
            @ApiResponse(code = 400, message = "Delete product not found"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.findProductById(id)
                .map(product -> {
                    service.delete(id);
                    return product;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Product not content"));
    }
}
