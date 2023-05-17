package com.example.dacs3.SignUpLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dacs3.HelperClass
import com.example.dacs3.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class Activity_Signup : AppCompatActivity() {
    var signupName: EditText? = null
    var signupUsername: EditText? = null
    var signupEmail: EditText? = null
    var signupPassword: EditText? = null
    var loginRedirectText: TextView? = null
    var signupButton: Button? = null
    var database: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupUsername = findViewById(R.id.signup_username)
        signupPassword = findViewById(R.id.signup_password)
        loginRedirectText = findViewById(R.id.login_RedirectText)

        signupButton = findViewById(R.id.signup_button)

        signup_button.setOnClickListener(View.OnClickListener {
            database = FirebaseDatabase.getInstance()
            reference = database!!.getReference("users")
            val username = signup_username.getText().toString()
            val password = signup_password.getText().toString()
            val helperClass = HelperClass(username, password)
            reference!!.child(username).setValue(helperClass)
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, Activity_Login::class.java)
            startActivity(intent)
        })

        login_RedirectText.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Activity_Login::class.java)
            startActivity(intent)
        })
    }
}


