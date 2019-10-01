package com.victor.livedataexample.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.recyclerview.adapter.ListaNomeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class ListaNomesActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListaNomeAdapter(this)
    }
    private val abreFormulario by lazy {
        Intent(this, FormularioActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val p1 = Pessoa(1, "Victor")
        val p2 = Pessoa(2, "Amorim")

        val dadosDoBancoDeDados = listOf(p1, p2)

        configuraAdapter()
        adapter.atualiza(dadosDoBancoDeDados)

        acaoDoItemAdapter()
        acaoDoFab()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if (requestCode == 100 && resultCode == 101 && data?.hasExtra("pessoa") as Boolean) {
            val pessoa = data.getSerializableExtra("pessoa") as Pessoa
            //adiciona novo Nome
        }


    }

    private fun acaoDoItemAdapter() {
        adapter.quandoItemClicado = {
            abreFormulario.putExtra("pessoa", it)
            startActivityForResult(abreFormulario, 100)
        }
    }

    private fun acaoDoFab() {
        activity_fab.setOnClickListener {
            startActivityForResult(abreFormulario, 100)
        }
    }

    private fun configuraAdapter() {
        activity_lista_recyclerview.adapter = adapter
    }
}
