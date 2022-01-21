package org.kvpbldsck.repository;

import java.sql.*;

public final class JdbcUtils {
    public static void getWarningsFromResultSet(ResultSet rs) {
        try {
            printWarnings(rs.getWarnings());
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void getWarningsFromStatement(Statement stmt) {
        try {
            printWarnings(stmt.getWarnings());
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void printWarnings(SQLWarning warning) {

        if (warning != null) {
            System.out.println("\n---Warning---\n");

            while (warning != null) {
                System.out.println("Message: " + warning.getMessage());
                System.out.println("SQLState: " + warning.getSQLState());
                System.out.print("Vendor error code: ");
                System.out.println(warning.getErrorCode());
                System.out.println("");
                warning = warning.getNextWarning();
            }
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException sqlException
                    && !ignoreSQLException(sqlException.getSQLState())) {

                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());

                Throwable t = ex.getCause();
                while (t != null) {
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;

        // 42Y55: Table already exists in schema
        return sqlState.equalsIgnoreCase("42Y55");
    }
}
