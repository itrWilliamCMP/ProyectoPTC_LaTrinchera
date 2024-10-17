package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {


            val url="jdbc:oracle:thin:@192.168.10.12:1521:xe"

            val user="TRINCHE_PTC2024"
            val password="TRINCHE_PTC2024"

            val connection= DriverManager.getConnection(url, user, password)

            return connection
        }catch (e: Exception){
            println("Este es el error:$e")
            return null
        }
    }
}