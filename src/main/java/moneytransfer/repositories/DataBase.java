package moneytransfer.repositories;

import moneytransfer.ApplicationProperties;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.util.Properties;

public class DataBase {
//
//    public static DataSource getDataSource() {
//        Properties properties = ApplicationProperties.get();
//        JdbcDataSource ds = new JdbcDataSource();
//        ds.setURL(properties.getProperty("db.url"));
//        ds.setUser(properties.getProperty("db.login"));
//        ds.setPassword(properties.getProperty("db.password"));
//        return ds;
//    }
//
    public static DataSource getDataSource() {
        Properties properties = ApplicationProperties.get();
        JdbcConnectionPool pool =  JdbcConnectionPool.create(
                properties.getProperty("db.url"), properties.getProperty("db.login"), properties.getProperty("db.password"));
        return pool;
    }
}
