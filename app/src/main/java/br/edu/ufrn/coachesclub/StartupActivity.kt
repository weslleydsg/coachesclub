package br.edu.ufrn.coachesclub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            val inviteKeyInput = findViewById<TextInputLayout>(R.id.inviteKeyTextField)
            val validationText = findViewById<TextView>(R.id.incorrect_code)

            Log.d("DEBUG_APP", "Código de Convite: " + inviteKeyInput.editText?.text)

            if(true){
            }else{
                validationText.visibility = View.VISIBLE
            }

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}