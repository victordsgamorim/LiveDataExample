package com.victor.livedataexample.retorfit.webclient

import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.retorfit.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PessoaWebClient {

    private val MENSAGEM_ERRO = "Erro ao tentar realizar conexao com o banco de dados"
    private val service = AppRetrofit().pessoaService


    private fun <T> quandoExecutaCall(
        call: Call<T>,
        quandoSucesso: (resultado: T?) -> Unit,
        quandoErro: (String?) -> Unit
    ) {

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    quandoSucesso(response.body())
                } else {
                    quandoErro(MENSAGEM_ERRO)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                quandoErro("$MENSAGEM_ERRO - ${t.message}")
            }
        })

    }

    fun buscaTodos(
        quandoSucesso: (resultado: List<Pessoa>?) -> Unit,
        quandoErro: (String?) -> Unit
    ) {
        quandoExecutaCall(service.buscaTodos(), quandoSucesso, quandoErro)
    }
}