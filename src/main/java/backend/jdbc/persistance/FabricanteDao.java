package backend.jdbc.persistance;

import backend.jdbc.entity.Fabricante;
import backend.jdbc.service.dbms.DatabaseConnector;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FabricanteDao implements Dao<Fabricante> {
    
    private final DatabaseConnector dbConnector;
    
    public FabricanteDao() throws IOException, SQLException {
        dbConnector = new DatabaseConnector();
    }
    
    @Override
    public Optional<Fabricante> get(long codigo) {
        Fabricante fabricante = null;
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM fabricante WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, codigo);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long cod = resultSet.getLong("codigo");
                    String nombre = resultSet.getString("nombre");
                    fabricante = new Fabricante(cod, nombre);
                }
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return Optional.ofNullable(fabricante);
    }

    @Override
    public List<Fabricante> getAll() {
        List<Fabricante> fabricantes = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM fabricante";
            Statement statement = connection.createStatement();
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    long codigo = resultSet.getLong("codigo");
                    String nombre = resultSet.getString("nombre");
                    Fabricante fabricante = new Fabricante(codigo, nombre);
                    fabricantes.add(fabricante);
                }
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return fabricantes;
    }

    @Override
    public List<Long> save(Fabricante fabricante) {
        List<Long> keys = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "INSERT INTO fabricante (nombre) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, fabricante.getNombre());
            int result = statement.executeUpdate();
            
            if (result > 0) {
                try (ResultSet newKeys = statement.getGeneratedKeys()) {
                    while (newKeys.next()) {
                        keys.add(newKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return keys;
    }

    @Override
    public void update(Fabricante fabricante, String... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Fabricante fabricante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
