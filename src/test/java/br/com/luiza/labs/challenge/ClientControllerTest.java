package br.com.luiza.labs.challenge;

import br.com.luiza.labs.challenge.controller.ClientController;
import br.com.luiza.labs.challenge.entity.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest extends AbstractMvcTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ClientController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void notShouldRegisterClientForbidden() throws Exception {

        Client client = new Client();
        client.setName("CLIENTE TESTE 2");
        client.setEmail("teste@teste.com.br");
        client.setFavorites(null);

        mock.perform(
                post("/client")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldRegisterClient() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        Client client = new Client();
        client.setName("CLIENTE TESTE 2");
        client.setEmail("teste2@teste.com.br");

        mock.perform(
                post("/client")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isCreated());
    }

    @Test
    public void notShouldRegisterClientEmailDuplicate() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        Client client = new Client();
        client.setName("CLIENTE TESTE 3");
        client.setEmail("emailduplicated@teste.com.br");

        mock.perform(
                post("/client")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isCreated());

        client = new Client();
        client.setName("CLIENTE TESTE 3");
        client.setEmail("emailduplicated@teste.com.br");

        mock.perform(
                post("/client")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void notShouldUpdatedClientForbidden() throws Exception {

        Client client = new Client();
        client.setId(1);
        client.setName("Gabriel Panassol da Fonseca - Update");
        client.setEmail("gpanassolUpdate@gmail.com");

        mock.perform(
                put("/client")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isForbidden());

    }

    @Test
    public void shouldUpdatedClient() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        Client client = new Client();
        client.setId(1);
        client.setName("Gabriel Panassol da Fonseca - Update");
        client.setEmail("gpanassolUpdate@gmail.com");

        mock.perform(
                post("/client")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isCreated());
    }

    @Test
    public void notShouldDeleteClientForbidden() throws Exception {
        mock.perform(
                put("/client/1")
                        .contentType("application/json")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        Client client = new Client();
        client.setName("CLIENTE TESTE 4");
        client.setEmail("emailDeleted@teste.com.br");

        ResultActions resultActions = mock.perform(
                post("/client")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isCreated());

        MvcResult mvcResult = resultActions.andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        client = objectMapper.readValue(contentAsString, Client.class);

        assertThat(client.getId()).isNotNull();

        mock.perform(
                delete("/client/" + client.getId())
                        .contentType("application/json")
                        .header("Authorization", token)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteClientNotExist() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        mock.perform(
                put("/client/999999")
                        .contentType("application/json")
                        .header("Authorization", token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnClientById() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/client/1")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNoContentClientById() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/client/999999")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnListClientPagined() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/client/pagination?page=0")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldListClientPaginedNoContent() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/client/pagination?page=999999")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void notShouldReturnListClientPaginedForbbiden() throws Exception {
        mock.perform(
                get("/client/pagination?page=0"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnListClient() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/client")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void notShouldReturnListClientForbbiden() throws Exception {
        mock.perform(
                get("/client"))
                .andExpect(status().isForbidden());
    }

}
