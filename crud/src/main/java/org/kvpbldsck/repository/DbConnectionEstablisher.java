package org.kvpbldsck.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public final class DbConnectionEstablisher {

    private static final String PROPERTIES_FILE_PATH = "db.properties";
    private static final String CONNECTION_STRING_PARAM = "connectionString";
    private static final List<String> OBLIGATORY_PARAMS = List.of(CONNECTION_STRING_PARAM);

    private final Connection connection;

    private DbConnectionEstablisher(Connection connectionString) {
        this.connection = connectionString;
    }

    public static DbConnectionEstablisher Create() {
        Properties dbProperties = readDbPropertiesFile();

        ensureObligatoryParamsAttended(dbProperties);

        var connectionString = dbProperties.getProperty(CONNECTION_STRING_PARAM);
        Connection connection = connectToDatabase(connectionString);

        return new DbConnectionEstablisher(connection);
    }

    private static Connection connectToDatabase(String connectionString) {
        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
            exitProgram("Can't connect to database%n");
        }
        return null;
    }

    private static void ensureObligatoryParamsAttended(Properties dbProperties) {
        var missedObligatoryParams = OBLIGATORY_PARAMS
                .stream()
                .filter(p -> !dbProperties.containsKey(p))
                .collect(Collectors.toUnmodifiableSet());

        if (!missedObligatoryParams.isEmpty()) {
            exitProgram(
                    "Miss some obligatory parameters in %s: %s%n",
                    PROPERTIES_FILE_PATH,
                    String.join("", missedObligatoryParams));
        }
    }

    private static Properties readDbPropertiesFile() {
        try {
            var propertiesFile = DbConnectionEstablisher.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);
            Properties dbProperties = new Properties();
            dbProperties.load(propertiesFile);
            return dbProperties;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exitProgram("Can't find db properties file: %s%n", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            exitProgram("Can't read db properties file: %s%n", e.getMessage());
        }

        return null;
    }

    private static void exitProgram(String errorMessageFormat, Object... args) {
        System.err.printf(errorMessageFormat, args);
        System.exit(1);
    }

    public Connection getConnection() {
        return connection;
    }
}
