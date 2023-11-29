package solution.onlineretailer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ProductSuggestionRestControllerTests {

	@Autowired
    private TestRestTemplate restTemplate;

	@BeforeEach
	public void init() {
        restTemplate.delete("/productSuggestions");
	}
	
	@Test
	public void getAllProductSuggestions_noProductSuggestionsExist_returnsEmptyList()	 {

        ResponseEntity<List<ProductSuggestion>> responseEntity = restTemplate.exchange(
        		"/productSuggestions",
        		HttpMethod.GET,
        		null,
        		new ParameterizedTypeReference<List<ProductSuggestion>>() {});

        List<ProductSuggestion> responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseBody.size());
    }

	@Test
	public void insertProductSuggestion_productSuggestionInsertedAndReturned()	 {

		ProductSuggestion ps = new ProductSuggestion("ProductA", 10.99, 10L);
        ResponseEntity<ProductSuggestion> responseEntity = restTemplate.postForEntity(
        		"/productSuggestions",
        		ps,
        		ProductSuggestion.class);

        ProductSuggestion responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseBody.getId());
    }

	@Test
	public void modifyPrice_productPriceModified()	 {

		ProductSuggestion ps = new ProductSuggestion("ProductA", 10.99, 10L);
        ResponseEntity<ProductSuggestion> postResponseEntity = restTemplate.postForEntity(
        		"/productSuggestions",
        		ps,
        		ProductSuggestion.class);
        ps = postResponseEntity.getBody();	// Has a valid ID now.

        String putUrl = String.format("/productSuggestions/modifyPrice/%d?newPrice=11.95", ps.getId());
        restTemplate.put(putUrl, null);

        ResponseEntity<List<ProductSuggestion>> getResponseEntity = restTemplate.exchange(
        		"/productSuggestions",
        		HttpMethod.GET,
        		null,
        		new ParameterizedTypeReference<List<ProductSuggestion>>() {});

        List<ProductSuggestion> responseBody = getResponseEntity.getBody();
        assertEquals(1, responseBody.size());
        assertEquals(11.95, responseBody.get(0).getRecommendedPrice(), 0.01);
	}

	@Test
	public void modifySales_productSalesModified()	 {

		ProductSuggestion ps = new ProductSuggestion("ProductB", 10.99, 10L);
        ResponseEntity<ProductSuggestion> postResponseEntity = restTemplate.postForEntity(
        		"/productSuggestions",
        		ps,
        		ProductSuggestion.class);
        ps = postResponseEntity.getBody();	// Has a valid ID now.

        String putUrl = String.format("/productSuggestions/modifySales/%d?newSales=20", ps.getId());
        restTemplate.put(putUrl, null);

        ResponseEntity<List<ProductSuggestion>> getResponseEntity = restTemplate.exchange(
        		"/productSuggestions",
        		HttpMethod.GET,
        		null,
        		new ParameterizedTypeReference<List<ProductSuggestion>>() {});

        List<ProductSuggestion> responseBody = getResponseEntity.getBody();
        assertEquals(1, responseBody.size());
        assertEquals(20, responseBody.get(0).getEstimatedAnnualSales());
	}
}
