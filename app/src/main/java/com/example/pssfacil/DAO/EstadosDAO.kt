package com.example.pssfacil.DAO

import com.example.pssfacil.model.Estados
import java.sql.ResultSet

private var conexao = ConnectionSQL()

class EstadosDAO {
    fun  ConsultaEstados(estado:String): ArrayList<Estados> {

        val estados  = ArrayList<Estados>()
        val statement = conexao.dbCon()?.createStatement()

        val resultset: ResultSet = statement!!.executeQuery("SELECT * FROM estados")

        while (resultset.next()) {
            val estado = Estados(
                estadosRowId = resultset.getInt("estadosRowId"),
                desc_estado = resultset.getString("desc_estado"),
                uf_sigla = resultset.getString("desc_estado")
            )
            estados.add(estado )
        }

        resultset.close()
        statement.close()
        conexao.dbCon()?.close()

        return estados
    }

    //Função para retornar ArrayList de Strings com os estados possiveis do banco de dados
    fun  ConsultaEstadosUF(estado:String): ArrayList<String> {

        val estados  = ArrayList<String>()
        val statement = conexao.dbCon()?.createStatement()

        val resultset: ResultSet = statement!!.executeQuery("SELECT * FROM estados")

        while (resultset.next()) {
            val estado = Estados(
                uf_sigla = resultset.getString("desc_estado")
            )
            estados.add(estado.uf_sigla )
        }

        resultset.close()
        statement.close()
        conexao.dbCon()?.close()

        return estados
    }
}