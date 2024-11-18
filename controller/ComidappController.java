package controller;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import dao.OrdenDeCompraDAO;
import model.OrdenDeCompra;
import model.Producto;
import model.Usuario;

import java.util.List;
import java.util.Scanner;

public class ComidappController {
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;
    private final OrdenDeCompraDAO ordenDeCompraDAO;

    public ComidappController() {
        this.usuarioDAO = new UsuarioDAO();
        this.productoDAO = new ProductoDAO();
        this.ordenDeCompraDAO = new OrdenDeCompraDAO();
    }

    public void gestionarUsuarios(Scanner scanner) {
        System.out.println("\n--- GESTIÓN DE USUARIOS ---");
        System.out.println("1. Agregar Usuario");
        System.out.println("2. Listar Usuarios");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (opcion == 1) {
            System.out.print("Ingrese nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese email: ");
            String email = scanner.nextLine();
            System.out.print("Ingrese teléfono: ");
            String telefono = scanner.nextLine();

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);

            usuarioDAO.addUsuario(usuario);
            System.out.println("Usuario agregado correctamente.");
        } else if (opcion == 2) {
            List<Usuario> usuarios = usuarioDAO.getUsuarios();
            usuarios.forEach(usuario -> {
                System.out.println("ID: " + usuario.getId());
                System.out.println("Nombre: " + usuario.getNombre());
                System.out.println("Email: " + usuario.getEmail());
                System.out.println("Teléfono: " + usuario.getTelefono());
                System.out.println("--------------------------");
            });
        } else {
            System.out.println("Opción no válida.");
        }
    }

    public void verProductos() {
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        List<Producto> productos = productoDAO.getProductos();
        productos.forEach(producto -> {
            System.out.println("ID: " + producto.getId());
            System.out.println("Nombre: " + producto.getNombre());
            System.out.println("Precio: " + producto.getPrecio());
            System.out.println("Categoría: " + producto.getCategoria());
            System.out.println("--------------------------");
        });
    }

    public void crearOrden(Scanner scanner) {
        System.out.println("\n--- CREAR ORDEN DE COMPRA ---");

        // Seleccionar usuario
        System.out.println("Seleccione un usuario (ID):");
        List<Usuario> usuarios = usuarioDAO.getUsuarios();
        usuarios.forEach(usuario -> {
            System.out.println("ID: " + usuario.getId() + " - Nombre: " + usuario.getNombre());
        });
        int usuarioId = scanner.nextInt();

        // Seleccionar productos
        System.out.println("Seleccione productos para la orden (ID, separados por comas):");
        List<Producto> productos = productoDAO.getProductos();
        productos.forEach(producto -> {
            System.out.println("ID: " + producto.getId() + " - Nombre: " + producto.getNombre() + " - Precio: " + producto.getPrecio());
        });
        scanner.nextLine(); // Consumir salto de línea
        String[] productoIds = scanner.nextLine().split(",");

        // Crear orden
        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setUsuarioId(usuarioId);
        for (String productoId : productoIds) {
            orden.addProducto(Integer.parseInt(productoId));
        }
        ordenDeCompraDAO.crearOrden(orden);
        System.out.println("Orden creada correctamente.");
    }
}
