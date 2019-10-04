package com.victor.livedataexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victor.livedataexample.database.dao.PessoaDao
import com.victor.livedataexample.model.Pessoa

private const val DATABASE_NOME = "pessoa.db"

@Database(entities = [Pessoa::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pessoaDao(): PessoaDao

    companion object {

        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {

            if (::db.isInitialized) return db

            db = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NOME)
                .build()

            return db
        }
    }


}