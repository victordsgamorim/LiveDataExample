package com.victor.livedataexample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.recyclerview.adapter.ListaNomeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListaNomeAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val p1 = Pessoa(1, "Victor")
        val p2 = Pessoa(2, "Amorim")

        val dadosDoBancoDeDados = listOf(p1, p2)

        configuraAdapter()
        adapter.atualiza(dadosDoBancoDeDados)

        adapter.quandoItemClicado = { it: Pessoa ->
            Log.i("Pessoa", it.nome)
        }

    }

    private fun configuraAdapter() {
        activity_lista_recyclerview.adapter = adapter
    }
}
