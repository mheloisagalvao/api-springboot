package mypackage;

import org.springframework.stereotype.Component;

@Component
public class MySimpleBeanImpl implements MySimpleBean {

	private static int nextId;
	
	private int id = nextId++;
	
	@Override
	public void doThis() {
		System.out.println("Hello from doThis().");
	}

	@Override
	public void doThat() {
		System.out.println("Hello from doThat().");
	}

	@Override
	public void someOtherOp1() {
		System.out.println("Hello from someOtherOp1().");
	}

	@Override
	public void someOtherOp2() {
		System.out.println("Hello from someOtherOp2().");
	}

	@Override
	public void someOtherOp3() {
		System.out.println("Hello from someOtherOp3().");
	}
	
	public String toString() {
		return String.format("MySimpleBeanImpl (id %d)", id);
	}
}
