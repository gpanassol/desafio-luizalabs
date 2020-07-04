package br.com.luiza.labs.challenge;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AbstractMvcTest {

    public ResultActions getResultToken(MockMvc mock) throws Exception {
        return mock.perform(
                post("/authenticate").content("{\"username\":\"luizalabs\",\"password\":\"luizalabs\"}")
        );
    }

}
