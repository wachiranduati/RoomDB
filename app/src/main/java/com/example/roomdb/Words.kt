package com.example.roomdb

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "words_table", indices = [Index(value = ["id"], unique = true)])
data class Words(
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "numbers")
    val numbers : Int){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public var id : Int = 0
}