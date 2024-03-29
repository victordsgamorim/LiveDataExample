package com.victor.livedataexample.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.PessoaRepository
import com.victor.livedataexample.repository.resource.PessoaResource

class ListaNomesActivityViewModel(
    private val repository: PessoaRepository
) : ViewModel() {

    fun buscaTodos(): LiveData<PessoaResource<List<Pessoa>?>> {
        return repository.buscaTodos()
    }

    fun adiciona(pessoa: Pessoa): LiveData<Pessoa?> {
        return repository.adiciona(pessoa)
    }

    fun edita(pessoa: Pessoa): LiveData<Pessoa?> {
        return repository.edita(pessoa)
    }

    fun deleta(pessoa: Pessoa): LiveData<Void?> {
        return repository.deleta(pessoa)
    }

}
