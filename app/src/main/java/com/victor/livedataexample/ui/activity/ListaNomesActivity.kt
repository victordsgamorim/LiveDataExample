package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_pessoas_fragment_container, ListaPessoasFragments())
            .commit()

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

        if (fragment is ListaPessoasFragments) {
            fragment.quandoFabClicado = {
                abreFormularioNovaPessoa()
            }

            fragment.quandoItemSelecionado = { pessoa, posicao ->
                abreEditaPessoa(pessoa)
            }
        }

        if (fragment is VisualizaPessoaFragment) {
            fragment.quandoFinalizaId = { finish() }
            fragment.quandoBotaoClicado = {
                val intent = Intent(this, FormularioActivity::class.java)
                intent.putExtra(PESSOA, it.id)
                startActivityForResult(intent, PESSOA_EDITA_RESULT_CODE)
            }
        }
    }


    private fun abreEditaPessoa(pessoa: Pessoa) {

        val dados = Bundle()
        dados.putLong(PESSOA, pessoa.id)

        val fragment = VisualizaPessoaFragment()
        fragment.arguments = dados


        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_pessoas_fragment_container, fragment)
            .commit()

    }

    private fun abreFormularioNovaPessoa() {
        startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)
    }


}


