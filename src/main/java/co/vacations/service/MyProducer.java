package co.vacations.service;

import java.util.concurrent.BlockingQueue;

public class MyProducer implements Runnable {
    private final BlockingQueue<Integer> sharedQueue;
    private final Integer totalItems;

    public MyProducer(BlockingQueue<Integer> sharedQueue, Integer totalItems) {
        this.sharedQueue = sharedQueue;
        this.totalItems = totalItems;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalItems; i++) {
            try {
                System.out.println("Producing values to the shared queue");
                this.sharedQueue.put(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
