package view;

import controller.ComidappController;

import java.util.Scanner;

public class Comidapp {
    public static void main(String[] args) {
        ComidappController controller = new ComidappController();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Ver Productos");
            System.out.println("3. Crear Orden de Compra");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    controller.gestionarUsuarios(scanner);
                    break;
                case 2:
                    controller.verProductos();
                    break;
                case 3:
                    controller.crearOrden(scanner);
                    break;
                case 4:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}
