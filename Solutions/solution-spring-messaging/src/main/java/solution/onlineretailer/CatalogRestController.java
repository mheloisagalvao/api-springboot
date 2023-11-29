package solution.onlineretailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
@CrossOrigin
public class CatalogRestController {

    @Autowired
    private Map<Integer, Item> catalog;

    @GetMapping(produces={"application/json","application/xml"})
    public ResponseEntity<Collection<Item>> getAllItemsInCatalog() {
        Collection<Item> result = catalog.values();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/{id}", produces={"application/json","application/xml"})
    public ResponseEntity<Item> getItemInCatalog(@PathVariable int id) {
        if (!catalog.containsKey(id))
            return ResponseEntity.notFound().build();
        else {
            Item result = catalog.get(id);
            return ResponseEntity.ok().body(result);
        }
    }

    @PostMapping(consumes={"application/json","application/xml"},
                 produces={"application/json","application/xml"})
    public ResponseEntity<Item> addItemToCatalog(@RequestBody Item item) {
        System.out.println("Adding " + item);
        catalog.put(item.getId(), item);
        URI uri = URI.create("/catalog/" + item.getId());
        return ResponseEntity.created(uri).body(item);
    }

    @PutMapping(value="/{id}", consumes={"application/json","application/xml"})
    public ResponseEntity modifyItemInCatalog(@PathVariable int id, @RequestBody Item item) {
        if (!catalog.containsKey(id))
            return ResponseEntity.notFound().build();
        else {
            System.out.println("Updating item id " + id);
            catalog.put(id, item);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItemInCatalog(@PathVariable int id) {
        if (!catalog.containsKey(id))
            return ResponseEntity.notFound().build();
        else {
            System.out.println("Deleting item id " + id);
            catalog.remove(id);
            return ResponseEntity.ok().build();
        }
    }
}
