package br.com.luiza.labs.challenge;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JWTAuthenticationTest {

    @Autowired
    private MockMvc mock;

    @Test
    public void shouldNotAllowAccessToUnauthenticated() throws Exception {
        mock.perform(get("/api/client/hello")).andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnAccessToAuthenticatedUser() throws Exception {
        ResultActions result = getResultToken();
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAccessToAuthenticated() throws Exception {
        MvcResult result = getResultToken().andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/client/hello")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    private ResultActions getResultToken() throws Exception {
        return mock.perform(
                post("/authenticate").content("{\"username\":\"luizalabs\",\"password\":\"luizalabs\"}")
        );
    }

}
