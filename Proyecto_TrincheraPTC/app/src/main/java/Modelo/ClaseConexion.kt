package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {

<<<<<<< HEAD
            val url="jdbc:oracle:thin:@10.10.2.241:1521:xe"
=======
            val url="jdbc:oracle:thin:192.168.1.10:1521:xe"
>>>>>>> 33784e090d270aff5923600d09c95556d59d8ecb

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