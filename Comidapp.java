import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Comidapp {
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<LocalDeComida> locales = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDatosMock();
        mostrarMenu();
    }

    private static void inicializarDatosMock() {
        productos.add(new Producto(1, "Hamburguesa", 500.0));
        productos.add(new Producto(2, "Pizza", 800.0));
        productos.add(new Producto(3, "Sushi", 1200.0));
        productos.add(new Producto(4, "Ensalada", 300.0));

        LocalDeComida localEjemplo = new LocalDeComida(1, "Comida Rápida S.A.");
        locales.add(localEjemplo);

        OrdenDeCompra orden = new OrdenDeCompra(localEjemplo.getId());
        orden.agregarProducto(productos.get(0));
        orden.agregarProducto(productos.get(1));
        localEjemplo.agregarOrden(orden);

        System.out.println("Datos mock cargados exitosamente.");
    }

    private static void mostrarMenu() {
        int opcion = -1;
        do {
            try {
                System.out.println("\n--- Menú Principal ---");
                System.out.println("1. Ver productos");
                System.out.println("2. Buscar productos");
                System.out.println("3. Crear orden");
                System.out.println("4. Crear nuevo local");
                System.out.println("5. Crear nuevo producto");
                System.out.println("6. Ver órdenes del local de ejemplo (ID: 1)");
                System.out.println("7. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> verProductos();
                    case 2 -> buscarProductos();
                    case 3 -> crearOrden();
                    case 4 -> crearLocal();
                    case 5 -> crearProducto();
                    case 6 -> verOrdenesDeLocal();
                    case 7 -> System.out.println("¡Gracias por usar Comidapp!");
                    default -> System.out.println("Opción inválida, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero. Intente nuevamente.");
                scanner.nextLine(); // Limpiar el buffer
            }
        } while (opcion != 7);
    }

    private static void verProductos() {
        System.out.println("\n--- Lista de Productos ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private static void buscarProductos() {
        System.out.print("Ingrese palabra para buscar: ");
        scanner.nextLine(); // Limpiar buffer
        String palabra = scanner.nextLine().toLowerCase();
        System.out.println("\n--- Resultados de búsqueda ---");
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(palabra)) {
                System.out.println(p);
            }
        }
    }

    private static void crearOrden() {
        try {
            System.out.print("Ingrese el ID del local: ");
            int idLocal = scanner.nextInt();
            OrdenDeCompra orden = new OrdenDeCompra(idLocal);

            while (true) {
                System.out.print("Ingrese el ID del producto (0 para terminar): ");
                int idProducto = scanner.nextInt();
                if (idProducto == 0) break;

                Producto producto = productos.stream()
                        .filter(p -> p.getId() == idProducto)
                        .findFirst()
                        .orElse(null);

                if (producto != null) {
                    orden.agregarProducto(producto);
                    System.out.println("Producto agregado: " + producto.getNombre());
                } else {
                    System.out.println("Producto no encontrado.");
                }
            }

            mostrarResumenOrden(orden);

            LocalDeComida local = locales.stream()
                    .filter(l -> l.getId() == idLocal)
                    .findFirst()
                    .orElse(null);

            if (local != null) {
                local.agregarOrden(orden);
                System.out.println("Orden creada exitosamente.");
            } else {
                System.out.println("Local no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido. Operación cancelada.");
            scanner.nextLine(); // Limpiar el buffer
        }
    }

    private static void mostrarResumenOrden(OrdenDeCompra orden) {
        System.out.println("\n--- Resumen de la Orden ---");
        double total = 0;
        for (Producto producto : orden.getProductos()) {
            System.out.println(producto);
            total += producto.getPrecio();
        }
        System.out.printf("Total de la Orden: $%.2f%n", total);
    }

    private static void crearLocal() {
        System.out.print("Ingrese el nombre del nuevo local: ");
        scanner.nextLine(); // Limpiar buffer
        String nombre = scanner.nextLine();
        LocalDeComida local = new LocalDeComida(locales.size() + 1, nombre);
        locales.add(local);
        System.out.println("Local creado con ID: " + local.getId());
    }

    private static void crearProducto() {
        System.out.print("Ingrese nombre del producto: ");
        scanner.nextLine(); // Limpiar buffer
        String nombre = scanner.nextLine();
        System.out.print("Ingrese precio del producto: ");
        double precio = scanner.nextDouble();
        Producto producto = new Producto(productos.size() + 1, nombre, precio);
        productos.add(producto);
        System.out.println("Producto creado con ID: " + producto.getId());
    }

    private static void verOrdenesDeLocal() {
        System.out.print("Ingrese el ID del local: ");
        int idLocal = scanner.nextInt();

        LocalDeComida local = locales.stream()
                .filter(l -> l.getId() == idLocal)
                .findFirst()
                .orElse(null);

        if (local != null) {
            System.out.println("\n--- Órdenes del Local " + local.getNombre() + " ---");
            for (OrdenDeCompra orden : local.getOrdenes()) {
                System.out.println(orden);
            }
        } else {
            System.out.println("Local no encontrado.");
        }
    }
}
