package co.vacations.router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MyRouter {
    protected Map<String, HttpHandler> routes;

    protected MyRouter() {
        this.routes = new HashMap<>();
    }

    public Map<String, HttpHandler> getRoutes() {
        return routes;
    }

    public boolean exists(String path) {
        return routes.get(path) != null;
    }

    public void execute(String route, HttpExchange exchange) throws IOException {
        this.routes.get(route).handle(exchange);
    }
}
