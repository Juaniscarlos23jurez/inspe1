package com.example.inspe

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class MySQLConnection {
    private var connection: Connection? = null

    fun connect(): Connection? {
        try {
            // Establece los datos de conexión a tu base de datos MySQL
            val url = "https://208.73.202.83:2083/yelsanco_Empresa"
            val user = "yelsanco"
            val password = "6bHgcAC1MUJl"

            // Carga el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver")

            // Establece la conexión
            connection = DriverManager.getConnection(url, user, password)

            return connection
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }

    fun disconnect() {
        connection?.close()
    }
}