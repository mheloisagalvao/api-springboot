package mypackage;

import org.aspectj.lang.annotation.Pointcut;

public final class MyPointcuts {

	private MyPointcuts() {
	}

	@Pointcut("execution(* mypackage..*.do*(..))")
	public void callToDoMethod() {
	}

	@Pointcut("execution(* mypackage..*(..)) && !execution(* mypackage..*.do*(..))")
	public void callToNonDoMethod() {
	}
}
