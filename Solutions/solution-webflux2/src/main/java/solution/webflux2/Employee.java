package solution.webflux2;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
public class Employee {

    @Id
	private Long id;
	
	private String name;
	private double salary;
	private String region;

	public Employee(Long id, String name, double salary, String region) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.region = region;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Employee) {
			Employee otherEmp = (Employee)other;
			result = (this.id == otherEmp.id);
		}
		return result;
	}
	
	@Override 
	public int hashCode() {
		return id.intValue();
	}
}
