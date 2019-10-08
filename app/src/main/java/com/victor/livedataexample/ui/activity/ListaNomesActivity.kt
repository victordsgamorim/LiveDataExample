package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.ui.activity.fragments.ListaNomesFragments

class ListaNomesActivity : AppCompatActivity() {

    private val abreFormulario by lazy {
        Intent(this, FormularioActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        if (fragment is ListaNomesFragments) {
            fragment.quandoFabClicado = {
                abreFormularioNovaPessoa()
            }

            fragment.quandoItemSelecionado = { pessoa, posicao ->
                abreEditaPessoa(pessoa, posicao)
            }
        }
    }


    private fun abreEditaPessoa(pessoa: Pessoa, posicao: Int) {
        abreFormulario.putExtra(PESSOA, pessoa.id)
        abreFormulario.putExtra(POSICAO, posicao)
        startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)

    }

    private fun abreFormularioNovaPessoa() {
        startActivityForResult(abreFormulario, PESSOA_REQUEST_CODE)
    }


}


