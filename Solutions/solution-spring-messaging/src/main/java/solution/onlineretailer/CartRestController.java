package solution.onlineretailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CartRestController {

	@Autowired
	private Map<Integer, Item> catalog;
	
	@Autowired
	private CartService service;
	
    @GetMapping(value="/items", produces={"application/json","application/xml"})
    public List<Item> getAllItems() {
    	return service.getAllItemsInCart()
    			      .keySet()
    			      .stream()
    			      .map(id -> catalog.get(id))
    			      .collect(Collectors.toList());
    }

	@GetMapping(value="/cartCost", produces={"application/json","application/xml"})
	public double getCartCost() {
		return service.calculateCartCost();
	}

    @GetMapping(value="/quantityForItem/{itemId}", produces={"application/json","application/xml"})
	public int getQuantityForItem(@PathVariable int itemId) {
    	Integer quantity = service.getAllItemsInCart().get(itemId);
    	if (quantity != null) {
    		return quantity;
    	}
    	else {
    		return 0;
    	}
	}
}