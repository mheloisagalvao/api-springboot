package solution.webflux2.rest;

import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solution.webflux2.Employee;
import solution.webflux2.EmployeeService;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping(value="/employees", produces=MediaType.APPLICATION_JSON_VALUE)
@Profile("controller-style-endpoint")
class EmployeeRestController {

    private final EmployeeService service;

    EmployeeRestController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    Publisher<Employee> getAll() {
        log.info("controller-style-endpoint, getAll()");
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    Publisher<Employee> getById(@PathVariable("id") Long id) {
        log.info("controller-style-endpoint, getById()");
        return this.service.getById(id);
    }

    @PostMapping
    Publisher<ResponseEntity<Employee>> create(@RequestBody Employee emp) {
        log.info("controller-style-endpoint, create()");
        return this.service
                   .create(emp.getName(), emp.getSalary(), emp.getRegion())
                   .map(e -> ResponseEntity.created(URI.create("/employees/" + e.getId()))
                                           .contentType(MediaType.APPLICATION_JSON)
                                           .build());
    }

    @PutMapping("/{id}")
    Publisher<ResponseEntity<Employee>> update(@PathVariable Long id, @RequestBody Employee emp) {
        log.info("controller-style-endpoint, update()");
        return this.service
                   .update(id, emp.getName(), emp.getSalary(), emp.getRegion())
                   .map(t -> ResponseEntity.ok()
                                           .contentType(MediaType.APPLICATION_JSON)
                                           .build());
    }

    @DeleteMapping("/{id}")
    Publisher<Employee> delete(@PathVariable Long id) {
        log.info("controller-style-endpoint, delete()");
        return this.service.delete(id);
    }
}