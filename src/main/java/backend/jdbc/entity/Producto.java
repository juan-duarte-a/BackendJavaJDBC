package backend.jdbc.entity;

import java.math.BigDecimal;

public class Producto {
    
    private long codigo;
    private String nombre;
    private BigDecimal precio;
    private long codigoFabricante;
    
    public Producto() { }

    public Producto(String nombre, BigDecimal precio, long codigoFabricante) {
        this.nombre = nombre;
        this.precio = precio;
        this.codigoFabricante = codigoFabricante;
    }
    
    public Producto(long codigo, String nombre, BigDecimal precio, long codigoFabricante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.codigoFabricante = codigoFabricante;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public long getCodigoFabricante() {
        return codigoFabricante;
    }

    public void setCodigoFabricante(long codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    @Override
    public String toString() {
        return "Producto: [código = " + codigo + 
                ", nombre = " + nombre + 
                ", precio = " + precio.toString() + 
                ", codigoFabricante = " + codigoFabricante + "]";
    }
    
}
