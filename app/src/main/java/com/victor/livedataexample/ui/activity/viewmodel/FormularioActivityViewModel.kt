package com.victor.livedataexample.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.PessoaRepository

class FormularioActivityViewModel(private val repository: PessoaRepository) : ViewModel() {

    fun busca(id: Long): LiveData<Pessoa?> {
        return repository.buscaPessoa(id)
    }
}