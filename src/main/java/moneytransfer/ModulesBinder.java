package moneytransfer;

import moneytransfer.repositories.DataBaseFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;

public class ModulesBinder extends AbstractBinder {

    @Override
    protected void configure() {
//        bind(MyService.class).to(MyService.class);
        bindFactory(DataBaseFactory.class).to(Jdbi.class).in(Singleton.class);
    }
}