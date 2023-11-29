package solution.onlineretailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository repository;

	@Value("#{catalog}")	
	private Map<Integer, Item> catalog;
	
	@Override
	public void addItemToCart(int id, int quantity) {
		if (catalog.containsKey(id)) {
			repository.add(id, quantity);
		}
	}
	
	@Override
	public void removeItemFromCart(int id) {
		repository.remove(id);
	}
	
	@Override
	public Map<Integer, Integer> getAllItemsInCart() {
		return repository.getAll();
	}
	
	@Override
	public double calculateCartCost() {
		Map<Integer, Integer> items = repository.getAll();
		
		double totalCost = 0;
		for (Map.Entry<Integer, Integer> item: items.entrySet()) {
			int id = item.getKey();
			int quantity = item.getValue();
			double itemCost = catalog.get(id).getPrice() * quantity;
			totalCost += itemCost;
		}
		return totalCost;
	}
}
