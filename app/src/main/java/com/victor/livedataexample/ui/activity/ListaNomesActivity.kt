package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.activity.extensions.fragmentTransaction
import com.victor.livedataexample.ui.fragments.ListaPessoasFragments
import com.victor.livedataexample.ui.fragments.VisualizaPessoaFragment
import kotlinx.android.synthetic.main.activity_pessoas.*

class ListaNomesActivity : AppCompatActivity() {

    private val abreFormulario by lazy {
        Intent(this, FormularioActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoas)

        if (savedInstanceState == null) {
            fragmentTransaction {
                replace(R.id.activity_pessoa_fragment_primaria, ListaPessoasFragments())
            }
        } else {

            retornaFragmentSelecionada()

        }

    }

    private fun retornaFragmentSelecionada() {
        val fragment = supportFragmentManager.findFragmentByTag("visualiza_pessoa")

        fragment?.let {

            val novoFragment = criaNovoFragment(fragment)

            removeFragmentAntigo(fragment)

            supportFragmentManager.popBackStack()

            criaNovoFragmentComDadosSelecionados(novoFragment)

        }
    }

    private fun criaNovoFragmentComDadosSelecionados(novoFragment: VisualizaPessoaFragment) {
        fragmentTransaction {

            val container = configuraContainerVisualizaPessoa()

            replace(container, novoFragment)
        }
    }

    private fun FragmentTransaction.configuraContainerVisualizaPessoa(): Int {
        return if (activity_pessoa_fragment_secundaria != null) {
            R.id.activity_pessoa_fragment_secundaria
        } else {
            addToBackStack(null)
            R.id.activity_pessoa_fragment_primaria
        }
    }

    private fun removeFragmentAntigo(fragment: Fragment) {
        fragmentTransaction {
            remove(fragment)
        }
    }

    private fun criaNovoFragment(fragment: Fragment): VisualizaPessoaFragment {
        val dadosDoFragmentAntigo = fragment.arguments
        val novoFragment = VisualizaPessoaFragment()
        novoFragment.arguments = dadosDoFragmentAntigo
        return novoFragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PESSOA_REQUEST_CODE
            && resultCode == PESSOA_NOVA_RESULT_CODE
            && data?.hasExtra(PESSOA) as Boolean
        ) {
            val pessoa = data.getSerializableExtra(PESSOA) as Pessoa
            //adicionaPessoa(pessoa)
        }

        if (requestCode == 100
            && resultCode == 102
            && data?.hasExtra(PESSOA) as Boolean
            && data?.hasExtra(POSICAO)
        ) {
            val pessoa = data.getSerializableExtra(PESSOA) as Pessoa
            val posicao = data.getIntExtra(POSICAO, POSICAO_DEFAULT_VALUE)
            //abrePessoa(pessoa, posicao)
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        when (fragment) {
            is ListaPessoasFragments -> listaPessoaFragment(fragment)
            is VisualizaPessoaFragment -> visualizaPessoaFragment(fragment)
        }
    }

    private fun visualizaPessoaFragment(fragment: VisualizaPessoaFragment) {
        fragment.quandoFinalizaId = this::finish

        fragment.quandoBotaoClicado = {
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra(PESSOA, it.id)
            startActivityForResult(intent, PESSOA_EDITA_RESULT_CODE)
        }
    }

    private fun listaPessoaFragment(fragment: ListaPessoasFragments) {
        fragment.quandoFabClicado = {
            abreFormularioNovaPessoa()
        }

        fragment.quandoItemSelecionado = { pessoa, posicao ->
            abreEditaPessoa(pessoa)
        }
    }


    private fun abreEditaPessoa(pessoa: Pessoa) {

        val dados = Bundle()
        dados.putLong(PESSOA, pessoa.id)

        val fragment = VisualizaPessoaFragment()
        fragment.arguments = dados



        fragmentTransaction {

            val container = configuraContainerVisualizaPessoa()

            replace(container, fragment, "visualiza_pessoa")
        }

    }

    private fun abreFormularioNovaPessoa() {
        startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)
    }


}


