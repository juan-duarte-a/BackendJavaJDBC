package backend.jdbc.service;

import backend.jdbc.entity.Producto;
import backend.jdbc.persistance.ProductoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductoService {
    
    private final ProductoDAO productoDao;
    
    public ProductoService() throws IOException, SQLException {
        productoDao = new ProductoDAO();
    }
    
    public Producto getProductoById(long codigo) {
        return productoDao.getById(codigo).orElseThrow();
    }
    
    public List<Producto> getProductoByName(String name) {
        return productoDao.getByName(name);
    }
    
    public List<Producto> getProductoByNameContains(String name) {
        return productoDao.getByName("%" + name + "%");
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
    
    public List<Producto> getProductosPriceBetween(BigDecimal min, BigDecimal max) {
        return productoDao.getAll().stream()
                .filter(p -> p.getPrecio().compareTo(min) >= 0
                        && p.getPrecio().compareTo(max) <= 0)
                .toList();
    }
    
    public Producto getCheapestProducto() {
        return productoDao.getAll().stream()
                .sorted((p1, p2) -> p1.getPrecio().compareTo(p2.getPrecio()))
                .findFirst()
                .get();
    }
    
}
