package backend.jdbc.persistance;

import backend.jdbc.service.dbms.DatabaseConnector;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    
    Optional<T> getById(long id);
    List<T> getByName(String name);
    List<T> getAll();
    List<Long> save(T t);
    void update(T t, String... params);
    void delete(T t);
    
    public static void printSQLException(SQLException e) {
        System.err.println(e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error code: " + e.getErrorCode());

        Throwable t = e.getCause();
        while (t != null) {
            System.err.println("Cause: " + t);
            t = t.getCause();
        }
    }
    
    public static void dropDatabase() throws IOException, SQLException {
        DatabaseConnector connector = new DatabaseConnector();
        try (Connection connection = connector.getConnection()) {
            String query = "DROP DATABASE " + connector.getDatabaseName();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
    }
    
}
