package moneytransfer.repositories;

import org.glassfish.hk2.api.Factory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DataBaseFactory implements Factory<Jdbi> {

    @Override
    public Jdbi provide() {
        Jdbi jdbi = Jdbi.create(DataBase.getDataSource());
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Override
    public void dispose(Jdbi instance) {
    }
}
