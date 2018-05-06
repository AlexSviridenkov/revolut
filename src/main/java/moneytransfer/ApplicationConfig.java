package moneytransfer;

import moneytransfer.repositories.DataBase;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        migrate();

        packages("moneytransfer");
        register(new ModulesBinder());

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Logger.getLogger("").setLevel(Level.FINEST);

        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");


    }

    private void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DataBase.getDataSource());
        flyway.migrate();
    }
}
