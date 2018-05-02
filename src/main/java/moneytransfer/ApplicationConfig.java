package moneytransfer;

import moneytransfer.repositories.DataBase;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.ResourceConfig;
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
    }

    private void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DataBase.getDataSource());
        flyway.migrate();
    }
}
