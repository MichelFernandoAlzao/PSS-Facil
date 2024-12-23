package com.example.pssfacil

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pssfacil.Fragments.DadosAcademicos_Fragment
import com.example.pssfacil.Fragments.DadosPessoais_Fragment
import com.example.pssfacil.Fragments.DadosProfissionaisFragment
import com.example.pssfacil.Fragments.TermosDeAdesaoFragment
import com.example.pssfacil.model.ModelCadastro
import com.example.pssfacil.model.Candidato

class Cadastro_Activity : AppCompatActivity() {

    private val sharedviewModel: ModelCadastro by viewModels()
    private lateinit var buttonDadosPessoais:Button
    private lateinit var buttonDadosAcademicos:Button
    private lateinit var buttonDadosProfissionais:Button
    private lateinit var buttonTermos:Button

    lateinit var candidato:Candidato


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)


        candidato = Candidato()
        sharedviewModel.dadosCompartilhados.value = candidato

        recuperadados()
        if(candidato.id != 0){
         sharedviewModel.dadosCompartilhados.value = candidato
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        buttonDadosPessoais = findViewById(R.id.btnDadosPessoais)
        buttonDadosPessoais.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("nome", "João")
            val fragment = DadosPessoais_Fragment.newInstance("TEste1", "Teste2")
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.ContainerCadastro, fragment,"testepassarid")
            transaction.commit()
        }

        buttonDadosAcademicos = findViewById(R.id.btnDadosAcademicos)
        buttonDadosAcademicos.setOnClickListener {
            if(sharedviewModel.dadosCompartilhados.value?.id == 0) {
                Toast.makeText(this,
                    "Usuário não logado ou cadastrado!",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val fragment = DadosAcademicos_Fragment.newInstance("", "")
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.ContainerCadastro, fragment)
            transaction.commit()
        }

        buttonDadosProfissionais = findViewById(R.id.btnDadosProfissionais)
        buttonDadosProfissionais.setOnClickListener {
            if(sharedviewModel.dadosCompartilhados.value?.id == 0) {
                Toast.makeText(this,
                    "Usuário não logado ou cadastrado!",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val fragment = DadosProfissionaisFragment.newInstance("", "")
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.ContainerCadastro, fragment)
            transaction.commit()
        }

        buttonTermos = findViewById(R.id.btnTermos)
        buttonTermos.setOnClickListener {
            if(sharedviewModel.dadosCompartilhados.value?.id == 0) {
                Toast.makeText(this,
                    "Usuário não logado ou cadastrado!",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val fragment = TermosDeAdesaoFragment.newInstance("", "")
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.ContainerCadastro, fragment)
            transaction.commit()
        }
    }
    fun recuperadados(){
        var origem = intent.getStringExtra("origem")
        if(origem != "Entrada"){
            candidato = intent.getSerializableExtra("candidato") as Candidato
        }
    }
}