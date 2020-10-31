package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var wordViewModel: WordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        readDb()


        InsertButton.setOnClickListener(this)
        updateButon.setOnClickListener(this)
        deleteWordsBtn.setOnClickListener(this)

    }

    private fun readDb() {
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            var dbContent : String = ""
            words.forEach {
                dbContent += "${it.word}[${it.id}] "
            }
            readDbTxtVw.text = dbContent
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.InsertButton -> insertNewWord()
            R.id.updateButon -> updateWord()
            R.id.deleteWordsBtn -> deleteErr()
        }
    }

    private fun deleteErr() {
        val position : Int = positionEdtTxt.text.toString().toInt()
        wordViewModel.deleteOne(position)
    }

    private fun updateWord() {
        val updtxt : String = updateTextTxtEdtTxt.text.toString()
        val updateWord : Words = Words(updtxt, updtxt.length)
        wordViewModel.update(updateWord)
    }

    private fun insertNewWord() {
        val textCreate : String = createEdtTxt.text.toString()
        val words : Words = Words(textCreate, textCreate.length)
        wordViewModel.insertWord(words)
    }

}