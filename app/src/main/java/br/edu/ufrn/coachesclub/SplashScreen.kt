package br.edu.ufrn.coachesclub

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi

class SplashScreen : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val settings = getSharedPreferences(getString(R.string.app_prefs), 0)

            if (settings.getBoolean("is_first_start", true)) {
                val intent = Intent(applicationContext, StartupActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

            finish()
        }, 500)
    }
}