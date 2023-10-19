package backend.jdbc.entity;

public class Fabricante {
    
    private long codigo;
    private String nombre;

    public Fabricante() { }

    public Fabricante(String nombre) {
        this.nombre = nombre;
    }
    
    public Fabricante(long id, String nombre) {
        this.codigo = id;
        this.nombre = nombre;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long id) {
        this.codigo = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return "Fabricante: [código = " + codigo
                + ", nombre = " + nombre + "]";
    }
    
}
