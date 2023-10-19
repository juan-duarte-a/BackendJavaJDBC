package backend.jdbc.service;

import backend.jdbc.entity.Fabricante;
import backend.jdbc.persistance.FabricanteDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FabricanteService {
    
    private final FabricanteDao fabricanteDao;
    
    public FabricanteService() throws IOException, SQLException {
        fabricanteDao = new FabricanteDao();
    }
    
    public Fabricante getFabricante(long codigo) {
        return fabricanteDao.get(codigo).orElseThrow();
    }
    
    public List<Fabricante> getAllFabricantes() {
        return fabricanteDao.getAll();
    }
    
    public void addFabricante(Fabricante fabricante) {
        fabricanteDao.save(fabricante);
    }
    
}
