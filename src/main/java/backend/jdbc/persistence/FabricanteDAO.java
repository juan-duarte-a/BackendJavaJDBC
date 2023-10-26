package backend.jdbc.persistence;

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

public class FabricanteDAO implements DAO<Fabricante> {
    
    private final DatabaseConnector dbConnector;
    
    public FabricanteDAO() throws IOException, SQLException {
        dbConnector = new DatabaseConnector();
    }
    
    @Override
    public Optional<Fabricante> getById(long codigo) {
        List<Fabricante> fabricante = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM fabricante WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, codigo);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                fabricante = mapToFabricante(resultSet);
            }
        } catch (SQLException e) {
            DAO.printSQLException(e);
        }
        
        return Optional.ofNullable(fabricante.isEmpty() ? null : fabricante.get(0));
    }
    
    @Override
    public List<Fabricante> getByName(String nombre) {
        List<Fabricante> fabricantes = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM fabricante WHERE nombre LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombre);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                fabricantes = mapToFabricante(resultSet);
            }
        } catch (SQLException e) {
            DAO.printSQLException(e);
        }
        
        return fabricantes;
    }

    @Override
    public List<Fabricante> getAll() {
        List<Fabricante> fabricantes = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM fabricante";
            Statement statement = connection.createStatement();
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                fabricantes = mapToFabricante(resultSet);
            }
        } catch (SQLException e) {
            DAO.printSQLException(e);
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
            DAO.printSQLException(e);
        }
        
        return keys;
    }

    @Override
    public void update(Fabricante fabricante, String... params) {
        try (Connection connection = dbConnector.getConnection()) {
            String query = "UPDATE fabricante SET nombre = ? WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, fabricante.getNombre());
            statement.setLong(2, fabricante.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            DAO.printSQLException(e);
        }
    }

    @Override
    public void delete(Fabricante fabricante) {
        try (Connection connection = dbConnector.getConnection()) {
            String query = "DELETE FROM fabricante WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, fabricante.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            DAO.printSQLException(e);
        }
    }
    
    private List<Fabricante> mapToFabricante(ResultSet resultSet) throws SQLException {
        List<Fabricante> fabricantes = new ArrayList<>();
        
        while (resultSet.next()) {
            long cod = resultSet.getLong("codigo");
            String nombre = resultSet.getString("nombre");
            Fabricante fabricante = new Fabricante(cod, nombre);
            fabricantes.add(fabricante);
        }
        
        return fabricantes;
    }
    
}
