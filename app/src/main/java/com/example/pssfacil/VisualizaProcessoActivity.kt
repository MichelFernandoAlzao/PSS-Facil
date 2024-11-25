package com.example.pssfacil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pssfacil.DAO.ProcessoDAO
import com.example.pssfacil.databinding.ActivityVisualizaProcessoBinding
import com.example.pssfacil.model.Processo
import java.text.NumberFormat
import java.util.Locale

class VisualizaProcessoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVisualizaProcessoBinding

    lateinit var processo: Processo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizaProcessoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //Recupera a id passada pela tela de navegação
        //recuperadados()

        val novo = Processo()
        processo = novo
        processo.id = "1"
        //Carrega o processo a ser exibido
        carregaprocesso()

        binding.txtNomeProcesso.text = processo.nomeProcesso
        binding.txtDescricaoProcesso.text = processo.descricaoProcesso
        binding.txtorgResponsavel.text = processo.orgao_responsavel
        binding.txtRemuneracao.text = formatarMoedaPersonalizada(processo.remuneracao.toDouble())

        binding.btnAplicar.setOnClickListener() {

        }

    }

    fun carregaprocesso() {
        val carregaprocesso = ProcessoDAO()
        val processoretornado = carregaprocesso.CarregaProcesso(processo)
        if (processoretornado != null) {
            processo = processoretornado
        }
    }

//    fun recuperadados() {
//        //processo.id = intent.getStringExtra("id").toString()
//        processo.id = "1"
//    }

    fun formatarMoedaPersonalizada(valor: Double): String {
        val locale = Locale("pt", "BR") // Português do Brasil
        val formatoMoeda = NumberFormat.getCurrencyInstance(locale)
        return formatoMoeda.format(valor)
    }
}