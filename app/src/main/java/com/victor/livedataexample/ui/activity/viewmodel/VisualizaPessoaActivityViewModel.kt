package com.victor.livedataexample.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.PessoaRepository

class VisualizaPessoaActivityViewModel(
    private val id: Long,
    private val repository: PessoaRepository
) : ViewModel() {

    val busca: LiveData<Pessoa?> = repository.buscaPessoa(id)

}