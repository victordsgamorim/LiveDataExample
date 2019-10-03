package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victor.livedataexample.R
import com.victor.livedataexample.database.AppDatabase
import com.victor.livedataexample.model.Pessoa
import com.victor.livedataexample.repository.PessoaRepository
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    private val pessoaId by lazy {
        intent.getLongExtra(PESSOA, 0)
    }

    private val posicao by lazy {
        intent.getIntExtra(POSICAO, POSICAO_DEFAULT_VALUE)
    }

    private val repository by lazy {
        val dao = AppDatabase.getInstance(context = this).pessoaDao()
        PessoaRepository(dao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        repository.buscaPorId(
            pessoaId,
            quandoSucesso = { it?.let { activity_form_nome.setText(it.nome) } })

        botaoCriaNovoAluno()

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


