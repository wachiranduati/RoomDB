package com.example.roomdb.persistence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdb.R
import com.example.roomdb.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondaryActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : RandomSentencesRepository
    var allrndSntcs : LiveData<List<RandomSentences>>

    init {
        val rndomSentencesDao = WordRoomDatabase.getDatabase(application, viewModelScope).RandomSentencesDao()
        repository = RandomSentencesRepository(rndomSentencesDao)
        allrndSntcs = repository.allSentences
    }

    fun insertRndSntc(randsntc : RandomSentences) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRandomSentence(randsntc)
    }
}