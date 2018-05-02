package moneytransfer.repositories;

import moneytransfer.models.Operation;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface OperationsRepository {

    @SqlUpdate("insert into operations (transferDate, accountIdFrom, accountIdTo, balance) " +
            "values (:transferDate, :accountIdFrom, :accountIdTo, :balance)")
    @GetGeneratedKeys("id")
    int insert(Operation operation);

    Operation getForAccount(int id);
}
