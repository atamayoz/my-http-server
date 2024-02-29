package co.vacations.service;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;

public class MyConsumer extends Observable implements Runnable {
    private final BlockingQueue<Integer> sharedQueue;
    private int itemTaken;
    private final MyEventListener listener;

    public MyConsumer(BlockingQueue<Integer> sharedQueue, MyEventListener listener) {
        this.sharedQueue = sharedQueue;
        this.listener = listener;
    }

    @Override
    public void run() {

        var myEvenSource = new MyEventSource();
        myEvenSource.addEventListener(listener);

        while (true) {
            try {
                itemTaken = this.sharedQueue.take();
                var response = "Item taken from queue: " + itemTaken;
                myEvenSource.fireEvent(response);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
