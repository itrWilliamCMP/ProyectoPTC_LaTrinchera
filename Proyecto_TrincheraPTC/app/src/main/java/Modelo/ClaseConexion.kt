package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {

            val url="jdbc:oracle:thin:@10.10.2.241:1521:xe"

            val user="TRINCHE_PTC"
            val password="TRINCHE_PTC"
            val connection= DriverManager.getConnection(url, user, password)

            return connection
        }catch (e: Exception){
            println("Este es el error:$e")
            return null
        }
    }
}