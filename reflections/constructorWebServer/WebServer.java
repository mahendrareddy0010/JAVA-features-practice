package reflections.constructorWebServer;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpServer;

public class WebServer {
    public void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create(ServerConfig.getInstance().getServerAddress(), 0);

        httpServer.createContext("/greetings").setHandler(exchange -> {
            String responseMessage = ServerConfig.getInstance().getWelcomeMessage();

            exchange.sendResponseHeaders(200, responseMessage.length());

            try (OutputStream responseBody = exchange.getResponseBody();) {
                responseBody.write(responseMessage.getBytes());
                responseBody.flush();
            }
        });

        System.out.println(String.format("Starting server at %s:%s", httpServer.getAddress().getHostName(),
                httpServer.getAddress().getPort()));
        httpServer.start();
    }
}
