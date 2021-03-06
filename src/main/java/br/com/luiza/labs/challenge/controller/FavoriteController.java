package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Favorite;
import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.service.impl.FavoriteImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping(value = "/favorite")
@Api(value = "Favorite", description = "Operations pertaining to product favorite list")
public class FavoriteController {

    @Autowired
    private FavoriteImpl service;

    @ApiOperation(value = "Find a products favorite list by client id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list products registration", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity findAllProductByIdClient(@PathVariable Integer id) {
        List<Favorite> products = service.findAllProductByIdClient(id);
        if (products.size() > 0) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Create a favorite product registration")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Created", response = Favorite.class),
            @ApiResponse(code = 400, message = "Bad Request. Failed to save product favorite."),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Favorite save(@RequestBody @Validated Favorite favorite) {
        List<Favorite> favorites = service.findByProductId(favorite.getProduct().getId());
        if (favorites.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product duplicated");
        }

        try {
            favorite = service.save(favorite);
        } catch (Exception ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ((ConstraintViolationException) ex.getCause()).getSQLException().getMessage());
            }
        }

        return favorite;
    }
}

