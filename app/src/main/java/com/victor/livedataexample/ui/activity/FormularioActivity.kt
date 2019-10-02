package com.victor.livedataexample.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victor.livedataexample.R
import com.victor.livedataexample.database.AppDatabase
import com.victor.livedataexample.model.Pessoa
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    private val pessoaId by lazy {
        intent.getLongExtra("pessoa", 0)
    }

    private val posicao by lazy {
        intent.getIntExtra("posicao", -1)
    }

    private val dao by lazy {
        AppDatabase.getInstance(context = this).pessoaDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)


        val pessoa = dao.buscaPorId(pessoaId)
        pessoa?.let {
            activity_form_nome.setText(pessoa.nome)
        }

        botaoCriaNovoAluno()

    }

    private fun botaoCriaNovoAluno() {


        activity_form_button.setOnClickListener{
            val nome = activity_form_nome.text.toString()
            salva(Pessoa(pessoaId, nome))

            finish()
        }

    }

    private fun salva(pessoa: Pessoa) {

        val intent = Intent()

        if (pessoa.id > 0) {
            intent.putExtra("pessoa", pessoa)
            intent.putExtra("posicao", posicao)
            setResult(102, intent)
        } else {
            intent.putExtra("pessoa", pessoa)
            setResult(101, intent)
        }

    }
}


