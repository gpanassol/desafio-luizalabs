package br.com.luiza.labs.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.luiza.labs.challenge.entity.Product;
import br.com.luiza.labs.challenge.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class FavoriteProductApplicationTests {

	@Autowired
	private ProductServiceImpl productService;

	@Test
	public void shouldReturnInitialProductLoad() {

		Product productTest = new Product(1, new BigDecimal(1599.99),
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ_zRJioBh7-wXdtLrC4petOj-tYiJxtObUIBJ2vk639vof8-TSgUpGexRLZD7dzDGMQteicOLb&usqp=CAc",
				"LG", "LG SMART TV PRO", 4.0);

		productService.salve(productTest);

		Optional<Product> product = productService.findProductById(1);
		assertThat(product.get().getTitle()).isEqualTo(productTest.getTitle());
	}

}
