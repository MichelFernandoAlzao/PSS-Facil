package com.example.pssfacil.DAO

import com.example.pssfacil.model.Candidato
import com.example.pssfacil.model.Estados
import java.sql.ResultSet
import java.sql.SQLException

private var conexao = ConnectionSQL()

class LoginDAO {
    fun validalogin(candidato: Candidato): ArrayList<Candidato> {

        val candidatos = ArrayList<Candidato>()
        val statement = conexao.dbCon()
            ?.prepareStatement("SELECT * FROM Candidato WHERE email = ? AND senha = ?")
        statement?.setString(1, candidato.email)
        statement?.setString(2, candidato.senha)


        val resultset: ResultSet = statement!!.executeQuery()

        while (resultset.next()) {
            Candidato(
                id = resultset.getInt("id"),
                email = resultset.getString("email"),
                senha = resultset.getString("senha")
            )
            candidatos.add(candidato)
        }

        resultset.close()
        statement.close()
        conexao.dbCon()?.close()
        return candidatos
    }


    fun validalogin2(email: String, senha: String): Candidato? {
        try {
            var candidato = Candidato()
            val sql = "SELECT * FROM Candidatos WHERE email = ? AND senha = ?"
            val preparedStatement = conexao.dbCon()!!.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, email.toString())
                preparedStatement.setString(2, senha.toString())
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet!!.next()) {
                candidato.id = resultSet.getInt("id")
                candidato.nome_completo = resultSet.getString("nome_completo")
                return candidato
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
}