import java.util.ArrayList;

public class OrdenDeCompra {
    private ArrayList<Producto> productos;
    private int idLocal;

    public OrdenDeCompra(int idLocal) {
        this.idLocal = idLocal;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orden del local ID ").append(idLocal).append(":\n");
        for (Producto p : productos) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
