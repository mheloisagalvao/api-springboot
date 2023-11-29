package solution.webflux2.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import solution.webflux2.Employee;
import solution.webflux2.EmployeeService;

import java.net.URI;

@Log4j2
@Component
@Profile("handler-style-endpoint")
public class EmployeeRestHandler {

    private final EmployeeService service;

    EmployeeRestHandler(EmployeeService service) {
        this.service = service;
    }

    Mono<ServerResponse> getAll(ServerRequest req) {

        log.info("handler-style-endpoint, getAll()");

        Flux<Employee> gotten = this.service.getAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gotten, Employee.class);
    }

    Mono<ServerResponse> getById(ServerRequest req) {

        log.info("handler-style-endpoint, getById()");

        Long id = Long.parseLong(req.pathVariable("id"));

        Mono<Employee> gotten = this.service.getById(id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gotten, Employee.class);
    }

    Mono<ServerResponse> create(ServerRequest req) {

        log.info("handler-style-endpoint, create()");

        Flux<Employee> created = req.bodyToFlux(Employee.class)
                                    .flatMap(e -> this.service.create(e.getName(), e.getSalary(), e.getRegion()));

        return Mono.from(created)
                   .flatMap(e -> ServerResponse.created(URI.create("/employees/" + e.getId()))
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .build());
    }

    Mono<ServerResponse> update(ServerRequest req) {

        log.info("handler-style-endpoint, update()");

        Long id = Long.parseLong(req.pathVariable("id"));

        Flux<Employee> updated = req.bodyToFlux(Employee.class)
                                    .flatMap(e -> this.service.update(id, e.getName(), e.getSalary(), e.getRegion()));

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(updated, Employee.class);
    }

    Mono<ServerResponse> delete(ServerRequest req) {

        log.info("handler-style-endpoint, delete()");

        Long id = Long.parseLong(req.pathVariable("id"));

        Mono<Employee> deleted = this.service.delete(id);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(deleted, Employee.class);
    }
}