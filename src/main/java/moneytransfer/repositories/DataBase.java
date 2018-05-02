package moneytransfer.repositories;

import moneytransfer.ApplicationProperties;
import org.h2.jdbcx.JdbcDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

public class DataBase {

    public static DataSource getDataSource() {
        Properties properties = ApplicationProperties.get();
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(properties.getProperty("db.url"));
        ds.setUser(properties.getProperty("db.login"));
        ds.setPassword(properties.getProperty("db.password"));
        return ds;
    }
}
