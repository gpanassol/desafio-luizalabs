package br.com.luiza.labs.challenge;

import br.com.luiza.labs.challenge.controller.FavoriteController;
import br.com.luiza.labs.challenge.entity.Client;
import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.entity.Favorite;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteControllerTest extends AbstractMvcTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private FavoriteController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void notShouldRegisterFavoriteProductForbidden() throws Exception {
        mock.perform(
                post("/favorite/")
                        .content(objectMapper.writeValueAsString(new Favorite()))

        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldRegisterFavoriteProduct() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        //CREATE PRODUCT
        Product product = new Product();
        product.setTitle("Title Test Favorite");
        product.setMarca("brand Test");
        product.setReviewScore(1.0);
        product.setUrl("http://teste.com");
        product.setPrice(BigDecimal.valueOf(1.0));

        ResultActions resultActions = mock.perform(
                post("/product")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product))
        ).andExpect(status().isCreated());

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        product = objectMapper.readValue(contentAsString, Product.class);

        assertThat(product.getId()).isNotNull();

        //CREATE CLIENT
        Client client = new Client();
        client.setName("Client teste Favorite");
        client.setEmail("testefavorite@teste.com.br");

        resultActions = mock.perform(
                post("/client")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client))
        ).andExpect(status().isCreated());

        mvcResult = resultActions.andReturn();
        contentAsString = mvcResult.getResponse().getContentAsString();
        client = objectMapper.readValue(contentAsString, Client.class);

        assertThat(client.getId()).isNotNull();

        //CREATE LIST FAVORITE
        Favorite favorite = new Favorite();
        favorite.setClient(client);
        favorite.setProduct(product);

        resultActions = mock.perform(
                post("/favorite")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(favorite))
        ).andExpect(status().isCreated());
    }
}
