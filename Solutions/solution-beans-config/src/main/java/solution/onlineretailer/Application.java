package solution.onlineretailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        CartService service = context.getBean(CartServiceImpl.class);

        // Buy an Apple Mac Book Pro.
        service.addItemToCart(0, 1);

        // Buy an OLED 64in TV.
        service.addItemToCart(2, 1);

        // Buy 5 Virtual Reality HeadSets.
        service.addItemToCart(4, 3);
        service.addItemToCart(4, 2);

        // Remove the Apple Mac Book Pro from cart.
        service.removeItemFromCart(0);

        // Get total cost of items in basket.
        double totalCost = service.calculateCartCost();
        System.out.printf("Total cart cost is £%.2f\n", totalCost);
    }

    @Bean
    public Map<Integer, Item> catalog() {
        Map<Integer, Item> items = new HashMap<>();
        items.put(0, new Item(0, "Apple Mac Book Pro", 2499.99));
        items.put(1, new Item(1, "32GB San Disk", 15.99));
        items.put(2, new Item(2, "OLED 64in TV", 1800));
        items.put(3, new Item(3, "Wireless Mouse", 10.50));
        items.put(4, new Item(4, "Virtual Reality HeadSet", 200));
        return items;
    }
}
