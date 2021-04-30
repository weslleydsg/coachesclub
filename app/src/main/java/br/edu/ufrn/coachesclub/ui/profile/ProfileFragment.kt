package br.edu.ufrn.coachesclub.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ufrn.coachesclub.R
import br.edu.ufrn.coachesclub.data.InviteKey
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val account = GoogleSignIn.getLastSignedInAccount(mContext)

        val userName = root.findViewById<TextView>(R.id.user_name)
        userName.text = account?.displayName

        val inviteKey1 = root.findViewById<TextView>(R.id.invite_key_1)
        val inviteKey2 = root.findViewById<TextView>(R.id.invite_key_2)

        val findByEmailListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.children.count() == 0) {
                    inviteKey1.text = "Falha ao carregar"
                    inviteKey2.text = "Falha ao carregar"
                } else {
                    for ((index, child) in dataSnapshot.children.withIndex()) {
                        val inviteKey = child.getValue(InviteKey::class.java)
                        if (index == 0) {
                            inviteKey1.text = inviteKey?.id
                        } else if (index == 1) {
                            inviteKey2.text = inviteKey?.id
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DEBUG", "Profile: onCancelled")
            }
        }
        val database = FirebaseDatabase.getInstance("https://coaches-club-default-rtdb.firebaseio.com/")
        val ref = database.getReference("InviteKeys").orderByChild("userEmail").equalTo(account?.email)
        ref.addListenerForSingleValueEvent(findByEmailListener)

        return root
    }
}