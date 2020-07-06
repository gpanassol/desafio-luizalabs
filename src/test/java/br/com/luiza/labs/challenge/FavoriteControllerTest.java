package br.com.luiza.labs.challenge;

import br.com.luiza.labs.challenge.controller.FavoriteController;
import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.entity.ProductClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
                        .content(objectMapper.writeValueAsString(new ProductClient()))

        ).andExpect(status().isForbidden());
    }
}
