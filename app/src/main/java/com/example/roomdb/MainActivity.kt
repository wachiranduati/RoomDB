package com.example.roomdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb.ui.SecondaryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var wordViewModel: WordViewModel
    var lastId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        readDb()


        InsertButton.setOnClickListener(this)
        updateButon.setOnClickListener(this)
        deleteWordsBtn.setOnClickListener(this)
        nextScreenButton.setOnClickListener(this)

    }

    private fun readDb() {
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            var dbContent : String = ""
            if(words.isNotEmpty()){
                lastId = words.last().id
                words.forEach {
                    dbContent += "${it.word}[${it.id}] "
                }
                readDbTxtVw.text = dbContent
            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.InsertButton -> insertNewWord()
            R.id.updateButon -> updateWord()
            R.id.deleteWordsBtn -> deleteErr()
            R.id.nextScreenButton -> startActivity(Intent(this, SecondaryActivity::class.java))
        }
    }

    private fun deleteErr() {
        val position : Int = positionEdtTxt.text.toString().toInt()
        wordViewModel.deleteOne(position)
    }

    private fun updateWord() {
        val updtxt : String = updateTextTxtEdtTxt.text.toString()
        val updateWord = Words(updtxt, updtxt.length)
        // we have to define the word id being updated -> for now we'll set it to the latest word
        updateWord.id = lastId
        wordViewModel.update(updateWord)
    }

    private fun insertNewWord() {
        val textCreate : String = createEdtTxt.text.toString()
        val words : Words = Words(textCreate, textCreate.length)
        wordViewModel.insertWord(words)
    }

}