package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.victor.livedataexample.R
import com.victor.livedataexample.database.AppDatabase
import com.victor.livedataexample.factory.FormularioActivityViewModelFactory
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.PessoaRepository
import com.victor.livedataexample.ui.activity.viewmodel.FormularioActivityViewModel
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    private val pessoaId by lazy {
        intent.getLongExtra(PESSOA, 0)
    }

    private val posicao by lazy {
        intent.getIntExtra(POSICAO, POSICAO_DEFAULT_VALUE)
    }

    private val viewModel by lazy {
        val repository = PessoaRepository(AppDatabase.getInstance(this).pessoaDao())
        val factory = FormularioActivityViewModelFactory(repository)
        ViewModelProviders.of(this, factory).get(FormularioActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)


        preencheCampoSePessoaExiste()
        botaoCriaNovoAluno()

    }

    private fun preencheCampoSePessoaExiste() {
        viewModel.busca(pessoaId).observe(this, Observer {
            it?.let { activity_form_nome.setText(it.nome) }
        })
    }

    private fun botaoCriaNovoAluno() {


        activity_form_button.setOnClickListener {
            val nome = activity_form_nome.text.toString()
            salva(Pessoa(pessoaId, nome))

            finish()
        }

    }

    private fun salva(pessoa: Pessoa) {

        val intent = Intent()

        if (pessoa.id > 0) {
            intent.putExtra(PESSOA, pessoa)
            intent.putExtra(POSICAO, posicao)
            setResult(PESSOA_EDITA_RESULT_CODE, intent)
        } else {
            intent.putExtra(PESSOA, pessoa)
            setResult(PESSOA_NOVA_RESULT_CODE, intent)
        }

    }
}


