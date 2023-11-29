package solution.onlineretailer;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CartRepositoryImpl implements CartRepository {

	// Map of item IDs and quantities.
	private Map<Integer, Integer> cart = new HashMap<>();
	
	@Override
	public void add(int itemId, int quantity) {
		Integer existingQuantity = cart.get(itemId);
		if (existingQuantity != null) {
			quantity += existingQuantity;			
		}
		cart.put(itemId,  quantity);
	}
	
	@Override
	public void remove(int itemId) {
		cart.remove(itemId);
	}
	
	@Override
	public Map<Integer, Integer> getAll() {
		return cart;
	}
}
