# Comidapp

Este proyecto es una aplicación de gestión de pedidos de comida utilizando Java y MySQL.

## Requisitos
- JDK 11 o superior
- MySQL 8.x
- IntelliJ IDEA (o cualquier IDE compatible con Java)

## Configuración
1. Ejecuta el script `comidapp_setup.sql` para inicializar la base de datos:

```
mysql -u root -p < comidapp_setup.sql
```
2. Configura las credenciales de la base de datos en `DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/comidapp";
private static final String USER = "root";
private static final String PASSWORD = "tu_contraseña";
```

3. Asegúrate de que el archivo `mysql-connector-j-9.1.0.jar` esté incluido en el classpath:
En IntelliJ IDEA: Haz clic derecho en el proyecto y selecciona Open Module Settings. Ve a Libraries y agrega el archivo JAR desde la carpeta libs.
