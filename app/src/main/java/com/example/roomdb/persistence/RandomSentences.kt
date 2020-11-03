package com.example.roomdb.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "randomnsntcs")
data class RandomSentences(
    @PrimaryKey
    val content : String
)