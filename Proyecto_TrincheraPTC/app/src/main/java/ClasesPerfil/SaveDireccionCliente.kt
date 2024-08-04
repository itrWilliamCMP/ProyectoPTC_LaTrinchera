package ClasesPerfil

import Modelo.ClaseConexion
import java.sql.SQLException

class SaveDireccionCliente {

    fun updateDireccionCliente(clientId: Int, direccionEntrega: String): Boolean {


        //Consulta SQL para actualizar la dirección de entrega
        val query = """
            UPDATE clientes_ptc SET direccion_entrega = ? WHERE id_cliente = ?
        """

        return try {
            // Obtener la conexión a la base de datos
            ClaseConexion().cadenaConexion()?.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    // Establecer los valores de los parámetros en la sentencia SQL

                    statement.setString(1, direccionEntrega)
                    statement.setInt(2, clientId)


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