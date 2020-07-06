package br.com.luiza.labs.challenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JWTAuthenticationTest extends AbstractMvcTest {

    @Autowired
    private MockMvc mock;

    @Test
    public void shouldNotAllowAccessToUnauthenticated() throws Exception {
        mock.perform(get("/api/actuator/health")).andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnAccessToAuthenticatedUser() throws Exception {
        ResultActions result = getResultToken(mock);
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAccessToAuthenticated() throws Exception {
        MvcResult result = getResultToken(mock).andReturn();
        String token = result.getResponse().getHeaders("Authorization").get(0);
        mock.perform(
                get("/actuator/health")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

}
