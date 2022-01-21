package org.kvpbldsck.repository;

import com.google.inject.Inject;
import org.kvpbldsck.models.UserAddress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class PostgresUserAddressRepository implements UserAddressRepository {

    private final DbConnectionEstablisher connectionEstablisher;

    @Inject
    public PostgresUserAddressRepository(DbConnectionEstablisher settings) {
        this.connectionEstablisher = settings;
    }

    @Override
    public int create(UserAddress userAddress) {
        try (PreparedStatement statement = connectionEstablisher.getConnection()
                .prepareStatement("INSERT INTO User_address(Last_name, First_name, Address, Phone) VALUES (?, ?, ?, ?)")){

            statement.setString(1, userAddress.lastName());
            statement.setString(2, userAddress.firstName());
            statement.setString(3, userAddress.address());
            statement.setString(4, userAddress.phone());

            return statement.executeUpdate();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return 0;
        }
    }

    @Override
    public Optional<UserAddress> read(int userId) {
        try (PreparedStatement statement = connectionEstablisher.getConnection()
                .prepareStatement("SELECT Id, Last_name, First_name, Address, Phone FROM User_address WHERE Id = ?")){

            statement.setInt(1, userId);

            var resultSet = statement.executeQuery();
            JdbcUtils.getWarningsFromResultSet(resultSet);

            return resultSet.next()
                    ? Optional.of(parseUser(resultSet))
                    : Optional.empty();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return Optional.empty();
        }
    }

    @Override
    public List<UserAddress> read() {
        try (Statement statement = connectionEstablisher.getConnection().createStatement()){

            var resultSet = statement.executeQuery("SELECT Id, Last_name, First_name, Address, Phone FROM User_address");
            JdbcUtils.getWarningsFromResultSet(resultSet);

            List<UserAddress> userAddresses = new LinkedList<>();

            while (resultSet.next()) {
                userAddresses.add(parseUser(resultSet));
            }

            return userAddresses;

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return List.of();
        }
    }

    @Override
    public int update(UserAddress userAddress) {
        try (PreparedStatement statement = connectionEstablisher.getConnection()
                .prepareStatement("UPDATE User_address SET Last_name = ?, First_name = ?, Address = ?, Phone = ? WHERE Id = ?")){

            statement.setString(1, userAddress.lastName());
            statement.setString(2, userAddress.firstName());
            statement.setString(3, userAddress.address());
            statement.setString(4, userAddress.phone());
            statement.setInt(5, userAddress.id());

            return statement.executeUpdate();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return 0;
        }
    }

    @Override
    public int delete(int userId) {
        try (PreparedStatement statement = connectionEstablisher.getConnection()
                .prepareStatement("DELETE FROM User_address WHERE Id = ?")){

            statement.setInt(1, userId);

            return statement.executeUpdate();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            return 0;
        }
    }

    private UserAddress parseUser(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("Id");
        var lastName = resultSet.getString("Last_name");
        var firstName = resultSet.getString("First_name");
        var address = resultSet.getString("Address");
        var phone = resultSet.getString("Phone");

        return new UserAddress(id, lastName, firstName, address, phone);
    }
}
