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
        atualizaLista()

        abreFormEditaPessoa()
        abreFormularioNovaPessoa()

    }

    override fun onResume() {
        super.onResume()
        atualizaLista()
    }

    private fun atualizaLista() {
        val todos = dao.buscaTodos()
        adapter.atualiza(todos)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100
            && resultCode == 101
            && data?.hasExtra("pessoa") as Boolean
        ) {
            val pessoa = data.getSerializableExtra("pessoa") as Pessoa
            adicionaPessoa(pessoa)
        }

        if (requestCode == 100
            && resultCode == 102
            && data?.hasExtra("pessoa") as Boolean
            && data?.hasExtra("posicao")
        ) {
            val pessoa = data.getSerializableExtra("pessoa") as Pessoa
            val posicao = data.getIntExtra("posicao", -1)
            editaPessoa(pessoa, posicao)
        }


    }

    private fun editaPessoa(pessoa: Pessoa, posicao: Int) {
        dao.edita(pessoa)
        adapter.edita(posicao, pessoa)
    }

    private fun adicionaPessoa(pessoa: Pessoa) {
        dao.adiciona(pessoa)
        adapter.adiciona(pessoa)
    }

    private fun abreFormEditaPessoa() {
        adapter.quandoItemClicado = { pessoa, posicao ->
            abreFormulario.putExtra("pessoa", pessoa.id)
            abreFormulario.putExtra("posicao", posicao)
            startActivityForResult(abreFormulario, 100)
        }
    }

    private fun abreFormularioNovaPessoa() {
        activity_fab.setOnClickListener {
            startActivityForResult(abreFormulario, 100)
        }
    }

    private fun configuraAdapter() {
        activity_lista_recyclerview.adapter = adapter
    }
}


