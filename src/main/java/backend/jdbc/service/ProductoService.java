package backend.jdbc.service;

import backend.jdbc.entity.Producto;
import backend.jdbc.persistance.ProductoDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductoService {
    
    private final ProductoDao productoDao;
    
    public ProductoService() throws IOException, SQLException {
        productoDao = new ProductoDao();
    }
    
    public Producto getProductoById(long codigo) {
        return productoDao.getById(codigo).orElseThrow();
    }
    
    public List<Producto> getProductoByName(String name) {
        return productoDao.getByName(name);
    }
    
    public List<Producto> getAllProductos() {
        return productoDao.getAll();
    }
    
    public long addProducto(Producto producto) {
        return productoDao.save(producto).get(0);
    }
    
    public void updateProducto(Producto producto) {
        productoDao.update(producto);
    }
    
    public void deleteProducto(Producto producto) {
        productoDao.delete(producto);
    }
    
}
