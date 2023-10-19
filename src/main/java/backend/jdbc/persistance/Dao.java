package backend.jdbc.persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    
    Optional<T> get(long id);
    List<T> getAll();
    void save(T t);
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
    
}
