package com.example.roomdb.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RandomSentencesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRandomSentence(randomSentences: RandomSentences)

    @Query("SELECT * FROM randomnsntcs")
    fun readAll() : LiveData<List<RandomSentences>>
}