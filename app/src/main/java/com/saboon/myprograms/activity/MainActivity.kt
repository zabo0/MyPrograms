package com.saboon.myprograms.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saboon.myprograms.R
import com.saboon.myprograms.di.MyProgsFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MyProgsFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}



