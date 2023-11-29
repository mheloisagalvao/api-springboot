package solution.webflux1;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Log4j2
@Service
public class EmployeeService {

    private final EmployeeRepository repo;
    private final ApplicationEventPublisher pub;

    EmployeeService(EmployeeRepository repo, ApplicationEventPublisher pub) {
        this.repo = repo;
        this.pub = pub;
    }

    public Mono<Long> getNum() {
        return repo.count();
    }

    public Flux<Employee> getAll() {
        return repo.findAll();
    }

    public Mono<Employee> getById(Long id) {
        return repo.findById(id);
    }

    public Mono<Employee> create(String name, double salary, String region) {
        return repo.save(new Employee(null, name, salary, region));
    }

    public Mono<Employee> update(Long id, String name, double salary, String region) {
        return repo.findById(id)
                   .map(e -> new Employee(e.getId(), name, salary, region))
                   .flatMap(t -> repo.save(t).thenReturn(t));
    }

    public Mono<Employee> delete(Long id) {
        return repo.findById(id)
                   .flatMap(e -> repo.deleteById(e.getId()).thenReturn(e));
    }
}
