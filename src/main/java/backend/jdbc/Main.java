package backend.jdbc;

import backend.jdbc.entity.Fabricante;
import backend.jdbc.persistance.Dao;
import backend.jdbc.service.FabricanteService;
import java.sql.SQLException;
import java.util.List;

public class Main {
    
    private final FabricanteService fabricanteService;

    public static void main(String[] args) {
        try {
            Main main = new Main();
            List<Fabricante> fabricantes =  main.fabricanteService.getAllFabricantes();
            fabricantes.forEach(System.out::println);
            
            System.out.println();
            Fabricante fabricante = main.fabricanteService.getFabricante(7);
            System.out.println(fabricante);
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
    
}
