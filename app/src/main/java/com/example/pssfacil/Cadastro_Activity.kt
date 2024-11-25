package com.example.pssfacil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pssfacil.Fragments.DadosAcademicos_Fragment
import com.example.pssfacil.Fragments.DadosPessoais_Fragment
import com.example.pssfacil.Fragments.DadosProfissionaisFragment
import com.example.pssfacil.Fragments.TermosDeAdesaoFragment
import com.example.pssfacil.model.ModelCadastro
import com.google.android.material.tabs.TabItem
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
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
        //enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)


        recuperadados()
        if(candidato.id != 0){
         sharedviewModel.dadosCompartilhados.value = candidato
        }

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
        candidato = intent.getSerializableExtra("candidato") as Candidato
    }
}