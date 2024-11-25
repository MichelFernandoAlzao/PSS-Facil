package com.example.pssfacil.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pssfacil.Cadastro_Activity
import com.example.pssfacil.DAO.CandidatoDAO
import com.example.pssfacil.DAO.EstadosDAO
import com.example.pssfacil.R
import com.example.pssfacil.api.Endpoint
import com.example.pssfacil.model.Candidato
import com.example.pssfacil.model.Endereco
import com.example.pssfacil.model.ModelCadastro
import com.example.pssfacil.util.NetworkUtil
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import com.example.pssfacil.databinding.FragmentDadosPessoaisBinding
import kotlinx.coroutines.flow.callbackFlow

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DadosPessoais_Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentDadosPessoaisBinding
    private val sharedViewModel: ModelCadastro by activityViewModels()
    var estados  = ArrayList<String>()



    var Candidato = Candidato()

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

        val teste  = arguments?.getString("param1")
        binding = FragmentDadosPessoaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Preparando para buscar UFs do banco de dados
        var estadoDAO = EstadosDAO()

        //Preparando para carregar generos e estado civil/* Como são poucos valores e imutaveis foi definido um xmlresource no package de recursos para guardar string de genero possiveis
        //Adapter do Spinner de genero sera montado sobre esse recurso
        popSpinnerGenero()
        //Adapter do Spinner de Estado Civil sera montado sobre esse recurso
        popSpinnerEstadoCivil()

        //CarregaEstados do banco
        estados = estadoDAO.ConsultaEstadosUF("")

        //Popula Spinner com dados recebidos
        carregaEstados(estados)

        //Se já tiverem dados preechidos coloca na tela, estes dados são mantidos somente enquanto a Activity Candidato estiver rodando
        sharedViewModel.dadosCompartilhados.observe(viewLifecycleOwner){
            Candidato = sharedViewModel.dadosCompartilhados.value!!
            if(Candidato.id != 0 && binding.edtTextNome.text.toString() == "")
            populaCampos(Candidato)
        }

        //Aqui começam os Listners para pegar quando o usuário sai do campo, nesse momento deve salvar o dado no sharedViewModel para não perder quando ocorre a troca de fragment
        binding.edtTextNome.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.nome_completo = binding.edtTextNome.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtNomePai.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.nome_pai = binding.edtTxtNomePai.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtNomeMae.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.nome_mae = binding.edtTxtNomeMae.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtDataNasc.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.data_nascimento = binding.edtDataNasc.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.spinGenero.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.sexo = binding.spinGenero.id.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.spinEstadoCivil.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.estadoCivil = binding.spinEstadoCivil.id.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtCPF.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.cpf = binding.edtTxtCPF.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtRG.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.rg = binding.edtTxtRG.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtCEP.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                buscadados(binding.edtTxtCEP.text.toString())
                Candidato.cep = binding.edtTxtCEP.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtLogradouro.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.logradouro = binding.edtTxtLogradouro.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtNumero.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.numero = binding.edtTxtNumero.text.toString().toInt()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtPais.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.pais = binding.edtTxtPais.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.spinGenero.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.sexo = binding.spinGenero.selectedItem.toString().substring(0,1)
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.spinEstadoCivil.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.estadoCivil = binding.spinEstadoCivil.selectedItem.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.edtTxtEmail.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.email = binding.edtTxtEmail.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.editTextPhone.onFocusChangeListener = View.OnFocusChangeListener{v,hasFocus->
            if(hasFocus == false){
                Candidato.telefone = binding.editTextPhone.text.toString()
                sharedViewModel.dadosCompartilhados.value = Candidato
            }
        }

        binding.btnCadastrar.setOnClickListener(){
            //Verificação se já não existe cadastro com este cpf ou email
            val verificarcadastro = CandidatoDAO()
            if(verificarcadastro.Verificacadastroexistente(Candidato)){
                Toast.makeText (context, "Email ou CPF já cadastrados!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Inserção do cadastro com teste de sucesso
            val cadastrar = CandidatoDAO()
            cadastrar.InsereCadastro(Candidato)
            if(cadastrar.resultado == "Sucesso"){
                //Antes de sinalisar pega nova id para caso precise lidar nas outras abas de dados academicos/profissionais ou aceite de termos
                val pegaid = CandidatoDAO()
                var idretorno = Candidato()
                idretorno =  pegaid.buscaidCandidato(Candidato)
                Candidato.id = idretorno.id
                //Salvando no sharedviewmodel para que outros fragments tenham acesso aos novos dados
                sharedViewModel.dadosCompartilhados.value = Candidato
                Toast.makeText (context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText (context, "Ocorreu um erro ao realizar o cadastro, verifique os dados e tente novamente!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DadosPessoais_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DadosPessoais_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //Cosnumo da API ViaCEP que busca o endereço do usuário com base no CEP digitado
    private fun buscadados(cep:String){
        val retrofitClient = NetworkUtil.getRetrofitInstance("https://viacep.com.br/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getEndereco(cep).enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response:Response<JsonObject>) {

                var data = mutableListOf<String>()

                val retorno = response.body()?.toString()
                val gson = Gson()
                val endereco:Endereco = gson.fromJson(retorno, Endereco::class.java)

                if(endereco != null){
                    popendereco(endereco)
                }
                println(retorno)
            }

            override fun onFailure(p0: Call<JsonObject>, p1: Throwable) {
                Toast.makeText (context, "Erro ao obter dados do CEP!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    //Se API retornou endereço usa este metodo para popular os campos da tela, posteriro a isso os dados serão salvos no sharedViewModel
    private fun popendereco(endereco: Endereco){
        if(endereco.logradouro != "")
            binding.edtTxtLogradouro.setText(endereco.logradouro.toString())
        if(binding.edtTxtPais.text.toString() == "" && Candidato.pais != "")
            binding.edtTxtPais.setText(endereco.localidade.toString())
        if(binding.edtTxtNumero.text.toString() == "" || binding.edtTxtNumero.text.toString().toInt() == 0)
            binding.edtTxtNumero.setText("")
    }


    //Este metodo tem como função popular os campos quando fragment aberto a partir de usuário logado ou usuário que esta realizando candidato
    private fun populaCampos(candidato: Candidato){

        val carregadados = CandidatoDAO()
        val retorno = carregadados.CarregaCandidato(Candidato)
        if(retorno != null){
            sharedViewModel.dadosCompartilhados.value = retorno
        }


        //pega a posição do genero para setar no spinner
        val generos = resources.getStringArray((R.array.Generos))
        val posGenero = generos.indexOf(Candidato.sexo)

        //pega a posição do estado civil para setar no spinner
        val estadocivil = resources.getStringArray((R.array.EstadoCivil))
        val posEstadoCivil = generos.indexOf(Candidato.estadoCivil)

        val posEstado = estados.indexOf(Candidato.uf)


        if(sharedViewModel.dadosCompartilhados.value?.nome_completo.toString() != "")
            binding.edtTextNome.setText(sharedViewModel.dadosCompartilhados.value?.nome_completo.toString() )

        if(sharedViewModel.dadosCompartilhados.value?.nome_pai.toString() != "")
            binding.edtTxtNomePai.setText(sharedViewModel.dadosCompartilhados.value?.nome_pai.toString())

        if(sharedViewModel.dadosCompartilhados.value?.nome_mae.toString() != "")
            binding.edtTxtNomeMae.setText(sharedViewModel.dadosCompartilhados.value?.nome_mae.toString())

        if(sharedViewModel.dadosCompartilhados.value?.data_nascimento.toString() != "")
            binding.edtDataNasc.setText(sharedViewModel.dadosCompartilhados.value?.data_nascimento.toString())

        if(sharedViewModel.dadosCompartilhados.value?.cpf.toString() != "")
            binding.edtTxtCPF.setText(sharedViewModel.dadosCompartilhados.value?.cpf.toString())

        if(sharedViewModel.dadosCompartilhados.value?.rg.toString() != "")
            binding.edtTxtRG.setText(sharedViewModel.dadosCompartilhados.value?.rg.toString())

        if(sharedViewModel.dadosCompartilhados.value?.logradouro.toString() != "")
            binding.edtTxtLogradouro.setText(sharedViewModel.dadosCompartilhados.value?.logradouro.toString())

        if(sharedViewModel.dadosCompartilhados.value?.numero != 0)
            binding.edtTxtNumero.setText(sharedViewModel.dadosCompartilhados.value?.numero.toString())

        if(sharedViewModel.dadosCompartilhados.value?.cep.toString() != "")
            binding.edtTxtCEP.setText(sharedViewModel.dadosCompartilhados.value?.cep.toString())

        if(sharedViewModel.dadosCompartilhados.value?.email.toString() != "")
            binding.edtTxtEmail.setText(sharedViewModel.dadosCompartilhados.value?.email.toString())

        if(sharedViewModel.dadosCompartilhados.value?.telefone.toString() != "")
            binding.editTextPhone.setText(sharedViewModel.dadosCompartilhados.value?.telefone.toString())

        if(sharedViewModel.dadosCompartilhados.value?.sexo != "")
            binding.spinGenero.setSelection(posGenero)

        if(candidato.estadoCivil != "")
            binding.spinEstadoCivil.setSelection(posGenero)

        if(candidato.uf != "")
            binding.spnEstados.setSelection(posEstado)

        if(sharedViewModel.dadosCompartilhados.value?.pais !="")
            binding.edtTxtPais.setText(sharedViewModel.dadosCompartilhados.value?.pais)

    }

    //Este metodo carrega o Spinner de estados com seu estado especifico
    private fun carregaEstados(estados:MutableList<String>){
        val adapterUF = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,estados)
        binding.spnEstados.adapter = adapterUF
    }

    private fun popSpinnerGenero(){
        var arraygeneros = resources.getStringArray(R.array.Generos)
        var generos = resources.getStringArray(R.array.Generos)
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,arraygeneros)
        binding.spinGenero.adapter = adapter
    }

    private fun popSpinnerEstadoCivil(){
        var estadocivil = resources.getStringArray(R.array.EstadoCivil)
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,estadocivil)
        binding.spinEstadoCivil.adapter = adapter
    }
}