package com.victor.livedataexample.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pessoa(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val nome: String = ""
) : Serializable
