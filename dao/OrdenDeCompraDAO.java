package dao;

import model.OrdenDeCompra;
import model.DBConnection;

import java.sql.*;

public class OrdenDeCompraDAO {
    public void crearOrden(OrdenDeCompra orden) {
        String query = "INSERT INTO OrdenDeCompra (id_usuario, total) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orden.getUsuarioId());
            statement.setDouble(2, 0.0); // Total inicial
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int ordenId = generatedKeys.getInt(1);
                for (int productoId : orden.getProductos()) {
                    agregarProductoAOrden(ordenId, productoId, connection);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarProductoAOrden(int ordenId, int productoId, Connection connection) throws SQLException {
        String query = "INSERT INTO DetalleOrden (id_orden, id_producto) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ordenId);
            statement.setInt(2, productoId);
            statement.executeUpdate();
        }
    }
}
