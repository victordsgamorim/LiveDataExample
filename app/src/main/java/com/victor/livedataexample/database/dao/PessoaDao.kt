package com.victor.livedataexample.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.victor.livedataexample.model.Pessoa

@Dao
interface PessoaDao {

    @Insert
    fun adiciona(pessoa: Pessoa)

    @Delete
    fun deleta(pessoa: Pessoa)

    @Query("SELECT * FROM Pessoa")
    fun buscaTodos(): LiveData<List<Pessoa>?>

    @Query("SELECT * FROM Pessoa WHERE id = :idPessoa")
    fun buscaPorId(idPessoa: Long): Pessoa?

    @Insert(onConflict = REPLACE)
    fun edita(pessoa: Pessoa)


}