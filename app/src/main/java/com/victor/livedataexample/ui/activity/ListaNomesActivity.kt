package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victor.livedataexample.R
import com.victor.livedataexample.database.AppDatabase
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.PessoaRepository
import com.victor.livedataexample.ui.recyclerview.adapter.ListaNomeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class ListaNomesActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListaNomeAdapter(this)
    }
    private val abreFormulario by lazy {
        Intent(this, FormularioActivity::class.java)
    }

    private val repository by lazy {
        val dao = AppDatabase.getInstance(this).pessoaDao()
        PessoaRepository(dao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configuraAdapter()
        atualizaLista()
        abreFormEditaPessoa()
        abreFormularioNovaPessoa()

        menuItemRemovePessoa()

    }

    override fun onResume() {
        super.onResume()
        atualizaLista()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PESSOA_REQUEST_CODE
            && resultCode == PESSOA_NOVA_RESULT_CODE
            && data?.hasExtra(PESSOA) as Boolean
        ) {
            val pessoa = data.getSerializableExtra(PESSOA) as Pessoa
            adicionaPessoa(pessoa)
        }

        if (requestCode == 100
            && resultCode == 102
            && data?.hasExtra(PESSOA) as Boolean
            && data?.hasExtra(POSICAO)
        ) {
            val pessoa = data.getSerializableExtra(PESSOA) as Pessoa
            val posicao = data.getIntExtra(POSICAO, POSICAO_DEFAULT_VALUE)
            editaPessoa(pessoa, posicao)
        }
    }


    private fun abreFormEditaPessoa() {
        adapter.quandoItemClicado = { pessoa, posicao ->
            abreFormulario.putExtra(PESSOA, pessoa.id)
            abreFormulario.putExtra(POSICAO, posicao)
            startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)
        }
    }

    private fun abreFormularioNovaPessoa() {
        activity_fab.setOnClickListener {
            startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)
        }
    }

    private fun configuraAdapter() {
        activity_lista_recyclerview.adapter = adapter
    }

    /** CRUD Room and Adapter*/
    private fun atualizaLista() {
        repository.atualiza(quandoSucesso = { adapter.atualiza(it) })
    }

    private fun adicionaPessoa(pessoa: Pessoa) {

        repository.adiciona(
            pessoa,
            quandoSucesso = {
                it?.let { adapter.adiciona(pessoa) }
            })
    }

    private fun editaPessoa(pessoa: Pessoa, posicao: Int) {
        repository.edita(
            pessoa, quandoSucesso = {
                it?.let { adapter.edita(posicao, it) }
            })
    }

    private fun menuItemRemovePessoa() {
        adapter.removeItemSelecionado = { pessoa, posicao ->
            repository.deleta(pessoa, quandoSucesso = { adapter.deleta(posicao) })
        }
    }

}


