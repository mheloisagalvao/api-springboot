package solution.webflux2;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

    @Query("SELECT COUNT(*) FROM EMPLOYEES WHERE REGION = :region")
    int countByRegion(String region);

    @Query("SELECT * FROM EMPLOYEES WHERE REGION = :region")
    List<Employee> findByRegion(String region);
}