package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {
<<<<<<< HEAD
            val url="jdbc:oracle:thin:@192.168.1.27:1521:xe"
            val user="C##_PTCLT"
            val password="ptccontraseña"
=======
            val url="jdbc:oracle:thin:@192.168.1.200:1521:xe"
            val user="WILLIAM_DEVELOPER"
            val password="123456"
>>>>>>> master
            val connection= DriverManager.getConnection(url, user, password)

            return connection
        }catch (e: Exception){
            println("este es el error:$e")
            return null
        }
    }
}