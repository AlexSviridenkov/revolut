package moneytransfer;

import moneytransfer.repositories.DataSourceFactory;
import moneytransfer.rest.GenericExceptionMapper;
import moneytransfer.services.AccountService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.ws.rs.ext.ExceptionMapper;

public class ModulesBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountService.class);
        bindFactory(DataSourceFactory.class).to(DataSource.class).in(Singleton.class);

        bind(new GenericExceptionMapper()).to(ExceptionMapper.class);
    }
}