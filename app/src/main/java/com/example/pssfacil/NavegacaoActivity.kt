package com.example.pssfacil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pssfacil.Adapter.AdapterProcesso
import com.example.pssfacil.DAO.ProcessoDAO
import com.example.pssfacil.databinding.ActivityNavegacaoBinding
import com.example.pssfacil.model.Candidato
import com.example.pssfacil.model.Processo

class NavegacaoActivity : AppCompatActivity(), AdapterProcesso.ClickProcesso {


    private lateinit var binding: ActivityNavegacaoBinding

    lateinit var candidato:Candidato
    lateinit var processoscarregados :ArrayList<Processo>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavegacaoBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)

        recuperadados()

        val processoexemplo = Processo(
            "0",
            R.drawable.curitibalogo,
            "PSS Nome Exemplo",
            "Processo seletivo da prefeitura de Curitiba mostrado como exemplo",
            "31/12/2024"
        )

        val processoexemplo2 = Processo(
            "1",
            R.drawable.pmpr,
            "PSS Nome Exemplo 2",
            "Processo seletivo da Policia Militar como exemplo",
            "25/12/2024"
        )

        val processoexemplo3 = Processo(
            "2",
            R.drawable.utfprlogo,
            "PSS Nome Exemplo 3",
            "Processo seletivo da UTFPR como exemplo",
            "25/11/2024"
        )

        val processoexemplo4 = Processo(
            "3",
            R.drawable.utfprlogo,
            "PSS Nome Exemplo 3",
            "Processo seletivo da UTFPR como exemplo",
            "25/11/2024"
        )

        val processoexemplo5 = Processo(
            "4",
            R.drawable.curitibalogo,
            "PSS Nome Exemplo",
            "Processo seletivo da prefeitura de Curitiba mostrado como exemplo",
            "31/12/2024"
        )

        val processoexemplo6 = Processo(
            "5",
            R.drawable.utfprlogo,
            "PSS Nome Exemplo 3",
            "Processo seletivo da UTFPR como exemplo",
            "25/11/2024"
        )



       val recyclerViewItens = findViewById<RecyclerView>(R.id.RecicleViewCardItens)
        recyclerViewItens.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerViewItens.setHasFixedSize(true)
        //Configura o adaptador
        var listaProcessos: MutableList<Processo> = mutableListOf()
        val adapterProcesso = AdapterProcesso(this,listaProcessos,this)
        recyclerViewItens.adapter = adapterProcesso

        //listaProcessos = processoscarregados

        listaProcessos.add(processoexemplo)
        listaProcessos.add(processoexemplo2)
        listaProcessos.add(processoexemplo3)
        listaProcessos.add(processoexemplo4)
        listaProcessos.add(processoexemplo5)
        listaProcessos.add(processoexemplo6)


        binding.btnCadastro.setOnClickListener {
            val intent = Intent(this,Cadastro_Activity::class.java)
            intent.putExtra("candidato",candidato)
            startActivity(intent)
        }

        binding.RecicleViewCardItens.setOnClickListener {
            val intent = Intent(this,VisualizaProcessoActivity::class.java)
            startActivity(intent)
        }

    }

    override fun clickProcesso(processo: Processo) {
        val intent = Intent(this,VisualizaProcessoActivity::class.java)
        intent.putExtra("id","1")
        startActivity(intent)
    }

    fun recuperadados(){
        candidato = intent.getSerializableExtra("candidato") as Candidato

        Toast.makeText(this, "Bem vindo " + candidato.nome_completo.toString(), Toast.LENGTH_SHORT).show()
    }

    fun carregalistaprocessos(){
        val processoslistados = ProcessoDAO()
        var processosrecuperados  = processoslistados.CarregaListaProcessos()

        if(processosrecuperados != null){
            processoscarregados = processosrecuperados
        }

    }

}