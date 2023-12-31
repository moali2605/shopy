package com.example.e_commerce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.e_commerce.databinding.ActivityMainBinding
import com.example.e_commerce.intro.MyIntro
import com.example.e_commerce.services.settingsharedpreference.SettingSharedPref
import com.example.e_commerce.utility.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val settingSharedPref = SettingSharedPref.getInstance(this)
        if (settingSharedPref.readStringFromSettingSP(Constants.CURRENCY)=="null") {
            settingSharedPref.writeStringToSettingSP(Constants.CURRENCY, Constants.EGP)
        }
        if (settingSharedPref.readStringFromSettingSP(Constants.INTRO) =="null") {
            val intent = Intent(this, MyIntro::class.java)
            startActivity(intent)
            settingSharedPref.writeStringToSettingSP(Constants.INTRO,Constants.INTRO)
        }
    }

    override fun onStart() {
        super.onStart()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}