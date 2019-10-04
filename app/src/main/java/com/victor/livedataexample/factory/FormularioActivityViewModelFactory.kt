package com.victor.livedataexample.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.livedataexample.repository.PessoaRepository
import com.victor.livedataexample.ui.activity.viewmodel.FormularioActivityViewModel

class FormularioActivityViewModelFactory(
    private val repository: PessoaRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormularioActivityViewModel(repository) as T
    }
}