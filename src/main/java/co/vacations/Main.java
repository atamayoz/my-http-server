package co.vacations;

import co.vacations.handler.MyHandler;
import co.vacations.handler.MyThreadPoolHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        var myServer = HttpServer.create(new InetSocketAddress(8080), 0);

        // This code here is to demonstrate that you can pass lambda or you can create an external class
        // implementing the HttpHandler.
        // Each route has its own handler
        HttpHandler handlerFunCurrency = (exchange) -> {
            System.out.println("In the exchange currency: " + exchange.getRequestURI().getPath());
        };

        HttpHandler handlerFun = (exchange) -> {
            System.out.println("In the exchange: " + exchange.getRequestURI().getPath());
        };

        MyHandler myHandler = (MyHandler) MyHandler.getInstance();
        myHandler.addRoute("/concurrency", handlerFunCurrency)
                .addRoute("/concurrency/thread-pool", new MyThreadPoolHandler())
                .addRoute("/concurrency/producer-consumer", handlerFun);

        myServer.createContext("/", myHandler);
        myServer.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        myServer.start();
        System.out.println("Server started on port 8080");
    }
}