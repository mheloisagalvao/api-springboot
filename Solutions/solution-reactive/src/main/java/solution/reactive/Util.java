package solution.reactive;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Util {
    public static void doSomethingUseful(int repeats) {
        for (int i = 0; i < repeats; i++) {
            log.info(".......Doing something useful #" + i);
            try { Thread.sleep(100); } catch (InterruptedException ex) {}
        }
    }
}
