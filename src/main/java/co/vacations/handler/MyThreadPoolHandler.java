package co.vacations.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolHandler implements HttpHandler {

    private static final String FILE_PATH = "/tmp/processed.txt";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        File file = new File(FILE_PATH);
        try (ExecutorService executorService = Executors.newFixedThreadPool(3);
             BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writeToFile(executorService, writer);

            executorService.shutdown();
            if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                OutputStream ous = exchange.getResponseBody();
                exchange.getResponseHeaders().set("Content-Type", "text/plain");

                var response = "Tasks did not complete in the specified time.";
                // this line is a must
                exchange.sendResponseHeaders(500, response.length());
                ous.write(response.getBytes(StandardCharsets.UTF_8));
                ous.close();
                return;
            }
            Thread.sleep(20);
            System.out.println("All tasks completed. File written: " + file.length());

            OutputStream ous = exchange.getResponseBody();
            exchange.getResponseHeaders().set("Content-Type", "application/zip");
            exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=processed.txt");
            exchange.sendResponseHeaders(200, file.length());

            Files.copy(file.toPath(), ous);
            ous.close();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            System.err.println("****** " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void writeToFile(ExecutorService executorService, BufferedWriter writer) {
        for (int i = 1; i <= 20; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                String message = "This is the task id " + taskId + " running on thread " + Thread.currentThread().getName();
                try {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
