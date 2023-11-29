package solution.webflux1;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@Log4j2
@DataR2dbcTest
@Import(EmployeeService.class)
public class EmployeeServiceModificationsTest {

    private final EmployeeService service;
    private final EmployeeRepository repo;

    public EmployeeServiceModificationsTest(@Autowired EmployeeService service, @Autowired EmployeeRepository repo, @Autowired DatabaseClient client) {
        this.service = service;
        this.repo = repo;

        client.execute("create table if not exists employee (id int auto_increment primary key, name varchar, salary number, region varchar);")
                .then()
                .subscribe(e -> log.info("Created"));
    }

    @Test
    public void createEmployee_returnsCreatedEmployeeWithId() {

        Mono<Employee> createdEmployee = service.create("Andrew", 7000, "Preston");

        StepVerifier
                .create(createdEmployee)
                .expectNextMatches(emp -> emp.getId() != null)
                .verifyComplete();
    }

    @Test
    public void updateEmployee_employeeUpdated() {

        Mono<Employee> updatedEmployee = service.create("Simon", 8000, "Brixton")
                                                .flatMap(emp -> service.update(emp.getId(), "Peter", 8000, "Brixton"));

        StepVerifier
                .create(updatedEmployee)
                .expectNextMatches(emp -> emp.getName().equals("Peter"))
                .verifyComplete();
    }

    @Test
    public void deleteEmployee_employeeDeleted() {

        Mono<Employee> deletedEmployee = service.create("Wendy", 9000, "London")
                .flatMap(emp -> service.delete(emp.getId()));

        StepVerifier
                .create(deletedEmployee)
                .expectNextMatches(emp -> emp.getSalary() == 9000)
                .verifyComplete();
    }
}