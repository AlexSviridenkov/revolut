package moneytransfer.repositories;

import moneytransfer.models.Operation;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface OperationsRepository {

    @SqlUpdate("insert into operations (transferDate, accountIdFrom, accountIdTo, amount) " +
            "values (:transferDate, :accountIdFrom, :accountIdTo, :amount)")
    @GetGeneratedKeys("id")
    int insert(@BindFields Operation operation);
}
