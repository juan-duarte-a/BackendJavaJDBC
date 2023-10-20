package backend.jdbc;

import backend.jdbc.entity.Fabricante;
import backend.jdbc.entity.Producto;
import backend.jdbc.persistance.Dao;
import backend.jdbc.service.FabricanteService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    
    private final FabricanteService fabricanteService;

    public static void main(String[] args) {
        try {
            Main main = new Main();
            
            List<Fabricante> fabricantes =  main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            Fabricante fabricante;
            
            System.out.println();
            fabricante = new Fabricante("Nvidia");
            long codigo = main.fabricanteService.addFabricante(fabricante);
            
            fabricante = main.fabricanteService.getFabricante(codigo);
            System.out.println(fabricante);
            
            fabricante.setNombre("Logitech");
            main.fabricanteService.updateFabricante(fabricante);
            fabricante = main.fabricanteService.getFabricante(codigo);
            System.out.println(fabricante);
            
            System.out.println();
            fabricantes = main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            
            System.out.println();
            main.fabricanteService.deleteFabricante(fabricante);
            fabricantes = main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            
            System.out.println();
            
        } catch (Exception e) {
            if (e instanceof SQLException sqlException) {
                Dao.printSQLException(sqlException);
            } else {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public Main() throws Exception {
        fabricanteService = new FabricanteService();
    }
    
    public static void dropDatabase() throws IOException, SQLException {
        FabricanteService.dropDatabase();
    }
    
}
