package reflections.constructorWebServer;

import java.net.InetSocketAddress;

public class ServerConfig {
    private static ServerConfig serverConfigInstance;
    private final InetSocketAddress serverAddress;
    private final String welcomeMessage;

    private ServerConfig(int port, String welcomeMessage) {
        this.serverAddress = new InetSocketAddress("localhost", port);
        this.welcomeMessage = welcomeMessage;
        if (serverConfigInstance == null) {
            serverConfigInstance = this;
        }
    }

    public static ServerConfig getInstance() {
        return serverConfigInstance;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }
    

}
