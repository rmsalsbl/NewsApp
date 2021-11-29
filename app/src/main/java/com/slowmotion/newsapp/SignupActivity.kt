package com.slowmotion.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.slowmotion.newsapp.databinding.ActivitySignupBinding
import org.jetbrains.anko.email

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: DatabaseReference
    private var firebaseUserId: String = ""
    private lateinit var activitySignupBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(activitySignupBinding.root)
        mAuth = FirebaseAuth.getInstance()
        activitySignupBinding.ButtonSignUp.setOnClickListener(this)
    }

    companion object {
        fun getLaunchService(from: Context) = Intent(from, SignupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.ButtonSignUp -> signupUser()
        }
    }

    private fun signupUser() {
        val fullName: String = activitySignupBinding.etNameSignUp.text.toString()
        val Email: String = activitySignupBinding.etEmailSignUp.text.toString()
        val Password: String = activitySignupBinding.etPasswordSignUp.text.toString()
        val ConfirmPassword: String = activitySignupBinding.etConfirmPasswordSignUp.text.toString()

        if (fullName == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else if (Email == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else if (Password == "") {
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show()
        } else if ((ConfirmPassword == "").equals(Password)) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    firebaseUserId = mAuth.currentUser!!.uid
                    refUser = FirebaseDatabase.getInstance().reference.child("Users")
                        .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["fullName"] = fullName
                    userHashMap["email"] = Email
                    userHashMap["linkedIn"] = ""
                    userHashMap["instagram"] = ""
                    userHashMap["medium"] = ""
                    userHashMap["photo"] = ""

                    refUser.updateChildren(userHashMap).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(MainActivity.getLaunchService(this)))
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Register Failed" + it.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }

            }


        }
    }
}
