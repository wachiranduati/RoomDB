package com.example.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
@Entity(tableName = "words_table")
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(words: Words)

    @Query("SELECT * FROM words_table ORDER BY id ASC")
    fun readWords() : LiveData<List<Words>>

    @Update
    suspend fun updateWord(words: Words)

    @Query("DELETE FROM words_table WHERE id = :num")
    suspend fun deleteWord(num: Int)

    @Query("DELETE FROM words_table")
    suspend fun deleteAll()

}