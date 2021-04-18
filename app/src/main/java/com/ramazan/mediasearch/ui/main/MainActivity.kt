package com.ramazan.mediasearch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.ramazan.mediasearch.R
import com.ramazan.mediasearch.core.BaseActivity
import com.ramazan.mediasearch.databinding.ActivityMainBinding
import kotlin.reflect.KClass

class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>(R.layout.activity_main){



    override fun onInitDataBinding() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun viewModelClass() = MainActivityViewModel::class


}