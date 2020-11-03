package com.example.roomdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb.R
import com.example.roomdb.persistence.RandomSentences
import com.example.roomdb.persistence.SecondaryActivityViewModel
import kotlinx.android.synthetic.main.activity_secondary.*

class SecondaryActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var secActViewModel: SecondaryActivityViewModel
    var allRndSnt = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)
        secActViewModel = ViewModelProvider(this).get(SecondaryActivityViewModel::class.java)
        readAllRandomWords()
        InsertButtonRndm.setOnClickListener(this)
    }

    private fun readAllRandomWords() {
        secActViewModel.allrndSntcs.observe(this, Observer {
            allRndSnt = ""
            if(it.isNotEmpty()){
                it.forEach {rndSentence ->
                    allRndSnt += "${rndSentence.content} /n"
                }
                randomSentencesTextView.text = allRndSnt
            }
        })
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.InsertButtonRndm){
            secActViewModel.insertRndSntc(RandomSentences(createEdtTxtRndm.text.toString()))
        }
    }
}