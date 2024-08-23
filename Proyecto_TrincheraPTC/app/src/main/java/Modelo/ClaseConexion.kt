package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {
<<<<<<< HEAD
            val url="jdbc:oracle:thin:@192.168.10.7:1521:xe"
=======
            val url="jdbc:oracle:thin:@10.10.4.159:1521:xe"
>>>>>>> 7e40b41934efe7816652d4b0d90a08a4e4524a4a
            val user="system"
            val password="ITR2024"
            val connection= DriverManager.getConnection(url, user, password)

            return connection
        }catch (e: Exception){
            println("Este es el error:$e")
            return null
        }
    }
}