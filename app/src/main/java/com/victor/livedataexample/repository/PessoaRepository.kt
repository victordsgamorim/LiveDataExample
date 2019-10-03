package com.victor.livedataexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victor.livedataexample.asynctask.BaseAsyncTask
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.model.Pessoa

class PessoaRepository(private val dao: PessoaDao) {

    private val listaPessoa = MutableLiveData<List<Pessoa>>()

    fun buscaTodos(): LiveData<List<Pessoa>> {
        buscaInterno(quandoSucesso = { listaPessoa.value = it })
        return listaPessoa
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

    //TODO{"not implemented"}
    fun deleta(pessoa: Pessoa): LiveData<Void?> {
        val mutableLiveData = MutableLiveData<Void?>()
        deletaInterno(pessoa, quandoSucesso = { mutableLiveData.value = null })
        return mutableLiveData
    }

    private fun buscaInterno(quandoSucesso: (List<Pessoa>) -> Unit) {
        BaseAsyncTask(
            quandoInicia = { dao.buscaTodos() },
            quandoFinaliza = quandoSucesso
        ).execute()
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

    fun buscaPorId(id: Long, quandoSucesso: (Pessoa?) -> Unit) {
        val pessoal: Pessoa?
        BaseAsyncTask(
            quandoInicia = { dao.buscaPorId(id) },
            quandoFinaliza = quandoSucesso
        ).execute()
    }
}