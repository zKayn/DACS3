package com.example.dacs3.SignUpLogin

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import com.example.dacs3.R
import android.content.Intent
import android.view.View
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.dacs3.MainActivity
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_login.*

class Activity_Login : AppCompatActivity() {
    var loginUsername: EditText? = null
    var loginPassword: EditText? = null
    var loginButton: Button? = null
    var signupRedirectText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginUsername = findViewById(R.id.login_username)
        loginPassword = findViewById(R.id.login_password)
        signupRedirectText = findViewById(R.id.signup_RedirectText)

        loginButton = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener(View.OnClickListener {
            if (!validateUsername() or !validatePassword()) {
            } else {
                checkUser()
            }
        })
        signup_RedirectText.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Activity_Signup::class.java)
            startActivity(intent)
        })
    }

    fun validateUsername(): Boolean {
        val `val` = loginUsername!!.text.toString()
        return if (`val`.isEmpty()) {
            loginUsername!!.error = "Vui lòng nhập tên người dùng"
            false
        } else {
            loginUsername!!.error = null
            true
        }
    }

    fun validatePassword(): Boolean {
        val `val` = loginPassword!!.text.toString()
        return if (`val`.isEmpty()) {
            loginPassword!!.error = "Vui lòng nhập mật khẩu"
            false
        } else {
            loginPassword!!.error = null
            true
        }
    }

    fun checkUser() {
        val userUsername = loginUsername!!.text.toString().trim { it <= ' ' }
        val userPassword = loginPassword!!.text.toString().trim { it <= ' ' }
        val reference = FirebaseDatabase.getInstance().getReference("users")
        val checkUserDatabase = reference.orderByChild("username").equalTo(userUsername)
        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    loginUsername!!.error = null
                    val passwordFromDB = snapshot.child(userUsername).child("password").getValue(
                        String::class.java
                    )
                    if (passwordFromDB == userPassword) {
                        loginUsername!!.error = null
                        val nameFromDB = snapshot.child(userUsername).child("name").getValue(
                            String::class.java
                        )
                        val usernameFromDB =
                            snapshot.child(userUsername).child("username").getValue(
                                String::class.java
                            )
                        val intent = Intent(this@Activity_Login, MainActivity::class.java)
                        intent.putExtra("username", usernameFromDB)
                        intent.putExtra("password", passwordFromDB)
                        startActivity(intent)
                    } else {
                        loginPassword!!.error = "Invalid Credentials"
                        loginPassword!!.requestFocus()
                    }
                } else {
                    loginUsername!!.error = "User does not exist"
                    loginUsername!!.requestFocus()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}