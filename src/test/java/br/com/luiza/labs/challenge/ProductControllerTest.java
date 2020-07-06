package br.com.luiza.labs.challenge;

import br.com.luiza.labs.challenge.controller.ProductController;
import br.com.luiza.labs.challenge.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest extends AbstractMvcTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void notShouldRegisterProductForbidden() throws Exception {
        mock.perform(
                post("/product/")
                .content(objectMapper.writeValueAsString(new Product()))

        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldRegisterProduct() throws Exception {
        Product product = new Product();
        product.setTitle("Title Test");
        product.setMarca("brand Test");
        product.setReviewScore(1.0);
        product.setUrl("http://teste.com");
        product.setPrice(BigDecimal.valueOf(1.0));

        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                post("/product/")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))

        ).andExpect(status().isCreated());
    }

    @Test
    public void notShouldUpdateProductForbidden() throws Exception {
        mock.perform(
                put("/product/")
                        .content(objectMapper.writeValueAsString(new Product()))

        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        Product product = new Product();
        product.setTitle("Title Test 2");
        product.setMarca("brand Test 2");
        product.setReviewScore(2D);
        product.setUrl("http://teste2.com");
        product.setPrice(BigDecimal.valueOf(2L));

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

        product.setTitle("Title Test 2 - UPDATED");

        mock.perform(
                put("/product/" + product.getId() )
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product))
        ).andExpect(status().isCreated());
    }

    @Test
    public void notShouldDeleteClientForbidden() throws Exception {
        mock.perform(
                put("/product/1")
                        .contentType("application/json")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        Product product = new Product();
        product.setTitle("Title Test 3");
        product.setMarca("brand Test 3");
        product.setReviewScore(3D);
        product.setUrl("http://teste3.com");
        product.setPrice(BigDecimal.valueOf(3L));

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

        mock.perform(
                delete("/product/" + product.getId())
                        .contentType("application/json")
                        .header("Authorization", token)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteClientNotExist() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);

        mock.perform(
                put("/product/999999")
                        .contentType("application/json")
                        .header("Authorization", token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnProductInitial() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/product/1")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void notShouldReturnProductInitialForbbiden() throws Exception {
        mock.perform(
                get("/product/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldProductNoContent() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/product/999999")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnListProduct() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/product/?page=0")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void notShouldReturnClientProductInitialForbbiden() throws Exception {
        mock.perform(
                get("/product/client/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnClientProductInitial() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/product/client/1")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldClientProductNoContent() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/product/client/133552")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }
}
