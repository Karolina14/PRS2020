package join;

import org.apache.log4j.Logger;

public class Join {

    public static Logger log = Logger.getLogger(Join.class);

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                log.info(Thread.currentThread().getName() + " " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("Thread ended:" + Thread.currentThread().getName());
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                log.info(Thread.currentThread().getName() + " " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("Thread ended:" + Thread.currentThread().getName());
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; ++i) {
                log.info(Thread.currentThread().getName() + " " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("Thread ended:" + Thread.currentThread().getName());
        });

        thread.start();

        thread.join();

        thread2.start();
        thread3.start();
        thread2.join();
        thread3.join();


        log.info("Main ended:" + Thread.currentThread().getName());
    }
}