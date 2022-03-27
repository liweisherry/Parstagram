package com.rkpandey.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide();
        // check if user logged in. If it is, go to the main activity
        if(ParseUser.getCurrentUser() != null){
            goTOMainActivity()
        }
        findViewById<Button>(R.id.btn_login).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.btn_signup).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            signUpUser(username, password)
        }


    }

    private fun signUpUser(username: String, password: String){
        // Create the ParseUser
        val user = ParseUser()

    // Set fields for the user to be created
        user.username = username
        user.setPassword(password)
        var currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            ParseUser.logOut();
        }


        user.signUpInBackground { e ->
            if (e == null) {
                // Sign up succeeds
                // Navigate the user to the main activity
                Log.i(TAG, "Successfully login")
                goTOMainActivity()
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                e.printStackTrace()
                Toast.makeText(this, "Error in signing up", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private  fun loginUser(username: String, password: String){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Successfully login")
                goTOMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error in logging", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private  fun goTOMainActivity(){
        val intent = Intent(this, MainActivity:: class.java)
        startActivity(intent)
        finish()
    }

    companion object{
        val TAG = "Login Activitiy"
    }
}