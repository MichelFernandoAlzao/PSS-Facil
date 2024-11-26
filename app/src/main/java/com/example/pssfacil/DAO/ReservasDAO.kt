package com.example.pssfacil.DAO

import com.example.pssfacil.model.Candidato
import com.example.pssfacil.model.Processo
import com.example.pssfacil.model.Reserva
import java.sql.SQLException

private var conexao = ConnectionSQL()

class ReservasDAO {
    var resultado:String = "Erro"
    fun InsereCadastro(vaga:String,candidato:String){
        try {
            val sql =
                "insert into ReservasVagas(candidato_id,vaga_id) values(?,?)"
            val inserereserva = conexao.dbCon()?.prepareStatement(sql)!!
            inserereserva.setString(1, candidato)
            inserereserva.setString(2, vaga)


            inserereserva.executeUpdate()

            resultado = "Sucesso"
        }
        catch (e: SQLException){
            println("Erro ao inserir usuário: ${e.message}")
        }
    }

    fun verificareserva(processo:String, candidato:String):Boolean {
        try {
            var lstResultados = ArrayList<Reserva>()
            var resultado = Reserva()
            val sql = "SELECT * FROM ReservasVagas WHERE candidato_id = ? AND vaga_id = ?"
            val preparedStatement = conexao.dbCon()!!.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, candidato)
                preparedStatement.setString(2, processo)
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet!!.next()) {
                var resultado = Reserva(
                candidato_id = resultSet.getString("candidato_id"),
                    vaga_id = resultSet.getString("vaga_id")
                )
                lstResultados.add(resultado)
            }



            if (resultSet != null) {
                resultSet.close()
                preparedStatement.close()
            }
            if(lstResultados.count() > 0)
                return true
            else return false
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return  false
    }
}