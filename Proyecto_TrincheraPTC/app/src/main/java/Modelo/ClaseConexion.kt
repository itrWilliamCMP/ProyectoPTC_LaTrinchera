package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {

<<<<<<< HEAD
            val url="jdbc:oracle:thin:@192.168.235.124:1521:xe"

            val user="TRINCHE_PTC"
            val password="TRINCHE_PTC"
=======
            val url="jdbc:oracle:thin:@192.168.56.1:1521:xe"

            val user="system"
            val password="ITR2024"
>>>>>>> master
            val connection= DriverManager.getConnection(url, user, password)

            return connection
        }catch (e: Exception){
            println("Este es el error:$e")
            return null
        }
    }
}