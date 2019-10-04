package com.victor.livedataexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.victor.livedataexample.asynctask.BaseAsyncTask
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.model.Pessoa

class PessoaRepository(private val dao: PessoaDao) {

    private val mediator = MediatorLiveData<List<Pessoa>?>()

    //TODO("not implemented") still need to implement web service and merge with the mediator
    fun buscaTodos(): LiveData<List<Pessoa>?> {

        mediator.addSource(dao.buscaTodos()) {
            mediator.value = it
        }

        return mediator
    }

    fun adiciona(pessoa: Pessoa): LiveData<Pessoa?> {
        val mutableLiveData = MutableLiveData<Pessoa?>()
        adicionaInterno(pessoa, quandoSucesso = { it?.let { mutableLiveData.value = it } })
        return mutableLiveData
    }

    fun edita(pessoa: Pessoa): LiveData<Pessoa?> {
        val mutableLiveData = MutableLiveData<Pessoa?>()
        editaInterno(pessoa, quandoSucesso = { it?.let { mutableLiveData.value = it } })
        return mutableLiveData
    }

    fun buscaPessoa(id: Long): LiveData<Pessoa?> {
        val mutableLiveData = MutableLiveData<Pessoa?>()
        buscaPorId(id, quandoSucesso = { mutableLiveData.value = it })
        return mutableLiveData
    }

    fun deleta(pessoa: Pessoa): LiveData<Void?> {
        val mutableLiveData = MutableLiveData<Void?>()
        deletaInterno(pessoa, quandoSucesso = { mutableLiveData.value = null })
        return mutableLiveData
    }

    private fun adicionaInterno(pessoa: Pessoa, quandoSucesso: (Pessoa?) -> Unit) {
        BaseAsyncTask(
            quandoInicia = {
                dao.adiciona(pessoa)
                dao.buscaPorId(pessoa.id)
            },
            quandoFinaliza = quandoSucesso
        ).execute()
    }


    private fun editaInterno(pessoa: Pessoa, quandoSucesso: (Pessoa?) -> Unit) {
        BaseAsyncTask(
            quandoInicia = {
                dao.edita(pessoa)
                dao.buscaPorId(pessoa.id)
            },
            quandoFinaliza = quandoSucesso
        ).execute()
    }

    private fun deletaInterno(pessoa: Pessoa, quandoSucesso: () -> Unit) {
        BaseAsyncTask(
            quandoInicia = { dao.deleta(pessoa) },
            quandoFinaliza = { quandoSucesso() }).execute()
    }

    private fun buscaPorId(id: Long, quandoSucesso: (Pessoa?) -> Unit) {
        BaseAsyncTask(
            quandoInicia = { dao.buscaPorId(id) },
            quandoFinaliza = quandoSucesso
        ).execute()
    }
}