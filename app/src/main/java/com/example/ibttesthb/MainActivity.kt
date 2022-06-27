package com.example.ibttesthb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ibttesthb.fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(supportFragmentManager.fragments.isEmpty())
            supportFragmentManager.beginTransaction().add(R.id.container, SearchFragment()).commit()
    }
}