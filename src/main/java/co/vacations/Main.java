package co.vacations;

import co.vacations.handler.MyHandler;
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

        HttpHandler handlerFunCurrency = (exchange) -> {
            System.out.println("In the exchange currency: " + exchange.getRequestURI().getPath());
        };

        HttpHandler handlerFun = (exchange) -> {
            System.out.println("In the exchange: " + exchange.getRequestURI().getPath());
        };

        MyHandler myHandler = (MyHandler) MyHandler.getInstance();
        myHandler.addRoute("/concurrency", handlerFunCurrency)
                .addRoute("/another", handlerFun)
                .addRoute("/another-more", handlerFun);

        myServer.createContext("/", myHandler);
        myServer.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        myServer.start();
        System.out.println("Server started on port 8080");
    }
}