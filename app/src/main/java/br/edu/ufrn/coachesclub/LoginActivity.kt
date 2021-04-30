package br.edu.ufrn.coachesclub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.edu.ufrn.coachesclub.data.InviteKey
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val REQUEST_CODE_SIGN_IN = 1

class LoginActivity : AppCompatActivity() {
    var inviteKeyId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inviteKeyId = intent.getStringExtra("inviteKeyId")

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_WIDE)

        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(this, gso)

        startActivityForResult(client.signInIntent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val database = FirebaseDatabase.getInstance("https://coaches-club-default-rtdb.firebaseio.com/")
            val inviteKeyRef = database.getReference("InviteKeys")

            val account = completedTask.getResult(ApiException::class.java)

            val findByEmailListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.children.count() > 0) {
                        goToMainActivity()
                    } else if (inviteKeyId != null) {
                        val findByInviteKeyListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val inviteKey = dataSnapshot.getValue(InviteKey::class.java)
                                if (inviteKey != null) {
                                    inviteKey.invalid = true
                                    inviteKeyRef.child(inviteKeyId!!).setValue(inviteKey)

                                    val inviteKey1 = InviteKey(account?.email)
                                    inviteKeyRef.child(inviteKey1.id).setValue(inviteKey1)

                                    val inviteKey2 = InviteKey(account?.email)
                                    inviteKeyRef.child(inviteKey2.id).setValue(inviteKey2)

                                    goToMainActivity()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.d("DEBUG", "Login: onCancelled")
                            }
                        }
                        val ref = inviteKeyRef.child(inviteKeyId!!)
                        ref.addListenerForSingleValueEvent(findByInviteKeyListener)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("DEBUG", "Login: onCancelled")
                }
            }
            val ref = inviteKeyRef.orderByChild("userEmail").equalTo(account?.email)
            ref.addListenerForSingleValueEvent(findByEmailListener)
        } catch (e: ApiException) {
            Log.d("DEBUG", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}