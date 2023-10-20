package backend.jdbc;

import backend.jdbc.entity.Fabricante;
import backend.jdbc.entity.Producto;
import backend.jdbc.service.FabricanteService;
import backend.jdbc.service.ProductoService;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import backend.jdbc.persistance.DAO;

public class Main {
    
    private final FabricanteService fabricanteService;
    private final ProductoService productoService;

    public static void main(String[] args) {
        try {
            Main main = new Main();
            
            List<Fabricante> fabricantes =  main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            
            System.out.println();
            Fabricante fabricante = new Fabricante("Nvidia");
            long codigo = main.fabricanteService.addFabricante(fabricante);
            
            fabricante = main.fabricanteService.getFabricanteById(codigo);
            System.out.println(fabricante);
            
            fabricante.setNombre("Logitech");
            main.fabricanteService.updateFabricante(fabricante);
            fabricante = main.fabricanteService.getFabricanteById(codigo);
            System.out.println(fabricante);
            
            System.out.println();
            fabricantes = main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            
            System.out.println();
            main.fabricanteService.deleteFabricante(fabricante);
            fabricantes = main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            
            System.out.println();
            List<Producto> productos = main.productoService.getAllProductos();
            productos.forEach(System.out::println);

            System.out.println();
            Producto producto = new Producto(
                    "SSD SATA 1TB", 
                    new BigDecimal("250.55"), 
                    7);
            
            codigo = main.productoService.addProducto(producto);
            
            producto = main.productoService.getProductoById(codigo);
            System.out.println(producto);
            
            producto.setNombre("NVMe m.2 PCIe 2TB");
            main.productoService.updateProducto(producto);
            producto = main.productoService.getProductoById(codigo);
            System.out.println(producto);
            
            System.out.println();
            productos = main.productoService.getAllProductos();
            productos.forEach(System.out::println);
            
            System.out.println();
            main.productoService.deleteProducto(producto);
            productos = main.productoService.getAllProductos();
            productos.forEach(System.out::println);
            
            System.out.println();
            productos = main.productoService.getProductosPriceBetween(
                    new BigDecimal("120"), new BigDecimal("202"));
            productos.forEach(System.out::println);
            
            System.out.println();
            productos = main.productoService.getProductoByNameContains("portátil");
            productos.forEach(System.out::println);
            
            System.out.println();
            producto = main.productoService.getCheapestProducto();
            System.out.println(producto);
        } catch (Exception e) {
            if (e instanceof SQLException sqlException) {
                DAO.printSQLException(sqlException);
            } else {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public Main() throws Exception {
        fabricanteService = new FabricanteService();
        productoService = new ProductoService();
    }
    
    public static void dropDatabase() throws IOException, SQLException {
        DAO.dropDatabase();
    }
    
}
