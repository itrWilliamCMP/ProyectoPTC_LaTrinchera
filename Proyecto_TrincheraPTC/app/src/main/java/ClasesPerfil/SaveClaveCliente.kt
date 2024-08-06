package ClasesPerfil

import Modelo.ClaseConexion
import java.security.MessageDigest
import java.sql.SQLException

class SaveClaveCliente {
    // Método para actualizar los datos de un cliente
    fun updateClaveCliente(clientId: Int, clave1: String, clave2: String): Boolean {

        // Verificar si las claves son iguales
        var hashClave = hashSHA256(clave1)


        val query = """
            UPDATE clientes_ptc SET contrasena = ? WHERE id_cliente = ?
        """


        return try {
            // Obtener la conexión a la base de datos
            ClaseConexion().cadenaConexion()?.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    // Establecer los valores de los parámetros en la sentencia SQL

                    statement.setString(1, hashClave)
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

    // Función para encriptar la contraseña (corregida)
    fun hashSHA256(contraseniaEscrita: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(contraseniaEscrita.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}