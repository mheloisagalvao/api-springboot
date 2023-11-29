package solution.webflux2.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@Profile("handler-style-endpoint")
public class EmployeeRestHandlerConfiguration {

    @Bean
    RouterFunction<ServerResponse> routes(EmployeeRestHandler handler) {
        return RouterFunctions.route(GET("/employees"), handler::getAll)
                .andRoute(GET("/employees/{id}"), handler::getById)
                .andRoute(POST("/employees"), handler::create)
                .andRoute(PUT("/employees/{id}"), handler::update)
                .andRoute(DELETE("/employees/{id}"), handler::delete);
    }
}
