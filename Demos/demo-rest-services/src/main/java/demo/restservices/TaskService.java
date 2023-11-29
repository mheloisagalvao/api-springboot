package demo.restservices;

import java.util.Collection;

public interface TaskService {

    Task getTask(int id);
    Collection<Task> getTasks();

    void insert(Task task);
    void update(Task task);
    void delete(int id);
}
