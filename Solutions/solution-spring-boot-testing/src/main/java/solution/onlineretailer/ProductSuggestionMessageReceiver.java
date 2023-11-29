package solution.onlineretailer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductSuggestionMessageReceiver {

    @JmsListener(destination="valuableProductSuggestionsDestination")
    public void receiveProductSuggestionMessage(ProductSuggestion ps) {
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("***** Valuable product suggestion *****************************************************\n");
        System.out.printf(" Timestamp: %s %s\n", now.toLocalDate(), now.toLocalTime());
        System.out.printf(" Details:   %s\n", ps);
        System.out.printf("***************************************************************************************\n");
    }
}
