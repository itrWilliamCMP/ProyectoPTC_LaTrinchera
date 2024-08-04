package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {
<<<<<<< HEAD
            val url="jdbc:oracle:thin:@192.168.0.11:1521:xe"
            val user="TRINCHE"
            val password="TRINCHE"
=======
            val url="jdbc:oracle:thin:@192.168.68.107:1521:xe"
            val user="C##_PTCLT"
            val password="ptccontraseña"
>>>>>>> 8660f7ad6884c81c6b66cb21717e5e6c53747891
            val connection= DriverManager.getConnection(url, user, password)

            return connection
        }catch (e: Exception){
            println("este es el error:$e")
            return null
        }
    }
}