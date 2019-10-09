package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.fragments.ListaPessoasFragments
import com.victor.livedataexample.ui.fragments.VisualizaPessoaFragment

class ListaNomesActivity : AppCompatActivity() {

    private val abreFormulario by lazy {
        Intent(this, FormularioActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoas)

        fragmentTransaction {
            replace(R.id.activity_pessoas_fragment_container, ListaPessoasFragments())
        }

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
            replace(R.id.activity_pessoas_fragment_container, fragment)
        }

    }

    private fun abreFormularioNovaPessoa() {
        startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)
    }

    private fun fragmentTransaction(executa: FragmentTransaction.() -> Unit) {
        val transacao = supportFragmentManager.beginTransaction()
        executa(transacao)
        transacao.commit()
    }


}


