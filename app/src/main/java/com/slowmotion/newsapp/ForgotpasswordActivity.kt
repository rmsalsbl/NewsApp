package com.slowmotion.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.slowmotion.newsapp.databinding.ActivityForgotpasswordBinding

class ForgotpasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var  forgotpasswordBinding: ActivityForgotpasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotpasswordBinding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(forgotpasswordBinding.root)
        supportActionBar?.hide()
        forgotpasswordBinding.fbForgot.setOnClickListener(this)
    }
    companion object{
    fun getLaunchServise(from : Context) = Intent(from, ForgotpasswordActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.fb_forgot -> forgotpassword()
        }

    }

    private fun forgotpassword() {
        mAuth = FirebaseAuth.getInstance()
        val email = forgotpasswordBinding.etEmailForgot.text.toString()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this, "Check email to reset Password", Toast.LENGTH_SHORT).show()
                    startActivity(SigninActivity.getLaunchService(this))
                }else{
                    Toast.makeText(this, "Failed to reset Password", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}