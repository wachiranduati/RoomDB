package com.example.roomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application){
    private val repository : WordRepository

    val allWords : LiveData<List<Words>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insertWord(words: Words) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(words)
    }

    fun deleteall() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

    fun update(words: Words) = viewModelScope.launch(Dispatchers.IO){
        repository.update(words)
    }
    fun deleteOne(pos: Int) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(pos)
    }
}