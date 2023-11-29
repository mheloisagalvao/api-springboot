package solution.onlineretailer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductSuggestionRepositoryTests {

    @Autowired
	ProductSuggestionRepository repository;

    ProductSuggestion ps1, ps2, ps3;
    
    @BeforeEach
    public void init() {

    	ps1 = new ProductSuggestion("ProductA", 10, 1000);
    	ps2 = new ProductSuggestion("ProductB", 20, 2000);
    	ps3 = new ProductSuggestion("ProductC", 30, 3000);

    	repository.deleteProductSuggestions();
    	repository.insertProductSuggestion(ps1);
    	repository.insertProductSuggestion(ps2);
    	repository.insertProductSuggestion(ps3);
    }
  
	@Test
	public void productSuggestionsPersisted()	 {

    	List<ProductSuggestion> items = (List<ProductSuggestion>)repository.getProductSuggestions();
    	
    	assertEquals(3, items.size());
    	assertProductSuggestionEquals(ps1, items.get(0));
    	assertProductSuggestionEquals(ps2, items.get(1));
    	assertProductSuggestionEquals(ps3, items.get(2));
	}
	
	@Test
	public void modifyPrice()	 {

		repository.modifyPrice(ps1.getId(), 15);
		repository.modifyPrice(ps2.getId(), 25);
		repository.modifyPrice(ps3.getId(), 35);
    	
		List<ProductSuggestion> items = (List<ProductSuggestion>)repository.getProductSuggestions();
    	
		ps1.setRecommendedPrice(15);
    	assertProductSuggestionEquals(ps1, items.get(0));

		ps2.setRecommendedPrice(25);
    	assertProductSuggestionEquals(ps2, items.get(1));

    	ps3.setRecommendedPrice(35);
    	assertProductSuggestionEquals(ps3, items.get(2));
	}

	@Test
	public void modifySales()	 {

		repository.modifySales(ps1.getId(), 1500);
		repository.modifySales(ps2.getId(), 2500);
		repository.modifySales(ps3.getId(), 3500);
    	
		List<ProductSuggestion> items = (List<ProductSuggestion>)repository.getProductSuggestions();
    	
		ps1.setEstimatedAnnualSales(1500);
    	assertProductSuggestionEquals(ps1, items.get(0));

		ps2.setEstimatedAnnualSales(2500);
    	assertProductSuggestionEquals(ps2, items.get(1));

    	ps3.setEstimatedAnnualSales(3500);
    	assertProductSuggestionEquals(ps3, items.get(2));
	}

	private boolean assertProductSuggestionEquals(ProductSuggestion expected, ProductSuggestion actual) {
		return expected.getDescription().equals(actual.getDescription()) &&
			   expected.getRecommendedPrice() == actual.getRecommendedPrice() &&
			   expected.getEstimatedAnnualSales() == actual.getEstimatedAnnualSales();
	}
}
