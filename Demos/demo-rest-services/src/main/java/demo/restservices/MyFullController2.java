package demo.restservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@Controller
@RequestMapping(value = "/taskManager")
@CrossOrigin
public class MyFullController2 {

    @Autowired
    private TaskService taskService;

// Get all tasks.
@GetMapping(value = "/tasks", produces = {"application/json", "application/xml"})
public ResponseEntity<Collection<Task>> getTasks() {
    Collection<Task> result = taskService.getTasks();
    return ResponseEntity.ok().body(result);
}

// Get a specific task.
@GetMapping(value = "/task/{id}", produces = {"application/json", "application/xml"})
public ResponseEntity<Task> getTask(@PathVariable int id) {
    Task result = taskService.getTask(id);
    if (result == null)
        return ResponseEntity.notFound().build();
    else
        return ResponseEntity.ok().body(result);
}

// Insert a new task.
@PostMapping(value = "/task",
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"})
public ResponseEntity<Task> addTask(@RequestBody Task task) {
    taskService.insert(task);
    URI uri = URI.create("/task/" + task.getId());
    return ResponseEntity.created(uri).body(task);
}

// Update an existing task.
@PutMapping(value = "/task/{id}", consumes = {"application/json", "application/xml"})
public ResponseEntity<Void> modifyTask(@PathVariable int id, @RequestBody Task task) {
    if (taskService.getTask(id) == null)
        return ResponseEntity.notFound().build();
    else {
        taskService.update(task);
        return ResponseEntity.ok().build();
    }
}

// Delete an existing task.
@DeleteMapping("/task/{id}")
public ResponseEntity<Void> deleteTask(@PathVariable int id) {
    if (taskService.getTask(id) == null)
        return ResponseEntity.notFound().build();
    else {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
}
