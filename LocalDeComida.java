import java.util.ArrayList;

public class LocalDeComida {
    private int id;
    private String nombre;
    private ArrayList<OrdenDeCompra> ordenes = new ArrayList<>();

    public LocalDeComida(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarOrden(OrdenDeCompra orden) {
        ordenes.add(orden);
    }

    public ArrayList<OrdenDeCompra> getOrdenes() {
        return ordenes;
    }
}
