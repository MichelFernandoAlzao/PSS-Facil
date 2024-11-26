package com.example.pssfacil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.pssfacil.DAO.ProcessoDAO
import com.example.pssfacil.DAO.ReservasDAO
import com.example.pssfacil.databinding.ActivityVisualizaProcessoBinding
import com.example.pssfacil.model.Processo
import com.example.pssfacil.model.Reserva
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

            val verificareserva = ReservasDAO()
            var reserva = Reserva()
            if(verificareserva.verificareserva("1","13")){
                Toast.makeText(
                    this,
                    "Você já se candidatou para esta vaga!",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                val inserereserva = ReservasDAO()
                var reserva = Reserva()
                inserereserva.InsereCadastro("1","13")
                if(inserereserva.resultado == "Sucesso"){
                    Toast.makeText(
                        this,
                        "Reserva realizada com sucesso!",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(
                        this,
                        "Ocorreu um erro ao realizar a reserva, contate o suporte!!",
                        Toast.LENGTH_SHORT).show()
                }
            }
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