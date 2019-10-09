package com.victor.livedataexample.di.modules

import androidx.room.Room
import com.victor.livedataexample.database.AppDatabase
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.repository.PessoaRepository
import com.victor.livedataexample.retorfit.webclient.PessoaWebClient
import com.victor.livedataexample.ui.activity.viewmodel.FormularioActivityViewModel
import com.victor.livedataexample.ui.activity.viewmodel.ListaNomesActivityViewModel
import com.victor.livedataexample.ui.activity.viewmodel.VisualizaPessoaActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE_NOME = "pessoa.db"

val appModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NOME
        ).build()
    }

    single<PessoaDao> {
        get<AppDatabase>().pessoaDao()
    }

    single<PessoaRepository> {
        PessoaRepository(get())
    }

    viewModel<ListaNomesActivityViewModel> {
        ListaNomesActivityViewModel(get())
    }

    viewModel<FormularioActivityViewModel> {
        FormularioActivityViewModel(get())
    }

    viewModel<VisualizaPessoaActivityViewModel> { (id: Long) ->
        VisualizaPessoaActivityViewModel(id, get())
    }


}