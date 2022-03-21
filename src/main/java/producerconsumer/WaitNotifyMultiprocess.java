package producerconsumer;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class WaitNotifyMultiprocess {

    static Logger logger = Logger.getLogger(WaitNotifyMultiprocess.class);
    static Queue<Integer> tasks = new LinkedList<Integer>();
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerMultiprocess producer = new ProducerConsumerMultiprocess(tasks, lock, condition);

        Thread p1 = new Thread(() -> producerThread(producer));
        Thread p2 = new Thread(() -> producerThread(producer));
        Thread p3 = new Thread(() -> producerThread(producer));

        Thread k1 = new Thread(() -> consumerThread(producer));
        Thread k2 = new Thread(() -> consumerThread(producer));

        p1.start();
        p2.start();
        p3.start();
        k1.start();
        k2.start();

        p1.join();
        p2.join();
        p3.join();
        k1.join();
        k2.join();

    }

    private static void consumerThread(ProducerConsumerMultiprocess producer) {
        try {
            boolean output = true;
            while (output) {
                output = producer.consume();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void producerThread(ProducerConsumerMultiprocess producer) {
        IntStream.rangeClosed(1, 100).forEach(num -> {
                Random r = new Random();
                try {
                    Thread.sleep(Math.abs(r.nextInt() % 2000));
                    producer.produce(r.nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
    }
}

class ProducerConsumerMultiprocess {

    Logger logger = Logger.getLogger(WaitNotifyMultiprocess.class);

    Queue<Integer> queue;
    Lock lock;
    Condition condition;

    public ProducerConsumerMultiprocess(Queue<Integer> queue, ReentrantLock lock, Condition condition) {
        this.queue = queue;
        this.lock = lock;
        this.condition = condition;
    }

    public void produce(Integer num) throws InterruptedException {
        lock.lock();
        logger.info("Start produce " + num);
        queue.add(num);
        condition.signalAll();
        logger.info("Finish produce " + num);
        lock.unlock();
    }

    public boolean consume() throws InterruptedException {
        logger.info("Start consume");
        lock.lock();
        boolean goOn = queue.size() == 0 && Thread.activeCount()>4;
        while (goOn) {
            condition.await();
            goOn = queue.size() == 0 && Thread.activeCount()>4;
        }
        if(queue.size()>0) {
            Integer num = queue.poll();
            logger.info("Finish consume " + num + " " + Thread.activeCount());
            lock.unlock();
            return true;
        }
        else if(Thread.activeCount()<5){
            logger.info("Finish consume END "+Thread.activeCount());
            lock.unlock();
            return false;
        }
        return true;
    }
}
