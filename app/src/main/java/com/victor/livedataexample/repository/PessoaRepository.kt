package com.victor.livedataexample.repository

import com.victor.livedataexample.asynctask.BaseAsyncTask
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.model.Pessoa

class PessoaRepository(private val dao: PessoaDao) {

    fun atualiza(quandoSucesso: (List<Pessoa>) -> Unit) {
        BaseAsyncTask(
            quandoInicia = { dao.buscaTodos() },
            quandoFinaliza = quandoSucesso
        ).execute()
    }

    fun adiciona(pessoa: Pessoa, quandoSucesso: (Pessoa?) -> Unit) {
        BaseAsyncTask(
            quandoInicia = {
                dao.adiciona(pessoa)
                dao.buscaPorId(pessoa.id)
            },
            quandoFinaliza = quandoSucesso
        ).execute()
    }

    fun edita(pessoa: Pessoa, quandoSucesso: (Pessoa?) -> Unit) {
        BaseAsyncTask(
            quandoInicia = {
                dao.edita(pessoa)
                dao.buscaPorId(pessoa.id)
            },
            quandoFinaliza = quandoSucesso
        ).execute()
    }

    fun deleta(pessoa: Pessoa, quandoSucesso: () -> Unit) {
        BaseAsyncTask(
            quandoInicia = { dao.deleta(pessoa) },
            quandoFinaliza = { quandoSucesso() }).execute()
    }

    fun buscaPorId(id: Long, quandoSucesso: (Pessoa?) -> Unit) {
        BaseAsyncTask(
            quandoInicia = { dao.buscaPorId(id) },
            quandoFinaliza = quandoSucesso ).execute()
}
}