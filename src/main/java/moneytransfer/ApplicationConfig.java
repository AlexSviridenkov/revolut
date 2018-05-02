package moneytransfer;

import moneytransfer.repositories.DataBase;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        migrate();

        packages("moneytransfer");
        register(new ModulesBinder());
    }

    private void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DataBase.getDataSource());
        flyway.migrate();
    }
}
