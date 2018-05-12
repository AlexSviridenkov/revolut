package moneytransfer.repositories;

import moneytransfer.models.Account;

import java.math.BigDecimal;
import java.sql.*;

public class AccountRepository {

    private Connection connection;

    public AccountRepository(Connection connection) {
        this.connection = connection;
    }

    public Account get(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE id = ?")) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            resultSet.next();
            Account account = new Account();
            account.id = resultSet.getInt("id");
            account.balance = resultSet.getBigDecimal("balance");
            account.creationDate = resultSet.getDate("creationDate");

            return account;
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        }
    }

    public void update(Account account) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?")) {
            statement.setBigDecimal(1, account.balance);
            statement.setInt(2, account.id);
            statement.execute();
        }
    }

    public void increaseBalance(int account, BigDecimal amount) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance + ?" +
                "WHERE id = ?")) {
            statement.setBigDecimal(1, amount);
            statement.setInt(2, account);
            statement.execute();
        }
    }

    public boolean decreaseBalance(int account, BigDecimal amount) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance - ?" +
                "WHERE id = ? AND balance >  ?")) {
            statement.setBigDecimal(1, amount);
            statement.setInt(2, account);
            statement.setBigDecimal(3, amount);
            return statement.executeUpdate() > 0;
        }
    }

    public int insert(Account account) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (creationDate, balance) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, new Timestamp(account.creationDate.getTime()));
            statement.setBigDecimal(2, account.balance);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt("id");
                } else {
                    throw new SQLException("Failed to create account, no ID obtained.");
                }
            }
        }
    }

    public boolean checkAccount(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT EXISTS(SELECT id FROM accounts WHERE id = ?)")) {
            statement.setInt(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
        }
    }
}
