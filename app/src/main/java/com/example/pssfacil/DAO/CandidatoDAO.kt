package com.example.pssfacil.DAO

import com.example.pssfacil.model.Candidato
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime

private var conexao = ConnectionSQL()



class CandidatoDAO {
    var resultado:String = "Erro"
    fun InsereCadastro(candidato:Candidato){
        try {
            val sql =
                "insert into Candidatos(nome_completo,nome_pai,nome_mae,data_nascimento,sexo,estado_civil,cpf,rg,endereco,numero,cep,pais,uf,email,telefone,senha) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            val insereusuario = conexao.dbCon()?.prepareStatement(sql)!!
            insereusuario.setString(1, candidato.nome_completo)
            insereusuario.setString(2, candidato.nome_pai)
            insereusuario.setString(3, candidato.nome_mae)
            insereusuario.setString(4, candidato.data_nascimento)
            insereusuario.setString(5, candidato.sexo)
            insereusuario.setString(6, candidato.estadoCivil)
            insereusuario.setString(7, candidato.cpf)
            insereusuario.setString(8, candidato.rg)
            insereusuario.setString(9, candidato.logradouro)
            insereusuario.setString(10, candidato.numero.toString())
            insereusuario.setString(11, candidato.cep)
            insereusuario.setString(12, candidato.pais)
            insereusuario.setString(13, candidato.uf)
            insereusuario.setString(14, candidato.email)
            insereusuario.setString(15, candidato.telefone)
            insereusuario.setString(16, candidato.senha)

            insereusuario.executeUpdate()

            resultado = "Sucesso"
        }
        catch (e:SQLException){
            println("Erro ao inserir usuário: ${e.message}")
        }
    }

    fun Verificacadastroexistente(candidato: Candidato):Boolean {
        try {
            val candidatos = ArrayList<Candidato>()
            val sql =
                "SELECT * FROM Candidatos WHERE cpf = ? OR email = ?"
            val preparedStatement = conexao.dbCon()?.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, candidato.cpf)
                preparedStatement.setString(2, candidato.email)
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet != null) {
                while (resultSet.next()) {
                    Candidato(
                        id = resultSet.getInt("id"),
                        email = resultSet.getString("email"),
                        senha = resultSet.getString("senha")
                    )
                    candidatos.add(candidato)
                }
            }

            if (resultSet != null) {
                resultSet.close()
                preparedStatement.close()
            }

            if (candidatos.count() > 0) {
                return true
            } else return false
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return false
    }

    fun buscaidCandidato(candidato: Candidato):Candidato {
        val candidatos = ArrayList<Candidato>()
        try {

            val sql =
                "SELECT * FROM Candidatos WHERE cpf = ?"
            val preparedStatement = conexao.dbCon()?.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, candidato.cpf)
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet != null) {
                while (resultSet.next()) {
                    Candidato(
                        id = resultSet.getInt("id"),
                        email = resultSet.getString("email"),
                        senha = resultSet.getString("senha")
                    )
                    candidatos.add(candidato)
                }
            }

            if (resultSet != null) {
                resultSet.close()
                preparedStatement.close()
            }

            if (candidatos.count() > 0) {
                return candidatos[0]
            } else return candidatos[0]
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return candidatos[0]
    }

    fun aceitatermos(candidato:Candidato){
        try {
            val sql =
                "UPDATE Candidatos SET aceite_termos = ?, data_aceite = ? WHERE id = ?"
            val insereusuario = conexao.dbCon()?.prepareStatement(sql)!!
            insereusuario.setString(1, "S")
            insereusuario.setString(2, LocalDate.now().toString())
            insereusuario.setString(3, candidato.id.toString())

            insereusuario.executeUpdate()

            resultado = "Sucesso"
        }
        catch (e:SQLException){
            println("Erro ao realizar aceite: ${e.message}")
        }
    }

    fun verificaaceitetermos(candidato: Candidato):Candidato {
        val candidatos = ArrayList<Candidato>()
        try {

            val sql =
                "SELECT * FROM Candidatos WHERE id = ?"
            val preparedStatement = conexao.dbCon()?.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, candidato.cpf)
                preparedStatement.setString(2, candidato.email)
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet != null) {
                while (resultSet.next()) {
                    Candidato(
                        aceite_termos = resultSet.getString("aceite_termos"),
                        data_aceite = resultSet.getString("data_aceite")
                    )
                    candidatos.add(candidato)
                }
            }

            if (resultSet != null) {
                resultSet.close()
                preparedStatement.close()
            }

            if (candidatos.count() > 0) {
                return candidatos[0]
            } else return candidatos[0]
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return candidatos[0]
    }

    fun CarregaCandidato(candidato: Candidato):Candidato? {
        try {
            var resultado = Candidato()
            val sql =
                "SELECT * FROM Candidatos WHERE id = ?"
            val preparedStatement = conexao.dbCon()?.prepareStatement(sql)
            if (preparedStatement != null) {
                preparedStatement.setString(1, candidato.id.toString())
            }

            val resultSet = preparedStatement?.executeQuery()

            if (resultSet != null) {
                while (resultSet.next()) {
                    val retorno = Candidato(
                        id = resultSet.getInt("id"),
                        nome_completo = resultSet.getString("nome_completo").toString(),
                        nome_pai = resultSet.getString("nome_pai").toString(),
                        nome_mae = resultSet.getString("nome_mae").toString(),
                        data_nascimento = resultSet.getString("data_nascimento").toString(),
                        sexo = resultSet.getString("sexo").toString(),
                        estadoCivil = resultSet.getString("estado_civil").toString(),
                        cpf = resultSet.getString("cpf").toString(),
                        rg = resultSet.getString("rg").toString(),
                        cep = resultSet.getString("cep").toString(),
                        logradouro = resultSet.getString("endereco").toString(),
                        numero = resultSet.getString("numero").toInt(),
                        pais = resultSet.getString("pais").toString(),
                        uf = resultSet.getString("uf").toString(),
                        email = resultSet.getString("email").toString(),
                        telefone = resultSet.getString("telefone").toString(),
                        senha = resultSet.getString("senha").toString(),
                        aceite_termos = resultSet.getString("aceite_termos")?:"",
                        data_aceite = resultSet.getString("data_aceite")?:"".toString()
                    )
                    return retorno
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

}