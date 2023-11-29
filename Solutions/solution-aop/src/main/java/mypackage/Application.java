package mypackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        MySimpleBean bean = context.getBean(MySimpleBean.class);
        bean.doThis();
        bean.doThat();
        bean.someOtherOp1();
        bean.someOtherOp2();
        bean.someOtherOp3();
    }
}
