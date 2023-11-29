package solution.onlineretailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class ProductSuggestionRepositoryImpl implements ProductSuggestionRepository {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public Collection<ProductSuggestion> getProductSuggestions() {
		String jpql = "select p from ProductSuggestion p";
		TypedQuery<ProductSuggestion> query = entityManager.createQuery(jpql, ProductSuggestion.class);
	    return query.getResultList();
	}

	@Override
	public ProductSuggestion getProductSuggestions(long id) {
		return entityManager.find(ProductSuggestion.class, id);
	}

	@Override
	@Transactional
	public long insertProductSuggestion(ProductSuggestion ps) {
		entityManager.persist(ps);
		entityManager.flush();
		sendMessageIfValuableProductSuggestion(ps);
		return ps.getId();
	}
	
	@Override
	@Transactional
	public boolean modifyPrice(long id, double newPrice) {
		ProductSuggestion ps = entityManager.find(ProductSuggestion.class, id);
		if (ps == null) {
			return false;
		}
		else {
			ps.setRecommendedPrice(newPrice);
			sendMessageIfValuableProductSuggestion(ps);
			return true;
		}
	}
	
	@Override
	@Transactional
	public boolean modifySales(long id, long newSales) {
		ProductSuggestion ps = entityManager.find(ProductSuggestion.class, id);
		if (ps == null) {
			return false;
		}
		else {
			ps.setEstimatedAnnualSales(newSales);
			sendMessageIfValuableProductSuggestion(ps);
			return true;
		}
	}

	@Override
	@Transactional
	public void deleteProductSuggestions() {
		Query query = entityManager.createQuery("delete from ProductSuggestion");
		query.executeUpdate();
	}

	private void sendMessageIfValuableProductSuggestion(ProductSuggestion ps) {
		if (ps.getRecommendedPrice() * ps.getEstimatedAnnualSales() >= 10_000) {
			// jmsTemplate.setPubSubDomain(true);
			jmsTemplate.convertAndSend("valuableProductSuggestionsDestination", ps);
		}
	}
}
