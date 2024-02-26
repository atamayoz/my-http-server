package co.vacations.handler;

import co.vacations.router.MyRouterBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class MyHandler implements HttpHandler {
    private static volatile MyHandler instance;
    private static volatile Map<String, HttpHandler> routes;

    private MyHandler() {
        routes = new LinkedHashMap<>();
    }

    public static HttpHandler getInstance() {
        if (instance == null) {
            synchronized (MyHandler.class) {
                if (instance == null) {
                    instance = new MyHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var myRouterBuilder = MyRouterBuilder.getInstance();
        var myRouter = myRouterBuilder.addRoutes(routes).build();

        OutputStream ous = exchange.getResponseBody();
        var path = exchange.getRequestURI().getPath();

        if (!myRouter.exists(path)) {
            var response = "Not allowed path";
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(400, response.length());
            ous.write(response.getBytes(StandardCharsets.UTF_8));
            ous.close();
            return;
        }

        myRouter.execute(path, exchange);
        /*
        var response = "Hello, this is your Java HTTP server!";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");

        // this line is a must
        exchange.sendResponseHeaders(200, response.length());
        ous.write(response.getBytes(StandardCharsets.UTF_8));
        ous.close();

         */
    }

    public MyHandler addRoute(String route, HttpHandler handlerFun) {
        routes.put(route, handlerFun);
        return this;
    }
}
