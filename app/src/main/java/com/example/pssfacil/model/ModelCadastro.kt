package com.example.pssfacil.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelCadastro:ViewModel() {
    val dadosCompartilhados = MutableLiveData<Candidato>()
    private val mutableSelectedItem = MutableLiveData<Candidato>()
    val selectedItem: LiveData<Candidato> get() = mutableSelectedItem

    fun selectItem(item: Candidato) {
        mutableSelectedItem.value = item
    }
}