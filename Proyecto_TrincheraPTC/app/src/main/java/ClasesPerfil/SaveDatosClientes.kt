package ClasesPerfil

import Modelo.ClaseConexion
import java.sql.SQLException

class SaveDatosClientes {

    // Método para actualizar los datos de un cliente
    fun updateDatosCliente(clientId: Int, nombre: String, telefono: String, correo: String): Boolean {

        // Consulta SQL para actualizar los datos del cliente
        val query = """
            UPDATE clientes_ptc
            SET nombre_clie = ?, telefono_clie = ?, correoElectronico = ?
            WHERE id_cliente = ?
        """

        // Ejecutar la consulta
        return try {
            // Obtener la conexión a la base de datos
            ClaseConexion().cadenaConexion()?.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    // Establecer los valores de los parámetros en la sentencia SQL
                    statement.setString(1, nombre)
                    statement.setString(2, telefono)
                    statement.setString(3, correo)
                    statement.setInt(4, clientId)

                    // Ejecutar la actualización
                    val rowsUpdated = statement.executeUpdate()

                    // Devolver true si se actualizó al menos una fila, false en caso contrario
                    rowsUpdated > 0
                }
            } ?: false
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }
}