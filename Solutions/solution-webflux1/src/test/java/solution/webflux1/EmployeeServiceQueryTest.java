package solution.webflux1;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.function.Predicate;

@Log4j2
@DataR2dbcTest
@Import(EmployeeService.class)
public class EmployeeServiceQueryTest {

    private final EmployeeService service;
    private final EmployeeRepository repo;

    public EmployeeServiceQueryTest(@Autowired EmployeeService service, @Autowired EmployeeRepository repo, @Autowired DatabaseClient client) {
        this.service = service;
        this.repo = repo;

        client.execute("create table if not exists employee (id int auto_increment primary key, name varchar, salary number, region varchar);")
                .then()
                .subscribe(e -> log.info("Created"));
    }

    @Test
    public void getAllEmployees_returnsAllEmployee() {

        Flux<Employee> toSave = Flux.just(
                new Employee(null, "Matt", 1000, "London"),
                new Employee(null, "Mark", 2000, "Boston"),
                new Employee(null, "Luke", 3000, "London"),
                new Employee(null, "John", 4000, "Boston")
        );

        Mono<Void> deletedEmployees = repo.deleteAll();
        StepVerifier
                .create(deletedEmployees)
                .verifyComplete();

        Flux<Employee> savedEmployees = repo.saveAll(toSave);
        StepVerifier
                .create(savedEmployees)
                .expectNextCount(4)
                .verifyComplete();

        savedEmployees.subscribe(s -> System.out.println("Saved! " + s));

        Flux<Employee> gottenEmployees = service.getAll();

        Predicate<Employee> testEmployee = gemp -> toSave.any(semp -> {
            System.out.println("GOTTEN: " + gemp.getId() + " " + gemp.getName() + " " + gemp.getSalary() + " " + gemp.getRegion());
            System.out.println("SAVED:  " + semp.getId() + " " + semp.getName() + " " + semp.getSalary() + " " + semp.getRegion());
            System.out.println("RESULT: " + semp.equals(gemp) + "\n");
            return semp.equals(gemp);
        }).block();

        StepVerifier.create(gottenEmployees)
                .expectNextMatches(testEmployee)
                .expectNextMatches(testEmployee)
                .expectNextMatches(testEmployee)
                .expectNextMatches(testEmployee)
                .verifyComplete();
    }
}