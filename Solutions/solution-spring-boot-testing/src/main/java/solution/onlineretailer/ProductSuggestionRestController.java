package solution.onlineretailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@Controller
@RequestMapping("/productSuggestions")
@CrossOrigin
public class ProductSuggestionRestController {

	@Autowired
	private ProductSuggestionRepository repository;
	
    @GetMapping(produces={"application/json","application/xml"})
    public ResponseEntity<Collection<ProductSuggestion>> getAllProductSuggestions() {
    	Collection<ProductSuggestion> result = repository.getProductSuggestions();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value="/{id}", produces={"application/json","application/xml"})
	public ResponseEntity<ProductSuggestion> getProductSuggestion(@PathVariable long id) {
		ProductSuggestion result = repository.getProductSuggestions(id);
		if (result == null) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok().body(result);
		}
	}

	@PostMapping(consumes={"application/json","application/xml"},
			     produces={"application/json","application/xml"})
	public ResponseEntity<ProductSuggestion> insertProductSuggestion(@RequestBody ProductSuggestion productSuggestion) {
		long id = repository.insertProductSuggestion(productSuggestion);
		productSuggestion.setId(id);
		URI uri = URI.create("/productSuggestions/" + id);
		return ResponseEntity.created(uri).body(productSuggestion);
	}

    @PutMapping(value="/modifyPrice/{id}")
	public ResponseEntity modifyPrice(@PathVariable long id, @RequestParam double newPrice) {
    	if (!repository.modifyPrice(id, newPrice)) {
			return ResponseEntity.notFound().build();
		}
    	else {
			return ResponseEntity.ok().build();
		}
	}

    @PutMapping(value="/modifySales/{id}")
	public ResponseEntity modifySales(@PathVariable long id, @RequestParam long newSales) {
		if (!repository.modifySales(id, newSales)) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok().build();
		}
	}

    @DeleteMapping
	public ResponseEntity deleteAllProductSuggestions() {
    	repository.deleteProductSuggestions();
		return ResponseEntity.ok().build();
	}
}