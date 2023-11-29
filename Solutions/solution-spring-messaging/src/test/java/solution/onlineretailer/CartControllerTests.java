package solution.onlineretailer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@WebMvcTest(CartController.class)
public class CartControllerTests {

    @Autowired
    MockMvc mockMvc;
    
	@MockBean
	CartService cartServiceMock;

	@Test
	public void testShowCatalogSuccessfully() throws Exception {

		mockMvc.perform(get("/"))
			   .andExpect(status().isOk())
			   .andExpect(xpath("//h3[contains(text(), 'Apple Mac Book Pro')]").exists())
			   .andExpect(xpath("//h3[contains(text(), '32GB San Disk')]").exists())
			   .andExpect(xpath("//h3[contains(text(), 'OLED 64in TV')]").exists())
			   .andExpect(xpath("//h3[contains(text(), 'Wireless Mouse')]").exists())
			   .andExpect(xpath("//h3[contains(text(), 'Virtual Reality HeadSet')]").exists())
			   .andExpect(xpath("//h3[contains(text(), 'Sat Nav')]").exists());
	}

	@Test
	public void testAddItemToCartSuccessfully() throws Exception {
	
		mockMvc.perform(get("/addItemToCart?id=0&quantity=10"))
			   .andExpect(status().isOk())
			   .andExpect(xpath("//div[contains(text(), 'Added to cart: Apple Mac Book Pro [x10]')]").exists());
		
		verify(cartServiceMock).addItemToCart(0, 10);
	}

	@Test
	public void testShowCartSuccessfully() throws Exception {

		Map<Integer, Integer> mockMap = new HashMap<>();
		mockMap.put(0, 10);
		mockMap.put(1, 20);
		when(cartServiceMock.getAllItemsInCart()).thenReturn(mockMap);
		when(cartServiceMock.calculateCartCost()).thenReturn(100.0);
		when(cartServiceMock.calculateSalesTax()).thenReturn(20.0);
		when(cartServiceMock.calculateDeliveryCharge()).thenReturn(2.50);
		
		mockMvc.perform(get("/showCart"))
			   .andExpect(status().isOk())
			   .andExpect(xpath("//div[(div[contains(text(), '10')]) and (div[contains(text(), 'Apple Mac Book Pro')])]").exists())
		       .andExpect(xpath("//div[(div[contains(text(), '20')]) and (div[contains(text(), '32GB San Disk')])]").exists())
		       .andExpect(xpath("//div[contains(text(), 'Cart cost') and (.//span[text() = '£100.00'])]").exists())
		       .andExpect(xpath("//div[contains(text(), 'Sales tax') and (.//span[text() = '£20.00'])]").exists())
		       .andExpect(xpath("//div[contains(text(), 'Delivery charge') and (.//span[text() = '£2.50'])]").exists());		
	}
}