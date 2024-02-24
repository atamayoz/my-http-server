package co.vacations.router;

import com.sun.net.httpserver.HttpHandler;

import java.util.Map;

public class MyRouterBuilder {
    private static MyRouterBuilder instance;
    private final MyRouter router = new MyRouter();

    private MyRouterBuilder(){}

    private MyRouterBuilder addRoute(String route, HttpHandler handlerFun) {
        router.routes.put(route, handlerFun);
        return this;
    }

    public static MyRouterBuilder getInstance() {
        if (instance == null) {
            instance = new MyRouterBuilder();
        }
        return instance;
    }

    public MyRouterBuilder addRoutes(Map<String, HttpHandler> routes) {
        router.routes.putAll(routes);
        return this;
    }

    public MyRouter build() {
        return router;
    }
}
