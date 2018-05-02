package moneytransfer.services;

import moneytransfer.models.Account;
import moneytransfer.models.Operation;
import moneytransfer.repositories.AccountRepository;
import moneytransfer.repositories.DataBase;
import moneytransfer.repositories.OperationsRepository;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

public class AccountService {

    @Inject
    public Jdbi jdbi;

    public Account create() {
        Account account = new Account();
        account.balance = BigDecimal.ZERO;
        account.creationDate = now();
        account.id = accountRepository().insert(account);
        return account;
    }

    public Account getAccount(int id) {
        return accountRepository().get(id);
    }

    public Account putMoney(int id, BigDecimal amount) {
        return jdbi.inTransaction((conn) -> {
            AccountRepository accountRepository = accountRepository(conn);
            Account account = accountRepository.get(id);
            account.balance = account.balance.add(amount);
            accountRepository.update(account);

            Operation operation = new Operation();
            operation.accountIdTo = id;
            operation.amount = amount;
            operation.transferDate = now();
            operationsRepository(conn).insert(operation);

            return account;
        });
    }

    public void delete(int id) {
        accountRepository().delete(id);
    }

    public void transfer(int from, int to, BigDecimal amount) throws NotEnoughMoneyException {
        jdbi.useTransaction((conn) -> {
            AccountRepository accountRepository = accountRepository(conn);
            Account fromAccount = accountRepository.get(from);
            Account toAccount = accountRepository.get(to);


            if (fromAccount.balance.compareTo(amount) < 0) {
                throw new NotEnoughMoneyException();
            }

            fromAccount.balance = fromAccount.balance.subtract(amount);
            accountRepository.update(fromAccount);

            toAccount.balance = fromAccount.balance.add(amount);
            accountRepository.update(fromAccount);

            Operation operation = new Operation();
            operation.accountIdFrom = from;
            operation.accountIdTo = to;
            operation.amount = amount;
            operation.transferDate = now();
            operationsRepository(conn).insert(operation);
        });
    }

    private AccountRepository accountRepository() {
        return jdbi.onDemand(AccountRepository.class);
    }

    private AccountRepository accountRepository(Handle conn) {
        return conn.attach(AccountRepository.class);
    }

    private OperationsRepository operationsRepository(Handle conn) {
        return conn.attach(OperationsRepository.class);
    }

    private static Date now() {
        return new Date();
    }
}
