package mypackage;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

	@Before("mypackage.MyPointcuts.callToDoMethod()")
	public void logCallToDoMethod(JoinPoint jp) {
		System.out.printf("***Before call to DO method %s on target %s\n",
				          jp.getSignature().getName(),
				          jp.getTarget());
	}

	@Before("mypackage.MyPointcuts.callToNonDoMethod()")
	public void logCallToNonDoMethod(JoinPoint jp) {
		System.out.printf("***Before call to non-DO method %s on target %s\n",
		          jp.getSignature().getName(),
		          jp.getTarget());
	}
}
