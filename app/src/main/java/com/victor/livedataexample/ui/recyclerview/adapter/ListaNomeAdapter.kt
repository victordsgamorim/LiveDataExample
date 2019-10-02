package com.victor.livedataexample.ui.recyclerview.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.victor.livedataexample.R
import com.victor.livedataexample.model.Pessoa
import kotlinx.android.synthetic.main.cardview_item.view.*

class ListaNomeAdapter(
    private val context: Context,
    private val lista: MutableList<Pessoa> = mutableListOf(),
    var quandoItemClicado: (pessoa: Pessoa, posicao: Int) -> Unit = { _, _ -> },
    var removeItemSelecionado: (pessoa: Pessoa, posicao: Int) -> Unit = { _, _ -> }

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

    fun edita(posicao: Int, pessoa: Pessoa) {
        lista.set(posicao, pessoa)
        notifyDataSetChanged()
    }

    fun deleta(posicao: Int) {
        lista.removeAt(posicao)
        notifyDataSetChanged()
    }


    inner class ListaNomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var pessoa: Pessoa

        init {
            itemView.setOnClickListener {
                if (::pessoa.isInitialized) {
                    quandoItemClicado(pessoa, adapterPosition)
                }
            }

            itemView.setOnCreateContextMenuListener { menu, view, menuInfo ->
                MenuInflater(context).inflate(R.menu.lista_produtos_menu, menu)
                menu.findItem(R.id.menu_lista_produto_remove)
                    .setOnMenuItemClickListener {
                        removeItemSelecionado(pessoa, adapterPosition)
                        true
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
