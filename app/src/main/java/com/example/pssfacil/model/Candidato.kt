package com.example.pssfacil.model

import java.io.Serializable

data class Candidato (
    var id : Int = 0,
    var nome_completo:String = "",
    var nome_pai:String = "",
    var nome_mae:String = "",
    var data_nascimento:String = "",
    var sexo:String = "",
    var estadoCivil:String = "",
    var cpf:String = "",
    var rg:String = "",
    var cep:String = "",
    var logradouro:String = "",
    var numero: Int = 0,
    var pais:String = "",
    var uf:String = "",
    var email:String = "",
    var telefone:String = "",
    var senha:String  = "",
    var aceite_termos:String = "",
    var data_aceite:String = ""
):Serializable