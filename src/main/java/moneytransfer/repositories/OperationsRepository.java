package moneytransfer.repositories;

import moneytransfer.models.Operation;
import org.jdbi.v3.sqlobject.customizer.BindFields;

import java.sql.*;

public class OperationsRepository {

    private Connection connection;

    public OperationsRepository(Connection connection) {
        this.connection = connection;
    }

    public int insert(@BindFields Operation operation) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO operations (transferDate, accountIdFrom, accountIdTo, amount)" +
                "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setTimestamp(1, new Timestamp(operation.transferDate.getTime()));
            statement.setInt(2, operation.accountIdFrom);
            statement.setInt(3, operation.accountIdTo);
            statement.setBigDecimal(4, operation.amount);

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt("id");
                } else {
                    throw new SQLException("Failed save operation, no ID obtained.");
                }
            }
        }
    }
}
