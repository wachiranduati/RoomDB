package com.example.roomdb

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdb.persistence.RandomSentences
import com.example.roomdb.persistence.RandomSentencesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Words::class, RandomSentences::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase() : RoomDatabase() {

    abstract fun wordDao() : WordsDao
    abstract fun RandomSentencesDao() : RandomSentencesDao

    companion object{
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : WordRoomDatabase{
            var tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "words_database"
                ).addCallback(WordDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE.let { database ->
                scope.launch {
                    if (database != null) {
                        populateDatabase(database.wordDao())
                        populateRndmSentcesDB(database.RandomSentencesDao())
                    }
                }
            }
        }

        private suspend fun populateRndmSentcesDB(randomSentencesDao: RandomSentencesDao) {
            randomSentencesDao.addRandomSentence(RandomSentences("The very first sentence"))
            randomSentencesDao.addRandomSentence(RandomSentences("Second statement as well"))
            randomSentencesDao.addRandomSentence(RandomSentences("third statement as well"))
        }

        suspend fun populateDatabase(wordDao: WordsDao) {
            var word = Words("Hello",12)
            wordDao.addWord(word)
            word = Words("World!", 23)
            wordDao.addWord(word)
        }
    }
}