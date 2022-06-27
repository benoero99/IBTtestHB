package com.example.ibttesthb.fakedb

// It is just a fake database because I didn't want to make it more complex
class Database private constructor() {
    //Singleton pattern
    var questionDao = QuestionDao()
        private set


    companion object {
        @Volatile private var instance: Database? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Database().also { instance = it }
            }
    }
}