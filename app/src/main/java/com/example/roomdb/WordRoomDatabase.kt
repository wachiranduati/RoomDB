package com.example.roomdb

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Words::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase() : RoomDatabase() {

    abstract fun wordDao() : WordsDao

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
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE.let { database ->
                scope.launch {
                    if (database != null) {
                        populateDatabase(database.wordDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordsDao) {
            wordDao.deleteAll()
            var word = Words("Hello",12)
            wordDao.addWord(word)
            word = Words("World!", 23)
            wordDao.addWord(word)
        }
    }
}