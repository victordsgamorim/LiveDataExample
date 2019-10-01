package com.victor.livedataexample.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victor.livedataexample.R
import com.victor.livedataexample.database.AppDatabase
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.recyclerview.adapter.ListaNomeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class ListaNomesActivity : AppCompatActivity() {

    private val dao: PessoaDao by lazy {
        AppDatabase.getInstance(this).pessoaDao()
    }

    private val adapter by lazy {
        ListaNomeAdapter(this)
    }
    private val abreFormulario by lazy {
        Intent(this, FormularioActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configuraAdapter()
        val todos = dao.buscaTodos()
        adapter.atualiza(todos)

        acaoDoItemAdapter()
        acaoDoFab()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == 101 && data?.hasExtra("pessoa") as Boolean) {
            val pessoa = data.getSerializableExtra("pessoa") as Pessoa
            dao.adiciona(pessoa)
            adapter.adiciona(pessoa)
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
