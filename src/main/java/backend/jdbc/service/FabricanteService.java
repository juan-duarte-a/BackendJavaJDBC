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
    
    public Fabricante getFabricanteById(long codigo) {
        return fabricanteDao.getById(codigo).orElseThrow();
    }
    
    public List<Fabricante> getFabricanteByName(String name) {
        return fabricanteDao.getByName(name);
    }
    
    public List<Fabricante> getAllFabricantes() {
        return fabricanteDao.getAll();
    }
    
    public long addFabricante(Fabricante fabricante) {
        return fabricanteDao.save(fabricante).get(0);
    }
    
    public void updateFabricante(Fabricante fabricante) {
        fabricanteDao.update(fabricante);
    }
    
    public void deleteFabricante(Fabricante fabricante) {
        fabricanteDao.delete(fabricante);
    }
    
}
