package com.example.roomdb

class WordRepository(private val wordsDao: WordsDao) {
    val allWords = wordsDao.readWords()

    suspend fun insert(words: Words){
        wordsDao.addWord(words)
    }

    suspend fun update(words: Words){
        wordsDao.updateWord(words)
    }

    suspend fun delete(pos: Int){
        wordsDao.deleteWord(pos)
    }

    suspend fun deleteAll(){
        wordsDao.deleteAll()
    }
}