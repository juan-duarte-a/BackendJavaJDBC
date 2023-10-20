package backend.jdbc.service.dbms;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnector {

    private final Properties dbmsProperties;
    private MysqlConnectionPoolDataSource poolDataSource;
    private static final String SQL_SCRIPT = "/sql_scripts/tienda.sql";
    private static final String PROPERTIES_FILE = "/properties/dbms.properties";
    private static final PropertiesLoader.TYPE PROPERTIES_TYPE = PropertiesLoader.TYPE.PROPERTIES;

    public DatabaseConnector() 
            throws IOException, SQLException {
        dbmsProperties = PropertiesLoader.loadProperties(PROPERTIES_FILE, PROPERTIES_TYPE);
        checkDatabase();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = connect();
        connection.setCatalog(dbmsProperties.getProperty("database"));
        return connection;
    }
    
    private Connection connect() throws SQLException {
        Connection connection = null;
        
        String connectionUrl = "jdbc:"
                + dbmsProperties.getProperty("dbms") + "://"
                + dbmsProperties.getProperty("server") + ":"
                + dbmsProperties.getProperty("port") + "/";
        if (dbmsProperties.get("connection-type").equals("standard")) {
            connection = standardConnection(connectionUrl);
        } else if (dbmsProperties.get("connection-type").equals("pooled")){
            connection = pooledConnection(connectionUrl);
        }
        
        return connection;
    }

    private Connection standardConnection(String connectionUrl) throws SQLException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connectionUrl, dbmsProperties);
        } catch (SQLException e) {
            System.err.println("Error connecting to database.");
            throw e;
        }

        return connection;
    }

    private Connection pooledConnection(String connectionUrl) throws SQLException {
        if (poolDataSource == null) {
            poolDataSource = new MysqlConnectionPoolDataSource();
            poolDataSource.setURL(connectionUrl);
            poolDataSource.setUser(dbmsProperties.getProperty("user"));
            poolDataSource.setPassword(dbmsProperties.getProperty("password"));
        }

        Connection connection;
        try {
            connection = poolDataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to database.");
            throw e;
        }

        return connection;
    }
    
    private void createDatabase(Connection connection) throws IOException, SQLException {
        Statement statement = connection.createStatement();
        String[] queries = SQLScriptParser.parseSQL(SQL_SCRIPT);
        
        for (String query : queries) {
            if (!query.isBlank()) {
                statement.addBatch(query);
            }
        }
        
        int[] results = statement.executeBatch();
        int numberOfStatements = results.length;
        
        for (int i = 0; i < numberOfStatements; i++) {
            if (results[i] == Statement.EXECUTE_FAILED) {
                System.err.println("Statement execution failed in statement number " + i);
            }
        }
    }
    
    private void checkDatabase() throws IOException ,SQLException {
        Connection connection = connect();
        
        try {
            connection.setCatalog(dbmsProperties.getProperty("database"));
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) {
                createDatabase(connection);
            } else {
                throw e;
            }
        }
    }
    
    public String getDatabaseName() {
        return dbmsProperties.getProperty("database");
    }

}
