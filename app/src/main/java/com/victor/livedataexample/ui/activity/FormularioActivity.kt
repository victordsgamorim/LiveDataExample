package com.victor.livedataexample.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import kotlinx.android.synthetic.main.activity_formulario.*

class FormularioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        if (intent.hasExtra("pessoa")) {
            val pessoa = intent.getSerializableExtra("pessoa") as Pessoa
        }

        botaoCriaNovoAluno()


    }

    private fun botaoCriaNovoAluno() {
        activity_form_button.setOnClickListener {
            val novoAluno = criaNovoAluno()

            val intent = Intent()
            intent.putExtra("pessoa", novoAluno)
            setResult(101, intent)
            finish()
        }
    }

    private fun criaNovoAluno(): Pessoa {
        val nomeDoAluno = activity_form_nome.text.toString()
        return Pessoa(1, nomeDoAluno)
    }
}
