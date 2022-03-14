package streams;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StreamsExample {

    static Logger log = Logger.getLogger(StreamsExample.class);

    public static void main(String [ ] args) {

        // Integer stream
        IntStream.rangeClosed(1, 10).forEach(num -> log.info(num));
        // ->12345678910
        IntStream.range(1, 10).forEach(num -> log.info(num));
        // ->123456789

        // Collection to stream
        List<Integer> lista = new ArrayList<>();
        IntStream.range(1, 10).forEach(num -> lista.add(num));
        lista.stream().forEach(num -> log.info(num));

//        whatIsIt = lista.stream();
    }
}