package solution.webflux2.rest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import solution.webflux2.EmployeeService;

@Log4j2
@ActiveProfiles("handler-style-endpoint")
@Import({EmployeeRestHandlerConfiguration.class, EmployeeRestHandler.class, EmployeeService.class})
public class EmployeeRestHandlerTest extends EmployeeRestBaseTest {

    @BeforeAll
    static void before() {
        log.info("Running tests for handler-style-endpoint");
    }

    EmployeeRestHandlerTest(@Autowired WebTestClient client) {
        super(client);
    }
}
