package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.entity.ProductClient;
import br.com.luiza.labs.challenge.service.impl.ProductClientImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/favorite")
@Api(value = "Favorite", description = "Operations pertaining to product favorite list")
public class FavoriteController {

    @Autowired
    private ProductClientImpl productClientService;

    @ApiOperation(value = "Find a products favorite list by client id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list products registration", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity findAllProductByIdClient(@PathVariable Integer id) {
        List<ProductClient> products = productClientService.findAllProductByIdClient(id);
        if (products.size() > 0) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }

}

