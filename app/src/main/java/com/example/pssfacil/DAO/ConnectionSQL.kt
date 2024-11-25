package com.example.pssfacil.DAO

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionSQL {
    private val ip="pssfacil.ddns.net:1975"
    //private val ip="192.168.15.104:1975"
    private val db="PSSFacil"
    private val username="Michel"
    private val senha="Michel11041997@"

    fun dbCon(): Connection? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn:Connection? = null
        val connString : String
        try{
            val driverClass = net.sourceforge.jtds.jdbc.Driver::class.java
            val driverInstance = driverClass.getDeclaredConstructor().newInstance()
            DriverManager.registerDriver(driverInstance)
            //Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            connString = "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$username;password=$senha"
            conn= DriverManager.getConnection(connString)
        }catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
        }catch (ex1: ClassNotFoundException) {
            Log.e("Error: ", ex1.message!!)
        }catch (ex2: Exception) {
            Log.e("Error: ", ex2.message!!)
        }

        return conn
    }


    fun getConnection():Connection{
        Class.forName("net.sourceforge.jtds.jdbc.Driver")

        //Retorna a conexão
        return DriverManager.getConnection(
            "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$username;password=$senha"
        )
    }
}