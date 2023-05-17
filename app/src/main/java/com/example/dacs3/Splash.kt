package com.example.dacs3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dacs3.SignUpLogin.Activity_Login
import kotlinx.android.synthetic.main.activity_splash.*


class Splash : AppCompatActivity() {

    //    Animations
    var sideAnim: Animation? = null
    var bottomAnim: Animation? = null
    var onBoardingScreen: SharedPreferences? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        remove top status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        //        Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        //        set Animations on elements
        imgBackground.setAnimation(sideAnim)
        txtQuanLy.setAnimation(bottomAnim)

//        function time
        Handler().postDelayed({
            onBoardingScreen =
                getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
            val intent = Intent(applicationContext, Activity_Login::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIMER.toLong())
    }

    companion object {
        private const val SPLASH_TIMER = 4000
    }
}