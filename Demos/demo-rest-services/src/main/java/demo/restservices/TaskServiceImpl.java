package demo.restservices;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    private static Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;

    // Populate collection with some simple Tasks, to get the ball rolling.
    {
        insert(new Task("Complete Spring Boot tutorial", false));
        insert(new Task("Write API documentation", true));
        insert(new Task("Refactor code", false));
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> getTasks() {
        return tasks.values();
    }

    @Override
    public void insert(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void update(Task task) {
        int id = task.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        }
    }

    @Override
    public void delete(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
        }
    }
}
