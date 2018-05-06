package moneytransfer.repositories;

import org.glassfish.hk2.api.Factory;

import javax.sql.DataSource;

public class DataSourceFactory implements Factory<DataSource> {

    @Override
    public DataSource provide() {
        return DataBase.getDataSource();
    }

    @Override
    public void dispose(DataSource instance) {
    }
}
