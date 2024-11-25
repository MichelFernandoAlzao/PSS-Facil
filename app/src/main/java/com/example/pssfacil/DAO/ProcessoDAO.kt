package com.example.pssfacil.DAO

import com.example.pssfacil.model.Processo
import java.sql.SQLException

private var conexao = ConnectionSQL()

class ProcessoDAO {
    fun CarregaProcesso(processo: Processo): Processo? {
        try {
            var dadoprocesso = Processo()
            val sql =
                "SELECT * FROM processos WHERE id = ?"
            val preparedStatement = conexao.dbCon()?.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, processo.id.toString())
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet != null) {
                while (resultSet.next()) {
                    dadoprocesso.id = resultSet.getString("id")
                    //dadoprocesso.logoOrgao = resultSet.getString("logo").toInt()
                    dadoprocesso.descricaoProcesso = resultSet.getString("descricao")
                    dadoprocesso.nomeProcesso = resultSet.getString("nome")
                    dadoprocesso.remuneracao = resultSet.getString("salario")
                    dadoprocesso.orgao_responsavel = resultSet.getString("org_responsavel")

                    return  dadoprocesso
                }
            }

            if (resultSet != null) {
                resultSet.close()
                preparedStatement.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun CarregaListaProcessos(): ArrayList<Processo>? {
        try {
            var dadoprocesso = Processo()
            val processos = ArrayList<Processo>()
            val sql =
                "SELECT * FROM processos"
            val preparedStatement = conexao.dbCon()?.prepareStatement(sql)

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet != null) {
                while (resultSet.next()) {
                    dadoprocesso.id = resultSet.getString("id")
                    dadoprocesso.logoOrgao = resultSet.getString("logo").toInt()
                    dadoprocesso.descricaoProcesso = resultSet.getString("descricao")
                    dadoprocesso.nomeProcesso = resultSet.getString("nome")
                    dadoprocesso.remuneracao = resultSet.getString("salario")
                    dadoprocesso.orgao_responsavel = resultSet.getString("org_responsavel")

                    processos.add(dadoprocesso)
                }
            }

            if (resultSet != null) {
                resultSet.close()
                preparedStatement.close()
            }

            return processos
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

}