package co.vacations.handler;

import co.vacations.service.MyConsumer;
import co.vacations.service.MyEventListener;
import co.vacations.service.MyProducer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class MyProducerConsumerHandler implements HttpHandler, MyEventListener {

    private static final String FILE_PATH = "/tmp/queue.txt";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        BlockingQueue<Integer> sharedQueue = new ArrayBlockingQueue<>(10);

        Thread producerThread = new Thread(new MyProducer(sharedQueue, 10));
        Thread consumerThread = new Thread(new MyConsumer(sharedQueue, this));

        producerThread.start();
        consumerThread.start();
    }

    @Override
    public void onEventOccurred(String event) {
        System.out.println("I received the event Yujuuu!!! : " + event);
        File file = new File(FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writeToFile(writer, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(BufferedWriter writer, String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
