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
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                long cod = resultSet.getLong("codigo");
                String nombre = resultSet.getString("nombre");
                fabricante = new Fabricante(cod, nombre);
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
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                long codigo = resultSet.getLong("codigo");
                String nombre = resultSet.getString("nombre");
                Fabricante fabricante = new Fabricante(codigo, nombre);
                fabricantes.add(fabricante);
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return fabricantes;
    }

    @Override
    public void save(Fabricante t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Fabricante t, String... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Fabricante t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
