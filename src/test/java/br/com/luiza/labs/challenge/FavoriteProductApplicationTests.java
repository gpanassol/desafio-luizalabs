package br.com.luiza.labs.challenge;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteProductApplicationTests {

	@Autowired
	private ProductServiceImpl productService;

	@Test
	public void shouldReturnInitialProductLoad() {

		Product productTest = new Product();
		productTest.setPrice(BigDecimal.valueOf(1599.99));
		productTest.setUrl("http://url.com/");
		productTest.setMarca("PROD-TEST-LG");
		productTest.setTitle("PROD TEST LG SMART TV PRO");
		productTest.setReviewScore(4.0);

		productService.salve(productTest);

		Optional<Product> product = productService.findProductById(productTest.getId());
		assertThat(product.get().getTitle()).isEqualTo(productTest.getTitle());
	}
}
