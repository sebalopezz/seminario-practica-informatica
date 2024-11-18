package model;

import java.util.ArrayList;
import java.util.List;

public class OrdenDeCompra {
    private int id;
    private int usuarioId;
    private List<Integer> productos;

    public OrdenDeCompra() {
        this.productos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Integer> getProductos() {
        return productos;
    }

    public void addProducto(int productoId) {
        this.productos.add(productoId);
    }
}
