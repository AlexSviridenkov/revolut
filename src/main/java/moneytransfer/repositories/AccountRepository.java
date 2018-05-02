package moneytransfer.repositories;

import moneytransfer.models.Account;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountRepository {

    @SqlQuery("select * from accounts where id = ?")
    @RegisterFieldMapper(Account.class)
    Account get(int id);

    @SqlUpdate("DELETE FROM accounts where id = ?")
    void delete(int id);

    @SqlUpdate("update accounts set balance = :balance where id = :id")
    void update(@BindFields Account account);

    @SqlUpdate("insert into accounts (creationDate, balance) values (:creationDate, :balance)")
    @GetGeneratedKeys("id")
    int insert(@BindFields Account account);
}
