package com.neonusa.kp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.chibatching.kotpref.Kotpref
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.MainActivity
import com.neonusa.kp.R
import com.neonusa.kp.ui.challenge.ChallengeActivity
import com.neonusa.kp.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Kotpref.init(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler().postDelayed({
            if(Kotpreference.isLogin){
                // rumah halaman utama setelah splashscreen disini
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
                              // langsung ke halaman kuis
//                            val intent = Intent(this@SplashScreen, ChallengeActivity::class.java)
//                            startActivity(intent)
//                            finish()

        },3000)
    }
}