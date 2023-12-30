package com.github.mrzahmadi.lightnote.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.github.mrzahmadi.lightnote.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}