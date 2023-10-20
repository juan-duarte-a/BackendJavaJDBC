package backend.jdbc.persistance;

import backend.jdbc.entity.Producto;
import backend.jdbc.service.dbms.DatabaseConnector;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDao implements Dao<Producto>{
    
    private final DatabaseConnector dbConnector;
    
    public ProductoDao() throws IOException, SQLException {
        dbConnector = new DatabaseConnector();
    }
    
    @Override
    public Optional<Producto> getById(long codigo) {
        List<Producto> producto = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM producto WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, codigo);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                producto = mapToProducto(resultSet);
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return Optional.ofNullable(producto.isEmpty() ? null : producto.get(0));
    }
    
    @Override
    public List<Producto> getByName(String nombre) {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM producto WHERE nombre LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombre);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                productos = mapToProducto(resultSet);
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> getAll() {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "SELECT * FROM producto";
            Statement statement = connection.createStatement();
            
            try (ResultSet resultSet = statement.executeQuery(query)) {
                productos = mapToProducto(resultSet);
            }
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
        
        return productos;
    }

    @Override
    public List<Long> save(Producto producto) {
        List<Long> keys = new ArrayList<>();
        
        try (Connection connection = dbConnector.getConnection()) {
            String query = "INSERT INTO producto "
                    + "(nombre, precio, codigo_fabricante) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getPrecio().toPlainString());
            statement.setLong(3, producto.getCodigoFabricante());
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
    public void update(Producto producto, String... params) {
        try (Connection connection = dbConnector.getConnection()) {
            String query = "UPDATE producto "
                    + "SET nombre = ?, precio = ?, codigo_fabricante = ? "
                    + "WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getPrecio().toPlainString());
            statement.setLong(3, producto.getCodigoFabricante());
            statement.setLong(4, producto.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
    }

    @Override
    public void delete(Producto producto) {
        try (Connection connection = dbConnector.getConnection()) {
            String query = "DELETE FROM producto WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, producto.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            Dao.printSQLException(e);
        }
    }
    
    private List<Producto> mapToProducto(ResultSet resultSet) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        
        while (resultSet.next()) {
            long codigo = resultSet.getLong("codigo");
            String nombre = resultSet.getString("nombre");
            BigDecimal precio = new BigDecimal(resultSet.getString("precio"));
            long codigoFabricante = resultSet.getLong("codigo_fabricante");
            Producto producto = new Producto(codigo, nombre, precio, codigoFabricante);
            productos.add(producto);
        }
        
        return productos;
    }
    
}
