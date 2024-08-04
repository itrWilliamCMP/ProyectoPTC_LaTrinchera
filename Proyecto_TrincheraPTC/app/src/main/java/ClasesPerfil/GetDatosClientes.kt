package ClasesPerfil

import Modelo.*
import java.sql.ResultSet


class GetDatosClientes {

    // Método para obtener los datos de un cliente por su ID
    fun getDatosCliente(clientId: Int): Perfil_info? {
        var client: Perfil_info? = null
        val query = "SELECT * FROM clientes_ptc WHERE id_cliente = ?"

        // Utilizar la conexión de la clase ClaseConexion para ejecutar la consulta
        ClaseConexion().cadenaConexion()?.use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setInt(1, clientId)
                val resultSet: ResultSet = statement.executeQuery()
                // Procesar el resultado de la consulta
                if (resultSet.next()) {
                    // Obtener los valores de los campos de la tabla
                    var nombre_clie = resultSet.getString("nombre_clie")
                    var telefono_clie = resultSet.getString("telefono_clie")
                    var correoElectronico = resultSet.getString("correoElectronico")
                    var direccion_entrega = resultSet.getString("direccion_entrega")

                    // Manejar valores nulos
                    if (direccion_entrega==null){
                        direccion_entrega = ""
                    }

                    if (nombre_clie==null){
                        nombre_clie = ""
                    }

                    if (telefono_clie==null){
                        telefono_clie = ""
                    }

                    if (correoElectronico==null){
                        correoElectronico = ""
                    }



                    // Obtener otros campos según sea necesario
                    client = Perfil_info(clientId, nombre_clie, telefono_clie, correoElectronico, direccion_entrega)
                }
            }
        }

        // Devolver el objeto Perfil_info si se encontró, o null si no se encontró
        return client
    }
}