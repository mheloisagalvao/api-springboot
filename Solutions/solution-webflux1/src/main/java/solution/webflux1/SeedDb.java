package solution.webflux1;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Component
public class SeedDb implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DatabaseClient client;

    @Autowired
    private EmployeeRepository repo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        client.execute(
                "create table employee (" +
                  "id int auto_increment primary key, " +
                  "name varchar, " +
                  "salary number, " +
                  "region varchar);")
              .then()
              .subscribe(e -> log.info("Created"));

        List<Employee> emps = Stream.of(
            new Employee(null, "James", 25000, "London"),
            new Employee(null, "James", 21000, "London"),
            new Employee(null, "Marie", 22000, "Edinburgh"),
            new Employee(null, "Peter", 23000, "Belfast"),
            new Employee(null, "Sally", 24000, "Cardiff"),
            new Employee(null, "Peter", 51000, "London"),
            new Employee(null, "Berty", 52000, "Edinburgh"),
            new Employee(null, "Milly", 53000, "Belfast"),
            new Employee(null, "Jayne", 54000, "Cardiff"),
            new Employee(null, "Wally", 91000, "London"),
            new Employee(null, "Emily", 92000, "Edinburgh"),
            new Employee(null, "Tommy", 93000, "Belfast"),
            new Employee(null, "Colin", 94000, "Cardiff"),
            new Employee(null, "Sarah", 121000, "London"),
            new Employee(null, "Darel", 122000, "Edinburgh"),
            new Employee(null, "Benji", 123000, "Belfast"),
            new Employee(null, "Carys", 124000, "Cardiff")
        ).collect(Collectors.toList());

        repo.deleteAll()
            .thenMany(Flux.fromIterable(emps))
                          .flatMap(emp -> repo.save(emp))
            .thenMany(repo.findAll())
            .subscribe(emp -> log.info("Employee successfully inserted: " + emp));
    }
}
