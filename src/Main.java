import facade.Facade;

import java.time.LocalDateTime;

public class Main {

    public static void main(String args[]) throws Exception {
        Facade.start();
        Facade.registerLogMessage(1001, null, null, LocalDateTime.now());
    }
}
