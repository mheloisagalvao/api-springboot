package solution.onlineretailer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTests {

    @MockBean
    private CartRepository repositoryMock;

    @Autowired
    CartService service;

    Map<Integer, Integer> items;
    
    @BeforeEach
    public void setup() {
    	items = new HashMap<>();
    	items.put(0, 10);	// Item 0 unit price is 2499.99
    	items.put(1, 20);   // Item 1 unit price is 15.99
    	items.put(2, 30);   // Item 2 unit price is 1800.99
    }
    
	@Test
	public void addItemToCart_validItemId_itemAddedToCart()	 {
		service.addItemToCart(1, 100);
        verify(repositoryMock).add(1, 100);
	}

	@Test
	public void addItemToCart_invalidItemId_itemNotAddedToCart()	 {
		service.addItemToCart(12345, 100);
        verify(repositoryMock, times(0)).add(1, 100);
	}

	@Test
	public void removeItemFromCart_validItemId_itemRemovedFromCart()	 {
		service.removeItemFromCart(1);
        verify(repositoryMock).remove(1);
	}

	@Test
	public void removeItemFromCart_invalidItemId_itemNotRemovedFromCart()	 {
		service.removeItemFromCart(12345);
        verify(repositoryMock, times(0)).remove(1);
	}
	
	@Test
	public void calculateCartCost_emptyCart_returnsZero()	 {
		
		when(repositoryMock.getAll()).thenReturn(new HashMap<>());
        
		double actual = service.calculateCartCost();
		double expected = 0;
		assertEquals(expected, actual, 0.01);
		
		verify(repositoryMock).getAll();
	}

	@Test
	public void calculateCartCost_itemsInCart_returnsCorrectCost()	 {
		
	    Map<Integer, Integer> items = new HashMap<>();
    	items.put(0, 1);   // Item 0 unit price is 2499.99
    	items.put(1, 2);   // Item 1 unit price is 15.99
    	items.put(2, 3);   // Item 2 unit price is 1800.99

	    when(repositoryMock.getAll()).thenReturn(items);
        
		double actual = service.calculateCartCost();
		double expected = 7934.94;
		assertEquals(expected, actual, 0.01);
		
		verify(repositoryMock).getAll();
	}

	@Test
	public void calculateSalesTax_itemsInCart_returnsCorrectSalesTax()	 {
		
	    Map<Integer, Integer> items = new HashMap<>();
    	items.put(0, 1);   // Item 0 unit price is 2499.99
    	items.put(1, 2);   // Item 1 unit price is 15.99
    	items.put(2, 3);   // Item 2 unit price is 1800.99

	    when(repositoryMock.getAll()).thenReturn(items);
        
		double actual = service.calculateSalesTax();
		double expected = 1586.99;
		assertEquals(expected, actual, 0.01);
		
		verify(repositoryMock).getAll();
	}

	@Test
	public void calculateDeliveryCharge_noItemsInCart_returnsZero()	 {
		
	    when(repositoryMock.getAll()).thenReturn(new HashMap<>());
        
		double actual = service.calculateDeliveryCharge();
		double expected = 0;
		assertEquals(expected, actual, 0.01);
		
		verify(repositoryMock).getAll();
	}

	@Test
	public void calculateDeliveryCharge_itemsBelowThreshold_returnsStandardDeliveryCharge()	 {
		
	    Map<Integer, Integer> items = new HashMap<>();
    	items.put(1, 2);   // Item 1 unit price is 15.99

	    when(repositoryMock.getAll()).thenReturn(items);

	    double actual = service.calculateDeliveryCharge();
		double expected = 2.50;
		assertEquals(expected, actual, 0.01);
		
		verify(repositoryMock).getAll();
	}

	@Test
	public void calculateDeliveryCharge_itemsAboveThreshold_returnsZeroDeliveryCharge()	 {
		
	    Map<Integer, Integer> items = new HashMap<>();
    	items.put(0, 2);   // Item 0 unit price is 2499.99

	    when(repositoryMock.getAll()).thenReturn(items);

	    double actual = service.calculateDeliveryCharge();
		double expected = 0;
		assertEquals(expected, actual, 0.01);
		
		verify(repositoryMock).getAll();
	}
}
