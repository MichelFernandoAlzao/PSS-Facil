package com.example.pssfacil.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.pssfacil.DAO.CandidatoDAO
import com.example.pssfacil.R
import com.example.pssfacil.databinding.FragmentDadosPessoaisBinding
import com.example.pssfacil.databinding.FragmentTermosDeAdesaoBinding
import com.example.pssfacil.model.Candidato
import com.example.pssfacil.model.ModelCadastro

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TermosDeAdesaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TermosDeAdesaoFragment : Fragment() {

    private lateinit var binding: FragmentTermosDeAdesaoBinding
    private val sharedViewModel: ModelCadastro by activityViewModels()
    var estados  = ArrayList<String>()



    var Candidato = Candidato()

    lateinit var textViewTermo:TextView
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermosDeAdesaoBinding.inflate(inflater, container, false)
        return binding.root
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TermosDeAdesaoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TermosDeAdesaoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Candidato = sharedViewModel.dadosCompartilhados.value!!



        //Verifica aceite dos termos
        binding.btnAceitar.setOnClickListener(){

            val verificaaceite = CandidatoDAO()
            var retorno = verificaaceite.verificaaceitetermos(Candidato)
            if(retorno.aceite_termos != ""){
                Toast.makeText (context, "Candidato já aceitou os termos em " + retorno.data_aceite + "!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val aceitatermo = CandidatoDAO()
            aceitatermo.aceitatermos(Candidato)
            if(aceitatermo.resultado == "Sucesso"){
                Toast.makeText (context, "Aceite realizado com sucesso!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}