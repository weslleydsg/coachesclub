package br.edu.ufrn.coachesclub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            val inviteKeyInput = findViewById<TextInputLayout>(R.id.inviteKeyTextField)

            Log.d("DEBUG_APP", "Código de Convite: " + inviteKeyInput.editText?.text)

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}