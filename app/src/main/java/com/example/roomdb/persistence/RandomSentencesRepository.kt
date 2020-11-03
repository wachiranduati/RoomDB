package com.example.roomdb.persistence

import androidx.lifecycle.LiveData

class RandomSentencesRepository(private val randomSentencesDao: RandomSentencesDao) {
    var allSentences = randomSentencesDao.readAll()

    suspend fun insertRandomSentence(randomSentences: RandomSentences){
        randomSentencesDao.addRandomSentence(randomSentences)
    }
}