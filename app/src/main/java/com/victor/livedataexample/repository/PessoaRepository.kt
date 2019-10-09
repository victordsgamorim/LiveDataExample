package com.victor.livedataexample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.victor.livedataexample.asynctask.BaseAsyncTask
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.resource.PessoaResource
import com.victor.livedataexample.retorfit.webclient.PessoaWebClient

class PessoaRepository(private val dao: PessoaDao) {

    private val webClient by lazy {
        PessoaWebClient()
    }

    private val mediator = MediatorLiveData<PessoaResource<List<Pessoa>?>>()

    fun buscaTodos(): LiveData<PessoaResource<List<Pessoa>?>> {

        mediator.addSource(dao.buscaTodos()) {
            mediator.value = PessoaResource(data = it, erro = null)
        }

        val falhasDaApi = MutableLiveData<PessoaResource<List<Pessoa>?>>()
        mediator.addSource(falhasDaApi) {
            val resourceAtual = mediator.value

            val listaDaApi: PessoaResource<List<Pessoa>?> =
                if (resourceAtual != null) {
                    PessoaResource(data = resourceAtual.data, erro = it.erro)
                } else {
                    it
                }

            mediator.value = listaDaApi
        }

        buscaTodosApi(quandoErro = {
            falhasDaApi.value = PessoaResource(data = null, erro = it)
        })

        return mediator
    }

    fun buscaPorId(id: Long): LiveData<Pessoa?> {
        val pessoaEncontrada = MutableLiveData<Pessoa?>()

        buscaPorId(id, quandoSucesso = { pessoaEncontrada.value = it })

        return pessoaEncontrada
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

    private fun buscaTodosApi(quandoErro: (String?) -> Unit) {
        webClient.buscaTodos(quandoSucesso = {
            it?.let { salvaListaInterna(it) }
        }, quandoErro = { quandoErro(it) })
    }

    private fun salvaListaInterna(lista: List<Pessoa>) {
        BaseAsyncTask(quandoInicia = {
            dao.salvaLista(lista)
        }, quandoFinaliza = {})
            .execute()
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