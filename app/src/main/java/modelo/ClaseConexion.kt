package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection? {

        try {

            val url = "jdbc:oracle:thin:@192.168.1.19:1521:xe"
            val usuario = "SARAVIA_DEVELOPER"
            val contrasena = "JLSN2024"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection

        }catch (e: Exception){
            println("Ha ocurrido un error: $e")
            return null
        }

    }

}