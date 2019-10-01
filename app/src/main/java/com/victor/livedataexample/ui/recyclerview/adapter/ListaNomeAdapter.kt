package com.victor.livedataexample.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import kotlinx.android.synthetic.main.cardview_item.view.*

class ListaNomeAdapter(
    private val context: Context,
    private val lista: MutableList<Pessoa> = mutableListOf(),
    var quandoItemClicado: (pessoa: Pessoa) -> Unit = {}
) :
    RecyclerView.Adapter<ListaNomeAdapter.ListaNomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaNomeViewHolder {
        val view =
            LayoutInflater
                .from(context)
                .inflate(
                    R.layout
                        .cardview_item, parent, false
                )
        return ListaNomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ListaNomeViewHolder, position: Int) {
        val pessoa = lista[position]
        holder.vincula(pessoa)
    }

    fun atualiza(pessoas: List<Pessoa>) {
        notifyItemRangeRemoved(0, this.lista.size)
        lista.clear()
        lista.addAll(pessoas)
        notifyItemRangeInserted(0, this.lista.size)
    }

    fun adiciona(pessoa: Pessoa) {
        lista.add(pessoa)
        notifyDataSetChanged()
    }

    inner class ListaNomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var pessoa: Pessoa

        init {
            itemView.setOnClickListener {
                if (::pessoa.isInitialized) {
                    quandoItemClicado(pessoa)
                }
            }
        }

        fun vincula(pessoa: Pessoa) {
            this.pessoa = pessoa
            itemView.cardview_student_id.text = pessoa.id.toString()
            itemView.cardview_student_name.text = pessoa.nome
        }
    }

}
