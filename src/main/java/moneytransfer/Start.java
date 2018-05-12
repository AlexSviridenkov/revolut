package moneytransfer;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Start {
    public static void main(String[] args) throws IOException {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(90).build();
        ApplicationConfig config = new ApplicationConfig();

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

        System.out.println("Press enter to stop the server...");
        System.in.read();
    }
}
