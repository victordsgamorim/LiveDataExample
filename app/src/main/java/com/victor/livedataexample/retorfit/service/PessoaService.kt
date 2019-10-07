package com.victor.livedataexample.retorfit.service

import com.victor.livedataexample.model.Pessoa
import retrofit2.Call
import retrofit2.http.GET

interface PessoaService {

    @GET("bins/f3gv3")
    fun buscaTodos(): Call<List<Pessoa>?>

}
