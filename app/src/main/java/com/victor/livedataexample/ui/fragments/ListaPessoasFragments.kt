package com.victor.livedataexample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.activity.viewmodel.ListaNomesActivityViewModel
import com.victor.livedataexample.ui.recyclerview.adapter.ListaNomeAdapter
import kotlinx.android.synthetic.main.lista_pessoa.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListaPessoasFragments : Fragment() {

    private val adapter by lazy {
        context?.let {
            ListaNomeAdapter(it)
        } ?: throw IllegalArgumentException("Contexto inválido")
    }

    private val viewModel by viewModel<ListaNomesActivityViewModel>()

    var quandoFabClicado: () -> Unit = {}
    var quandoItemSelecionado: (pessoa: Pessoa, posicao: Int) -> Unit = { _, _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        atualizaLista()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.lista_pessoa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraAdapter()
        abreFormEditaPessoa()
        abreFormularioNovaPessoa()
        activity?.title = "Pessoas"

    }

    private fun configuraAdapter() {
        lista_recyclerview.adapter = adapter
    }

    /** CRUD ViewModel and Adapter*/
    private fun atualizaLista() {
        viewModel.buscaTodos().observe(this, Observer {
            val lista = it.data
            lista?.let { adapter.atualiza(lista) }
        })
    }

    private fun adicionaPessoa(pessoa: Pessoa) {
        viewModel.adiciona(pessoa).observe(this, Observer {
            it?.let { adapter.adiciona(it) }
        })
    }

    private fun editaPessoa(pessoa: Pessoa, posicao: Int) {
        viewModel.edita(pessoa).observe(this, Observer {
            it?.let { adapter.edita(posicao, it) }
        })
    }

    private fun menuItemRemovePessoa() {
        adapter.removeItemSelecionado = { pessoa, posicao ->
            viewModel.deleta(pessoa).observe(this, Observer {
                adapter.deleta(posicao)
            })
        }
    }

    private fun abreFormularioNovaPessoa() {
        fab_novo_nome.setOnClickListener {
            quandoFabClicado()
        }
    }

    private fun abreFormEditaPessoa() {
        adapter.quandoItemClicado = { pessoa, posicao ->
            quandoItemSelecionado(pessoa, posicao)
        }
    }
}