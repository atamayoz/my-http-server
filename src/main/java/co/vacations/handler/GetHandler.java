package co.vacations.handler;

import com.sun.net.httpserver.HttpExchange;

public class GetHandler {
    public void handle(HttpExchange exchange) {
        var uri = exchange.getRequestURI();
        var query = uri.getQuery();
        System.out.println("Query: " + query);
        var queryParams = exchange.getRequestHeaders().get("query");
        System.out.println("Query params: " + queryParams);
    }
}
