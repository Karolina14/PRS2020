package debug;

import org.apache.log4j.Logger;

public class DebugExample {

    static Logger log = Logger.getLogger(DebugExample.class);

    public static void main(String [ ] args) {

        int silnia = 1;

        for(int i = 1;i<15;i++ ) {
            silnia = i*silnia;
        }

        log.info("Silnia 15 : " + silnia);
    }
}