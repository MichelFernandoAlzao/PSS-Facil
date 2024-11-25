package com.example.pssfacil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pssfacil.DAO.LoginDAO
import com.example.pssfacil.databinding.ActivityMainBinding
import com.example.pssfacil.model.Candidato

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.buttonLogar.setOnClickListener {

            if(binding.txtusuario.text.toString() == "" || binding.txtusuario.text.toString().length < 3){
                Toast.makeText(this, "Usuário não informado ou invalido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.txtSenha.text.toString() == "" || binding.txtSenha.text.toString().length < 3){
                Toast.makeText(this, "Senha não informada ou invalida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var candidato = Candidato()
            candidato = validalogin()

            if(candidato.id != 0){
                val intent = Intent(this,NavegacaoActivity::class.java)
                intent.putExtra("candidato",candidato)
                //intent.putExtra("iduser",candidato.id)
                //intent.putExtra("username",candidato.nome_completo)
                startActivity(intent)
            }
        }

        binding.buttonCadastrar.setOnClickListener {
            val intent = Intent(this,Cadastro_Activity::class.java)
            intent.putExtra("origem","Entrada")
            startActivity(intent)

        }
    }


    private fun validalogin(): Candidato {

        var candidato = Candidato()
        candidato.email = binding.txtusuario.text.toString()
        candidato.senha = binding.txtSenha.text.toString()

        var login = LoginDAO()
        var candidatoencontrado  = login.validalogin2(candidato.email,candidato.senha)
        if(candidatoencontrado != null){
            candidato = candidatoencontrado
        }
        else{
            Toast.makeText(
                this,
                "Usuário ou senha invalidos!",
                Toast.LENGTH_SHORT).show()
        }
        return candidato
    }
}