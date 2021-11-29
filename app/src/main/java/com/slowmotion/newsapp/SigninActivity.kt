package com.slowmotion.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.slowmotion.newsapp.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var signinBinding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signinBinding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(signinBinding.root)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        signinBinding.btnSignin.setOnClickListener(this)
        signinBinding.tvForgot.setOnClickListener(this)
        signinBinding.tvSignUp.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btnSignin -> signInEmailPassword()
            R.id.tvSignUp -> startActivity(SignupActivity.getLaunchService(this))
            R.id.tvForgot -> startActivity(ForgotpasswordActivity.getLaunchServise(this))

        }

    }

    private fun signInEmailPassword() {
        val email = signinBinding.etEmail.text.toString()
        val password = signinBinding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Isi dengan lengkap", Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(MainActivity.getLaunchService(this))
                return@addOnCompleteListener
            }else{
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Check your Internet", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()
        val userStatus = FirebaseAuth.getInstance().currentUser
        if (userStatus != null){
            startActivity(MainActivity.getLaunchService(this))
        }
    }
    companion object{
        fun getLaunchService(from : Context)= Intent(from, SigninActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        }
    }
}