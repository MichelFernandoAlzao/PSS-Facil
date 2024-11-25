package com.example.pssfacil.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pssfacil.Cadastro_Activity
import com.example.pssfacil.NavegacaoActivity
import com.example.pssfacil.R
import com.example.pssfacil.model.Processo

class AdapterProcesso(private val context:Context,private val processos:MutableList<Processo>,var clickProcesso:ClickProcesso): RecyclerView.Adapter<AdapterProcesso.ProcessoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessoViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.cards_de_processos,parent,false)
        val holder = ProcessoViewHolder(itemLista)
        itemLista.setOnClickListener(){
            val intent = Intent(context, Cadastro_Activity::class.java)
            //startActivity(context,intent,false)
        }

        return  holder
    }

    override fun getItemCount(): Int = processos.size



    override fun onBindViewHolder(holder: ProcessoViewHolder, position: Int) {

        val processo: Processo = processos.get(position)
        holder.logo.setImageResource(processos[position].logoOrgao)
        holder.nomeprocesso.text = processos[position].nomeProcesso
        holder.descricaoprocesso.text = processos[position].descricaoProcesso
        holder.vigenciaprocesso.text = processos[position].dataVigencia
        holder.cardViewProcesso.setOnClickListener{
            clickProcesso.clickProcesso(processo)
        }


    }

    interface ClickProcesso{
        fun clickProcesso(processo:Processo)
    }

    inner class ProcessoViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val logo = itemView.findViewById<ImageView>(R.id.logoOrgao)
        val nomeprocesso = itemView.findViewById<TextView>(R.id.nomeOrgao)
        val descricaoprocesso = itemView.findViewById<TextView>(R.id.descricaoProcesso)
        val vigenciaprocesso = itemView.findViewById<TextView>(R.id.vigenciaProcesso)
        val cardViewProcesso = itemView.findViewById<View>(R.id.cardProcesso)

    }
}