package br.edu.ufrn.coachesclub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.edu.ufrn.coachesclub.data.InviteKey
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            val inviteKeyInput = findViewById<TextInputLayout>(R.id.inviteKeyTextField)

            validateInviteKey(inviteKeyInput.editText?.text.toString())
        }

        val goToLogin = findViewById<Button>(R.id.go_to_login)
        goToLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideErrorText() {
        val validationText = findViewById<TextView>(R.id.invite_key_invalid)
        validationText.visibility = View.GONE
    }

    private fun showErrorText(text: String) {
        val validationText = findViewById<TextView>(R.id.invite_key_invalid)
        validationText.text = text
        validationText.visibility = View.VISIBLE
    }

    private fun validateInviteKey(inviteKeyId: String) {
        hideErrorText()

        val findByInviteKeyListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val inviteKey = dataSnapshot.getValue(InviteKey::class.java)

                if (inviteKey == null || inviteKey.invalid) {
                    showErrorText(getString(R.string.invite_key_invalid))
                } else {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    intent.putExtra("inviteKeyId", inviteKeyId)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showErrorText(getString(R.string.invite_key_unable))
            }
        }
        val database = FirebaseDatabase.getInstance("https://coaches-club-default-rtdb.firebaseio.com/")
        val ref = database.getReference("InviteKeys").child(inviteKeyId)
        ref.addListenerForSingleValueEvent(findByInviteKeyListener)
    }
}