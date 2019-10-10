package com.victor.livedataexample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.activity.PESSOA
import com.victor.livedataexample.ui.activity.viewmodel.VisualizaPessoaActivityViewModel
import kotlinx.android.synthetic.main.visualiza_pessoa.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VisualizaPessoaFragment : Fragment() {

    private val pessoaId by lazy {
        arguments?.getLong(PESSOA)
            ?: throw IllegalArgumentException("Não pode encontrar a chave da Pessoa")
    }

    private val viewModel by viewModel<VisualizaPessoaActivityViewModel> { parametersOf(pessoaId) }

    var quandoFinalizaId: () -> Unit = {}
    var quandoBotaoClicado: (pessoa: Pessoa) -> Unit = {}


    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verificaId()
        buscaPessoa()

    }

    private fun verificaId() {
        if (pessoaId > 0L) {
            quandoFinalizaId
        }
    }

    private fun buscaPessoa() {
        viewModel.busca.observe(this, Observer {
            it?.let {
                visualiza_pessoa_nome.text = it.nome
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.visualiza_pessoa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Pessoa"

        visualiza_pessoa_editar_botao.setOnClickListener(View.OnClickListener {
            viewModel.busca.value?.let {quandoBotaoClicado(it) }
        })


    }
}