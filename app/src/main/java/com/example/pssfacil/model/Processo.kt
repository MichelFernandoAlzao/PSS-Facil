package com.example.pssfacil.model

data class Processo(
    var id:String = "",
    var logoOrgao: Int = 0,
    var nomeProcesso:String ="",
    var descricaoProcesso:String = "",
    var dataVigencia:String = "",
    var orgao_responsavel:String = "",
    var remuneracao:String = ""
)