package br.com.luiza.labs.challenge.controller;

import br.com.luiza.labs.challenge.entity.Client;
import br.com.luiza.labs.challenge.service.impl.ClientServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
@Api(value = "Client", description = "Operations pertaining to client")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @ApiOperation(value = "Find a list of clients registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a register clients", response = Client.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        List<Client> clients = service.findAll();
        if (clients.size() > 0) {
            return ResponseEntity.ok(clients);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Find a list pagined of client registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list pagined of client registration. Page default 0 (zero).", response = Client.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "/pagination", method = RequestMethod.GET)
    public ResponseEntity findAllPagined(@RequestParam(defaultValue = "0")  Integer page) {
        List<Client> clients = service.findPaginated(page);
        if (clients.size() > 0) {
            return ResponseEntity.ok(clients);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Find a client registration by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a client registration", response = Client.class),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Client findProductById(@PathVariable Integer id) {
        return service.findClientById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    }

    @ApiOperation(value = "Create a client registration")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Created", response = Client.class),
            @ApiResponse(code = 400, message = "Bad Request. Failed to save user."),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody @Validated Client client) {
        try {
            client = service.save(client);
        } catch (Exception ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                if (ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getCause().getCause().getMessage());
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getCause().getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return client;
    }

    @ApiOperation(value = "Update a client registration by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Updated", response = Client.class),
            @ApiResponse(code = 400, message = "Bad Request. Updated client not found", response = Client.class),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@PathVariable Integer id,
                                 @RequestBody @Validated Client client) {

        service.findClientById(id)
                .map(clientReturn -> {
                    client.setId(clientReturn.getId());
                    clientReturn = service.save(client);
                    return clientReturn;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Client not exist"));

        return service.updade(client);
    }

    @ApiOperation(value = "Delete a client registration by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registry Deleted"),
            @ApiResponse(code = 400, message = "Delete client not found"),
            @ApiResponse(code = 403, message = "Forbidden. Access is denied")
    })
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.findClientById(id)
                .map(client -> {
                    service.delete(id);
                    return client;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Client not content"));
    }
}
