package moneytransfer.services;

import moneytransfer.models.Account;
import moneytransfer.models.Operation;
import moneytransfer.repositories.AccountRepository;
import moneytransfer.repositories.OperationsRepository;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class AccountService {

    @Inject
    public DataSource dataSource;

    public Account create() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Account account = new Account();
            account.balance = BigDecimal.ZERO;
            account.creationDate = now();
            account.id = accountRepository(connection).insert(account);
            return account;
        }
    }

    public Account getAccount(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return accountRepository(connection).get(id);
        }
    }

    public Account putMoney(int id, BigDecimal amount) throws SQLException {
        return inTx(connection -> {
            AccountRepository accountRepository = accountRepository(connection);
            checkAccount(accountRepository, id);

            accountRepository.increaseBalance(id, amount);

            Operation operation = new Operation();
            operation.accountIdTo = id;
            operation.amount = amount;
            operation.transferDate = now();
            operationsRepository(connection).insert(operation);

            return accountRepository.get(id);
        });
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            accountRepository(connection).delete(id);
        }
    }

    public void transfer(int from, int to, BigDecimal amount) throws SQLException {
        inTx(connection -> {
            AccountRepository accountRepository = accountRepository(connection);
            checkAccount(accountRepository, from);
            checkAccount(accountRepository, to);

            if (!accountRepository.decreaseBalance(from, amount)){
                throw new NotEnoughMoneyException("Account " + from + " doesn't have enough money");
            };

            accountRepository.increaseBalance(to, amount);

            Operation operation = new Operation();
            operation.accountIdFrom = from;
            operation.accountIdTo = to;
            operation.amount = amount;
            operation.transferDate = now();
            operationsRepository(connection).insert(operation);
            return null;
        });
    }

    private void checkAccount(AccountRepository accountRepository, int id) throws SQLException {
        if (!accountRepository.checkAccount(id)) {
            throw new UnknownAccountException(id);
        }
    }

    public interface InTxFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    private <T> T inTx(InTxFunction<Connection, T> execute) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            T result = execute.apply(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    private AccountRepository accountRepository(Connection connection) {
        return new AccountRepository(connection);
    }

    private OperationsRepository operationsRepository(Connection connection) {
        return new OperationsRepository(connection);
    }

    private static Date now() {
        return new Date();
    }
}
