package solution.webflux2.rest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import solution.webflux2.Employee;
import solution.webflux2.EmployeeRepository;

import java.time.LocalDateTime;

@Log4j2
@WebFluxTest
public class EmployeeRestBaseTest {

    @MockBean
    private EmployeeRepository repo;

    private final WebTestClient client;

    public EmployeeRestBaseTest(WebTestClient client) {
        this.client = client;
    }

    @Test
    public void getAll() {

        Mockito.when(this.repo.findAll())
               .thenReturn(Flux.just(new Employee(1L, "Matt", 1000, "London"),
                                     new Employee(2L, "Mark", 2000, "Boston")));

        this.client
                .get()
                .uri("/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(1)
                .jsonPath("$.[0].name").isEqualTo("Matt")
                .jsonPath("$.[1].id").isEqualTo(2)
                .jsonPath("$.[1].name").isEqualTo("Mark");
    }

    @Test
    public void getById() {

        Employee emp = new Employee(3L, "Luke", 3000, "London");

        Mockito.when(this.repo.findById(emp.getId()))
                .thenReturn(Mono.just(emp));

        this.client
                .get()
                .uri("/employees/" + emp.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.name").isEqualTo("Luke");
    }

    @Test
    public void save() {

        Employee emp = new Employee(4L, "John", 4000, "Boston");

        Mockito.when(this.repo.save(Mockito.any(Employee.class)))
               .thenReturn(Mono.just(emp));

        this.client
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(emp), Employee.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void update() {

        Employee emp = new Employee(5L, "Peter", 5000, "Euston");

        Mockito.when(this.repo.findById(emp.getId()))
               .thenReturn(Mono.just(emp));

        Mockito.when(this.repo.save(emp))
               .thenReturn(Mono.just(emp));

        this.client
                .put()
                .uri("/employees/" + emp.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(emp), Employee.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void delete() {

        Employee emp = new Employee(6L, "Paul", 6000, "Newton");

        Mockito.when(this.repo.findById(emp.getId()))
               .thenReturn(Mono.just(emp));

        Mockito.when(this.repo.deleteById(emp.getId()))
               .thenReturn(Mono.empty());

        this.client
            .delete()
            .uri("/employees/" + emp.getId())
            .exchange()
            .expectStatus().isOk();
    }
}
